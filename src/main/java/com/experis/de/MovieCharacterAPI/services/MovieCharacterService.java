package com.experis.de.MovieCharacterAPI.services;

import com.experis.de.MovieCharacterAPI.dto.*;
import com.experis.de.MovieCharacterAPI.models.Character;
import com.experis.de.MovieCharacterAPI.models.Franchise;
import com.experis.de.MovieCharacterAPI.models.Movie;
import com.experis.de.MovieCharacterAPI.repositories.CharacterRepository;
import com.experis.de.MovieCharacterAPI.repositories.FranchiseRepository;
import com.experis.de.MovieCharacterAPI.repositories.MovieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class MovieCharacterService {

    private final CharacterRepository characterRepository;
    private final MovieRepository movieRepository;
    private final FranchiseRepository franchiseRepository;

    @Autowired
    public MovieCharacterService(CharacterRepository characterRepository, MovieRepository movieRepository, FranchiseRepository franchiseRepository) {
        this.characterRepository = characterRepository;
        this.movieRepository = movieRepository;
        this.franchiseRepository = franchiseRepository;
    }

    // Movie CRUD
    public MovieMessage getMovie(Long id) {
        MovieMessage movieMessage = null;
        if (movieRepository.existsById(id)){
            var movie = movieRepository.getById(id);
            movieMessage = MessageConverter.toMovieDto(movie, true);
        }
        return movieMessage;
    }

    public List<MovieMessage> getAllMovies() {
        List<MovieMessage> movieMessageList = new ArrayList<>();

        for (Movie movie:movieRepository.findAll()) {
            movieMessageList.add(MessageConverter.toMovieDto(movie, true));
        }

        return movieMessageList;
    }

    public MovieMessage createOrUpdateMovie(MovieMessage movieMessage) {
        var movie = MessageConverter.fromMovieDto(movieMessage, true);
        return createOrUpdateMovie(movie);
    }

    public MovieMessage createOrUpdateMovie(Movie movie) {
        movie = movieRepository.save(movie);
        return MessageConverter.toMovieDto(movie, true);
    }

    public Boolean setMovieCharacters(Long movieId, Long[] characterIds) {
        if (movieRepository.existsById(movieId))
        {
            var characters = characterRepository.findAllById(Arrays.asList(characterIds));
            if (characters.size() > 0) {
                var movie = movieRepository.getById(movieId);
                movie.setCharacters(new HashSet<>(characters));
                movieRepository.save(movie);
                return true;
            }
        }
        return false;
    }

    // Character CRUD
    public CharacterMessage getCharacter(Long id) {
        CharacterMessage characterMessage = null;
        if (characterRepository.existsById(id)){
            var character = characterRepository.getById(id);
            characterMessage = MessageConverter.toCharacterDto(character, true);
        }
        return characterMessage;
    }

    public List<CharacterMessage> getAllCharacters() {
        List<CharacterMessage> characterMessageList = new ArrayList<>();

        for (Character character : characterRepository.findAll()) {
            characterMessageList.add(MessageConverter.toCharacterDto(character, true));
        }

        return characterMessageList;
    }

    public CharacterMessage createOrUpdateCharacter(CharacterMessage characterMessage) {
        Character character = null;

        // check for update or insert
        // if character exists load character data
        if (characterMessage.getId() != null) {
            if (characterRepository.existsById(characterMessage.getId())) {
                character = characterRepository.getById(characterMessage.getId());
            }
        }

        // update character with data from DTO
        character = MessageConverter.setCharacterFromDto(characterMessage, character);
        characterRepository.save(character);

        // iterate over movies and add character (because movies "owns" characters)
        var movieIds = characterMessage.getMovies().stream().map(MovieMessage::getId).collect(Collectors.toList());
        var movies = movieRepository.findAllById(movieIds);
        for (Movie movie : movies) {
            movie.getCharacters().add(character);
        }
        character.getMovies().addAll(movies); // also set inverse side (because Hibernate doesn't do that)

        // save movies and character
        movieRepository.saveAll(movies);
        character = characterRepository.save(character);

        // transform character back to DTO
        return MessageConverter.toCharacterDto(character, true);
    }

    public Boolean deleteCharacter(Long id) {
        if (characterRepository.existsById(id)){
            List<Movie> movies = movieRepository.getMoviesByCharacterId(id);
            for (Movie movie: movies) {
                movie.getCharacters().removeIf(x -> x.getId().equals(id));
            }
            movieRepository.saveAll(movies);
            characterRepository.deleteById(id);
            return true;
        }
        else return false;
    }

    // Franchise CRUD
    public FranchiseMessage getFranchise(Long id) {
        FranchiseMessage franchiseMessage = null;
        if (franchiseRepository.existsById(id)){
            var franchise = franchiseRepository.getById(id);
            franchiseMessage = MessageConverter.toFranchiseDto(franchise, true);
        }
        return franchiseMessage;
    }

    public List<FranchiseMessage> getAllFranchises() {
        List<FranchiseMessage> franchiseMessageList = new ArrayList<>();

        for (Franchise franchise : franchiseRepository.findAll()) {
            franchiseMessageList.add(MessageConverter.toFranchiseDto(franchise, true));
        }

        return franchiseMessageList;
    }

    public FranchiseMessage createOrUpdateFranchise(FranchiseMessage franchiseMessage) {
        var franchise = MessageConverter.fromFranchiseDto(franchiseMessage, true);
        return createOrUpdateFranchise(franchise);
    }

    public FranchiseMessage createOrUpdateFranchise(Franchise franchise) {
        franchise = franchiseRepository.save(franchise);
        return MessageConverter.toFranchiseDto(franchise, true);
    }

    public Boolean setFranchiseMovies(Long franchiseId, Long[] movieIds) {
        if (franchiseRepository.existsById(franchiseId))
        {
            var franchise = franchiseRepository.getById(franchiseId);
            var movies = movieRepository.findAllById(Arrays.asList(movieIds));

            if (movies.size() > 0)
            {
                // remove franchise from movies which are not in current list
                List<Long> movieIdList = movies.stream().map(Movie::getId).collect(Collectors.toList()); //.toArray(Long[]::new);
                for (Movie movie: movieRepository.getMoviesByFranchiseId(franchiseId))
                {
                    if (!movieIdList.contains(movie.getFranchise().getId())) {
                        movie.setFranchise(null);
                        movieRepository.save(movie);
                    }
                }

                // add new franchises to movies
                for (Movie movie: movies) {
                    movie.setFranchise(franchise);
                    movieRepository.save(movie);
                }

                return true;
            }
        }
        return false;
    }

}
