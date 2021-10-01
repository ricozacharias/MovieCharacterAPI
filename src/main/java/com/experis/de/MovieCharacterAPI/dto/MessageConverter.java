package com.experis.de.MovieCharacterAPI.dto;

import com.experis.de.MovieCharacterAPI.models.Character;
import com.experis.de.MovieCharacterAPI.models.Franchise;
import com.experis.de.MovieCharacterAPI.models.Movie;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class MessageConverter {

    // ************************
    // *** Movie converters ***
    // ************************
    /**
     * Converts movie model to movie DTO
     * @param movie Movie model
     * @param loadChilds should references added to DTO
     * @return movie DTO
     */
    public static MovieMessage toMovieDto(Movie movie, Boolean loadChilds) {
        MovieMessage movieMessage = new MovieMessage(movie.getId(), movie.getTitle(), movie.getGenre(), movie.getReleaseYear(), movie.getDirector());
        if (loadChilds) {
            movieMessage.setCharacters(toCharactersDto(movie.getCharacters(), false));
            movieMessage.setFranchise(toFranchiseDto(movie.getFranchise(), false));
        }
        return movieMessage;
    }

    /**
     * Converts list of movie models to movie DTO lists
     * @param movies list of movie models
     * @param loadChilds should references addeds to DTO
     * @return list of movie DTOs
     */
    public static List<MovieMessage> toMoviesDto(Set<Movie> movies, Boolean loadChilds) {
        List<MovieMessage> movieMessageList = new ArrayList<>();
        for (Movie movie : movies) {
            movieMessageList.add(toMovieDto(movie, loadChilds));
        }
        return movieMessageList;
    }

    /**
     * Converts movie DTO back to movie model
     * @param movieMessage Movie DTO
     * @param loadChilds should references addeds to DTO
     * @return Movie model
     */
    public static Movie fromMovieDto(MovieMessage movieMessage, Boolean loadChilds) {
        Movie movie = new Movie(movieMessage.title, movieMessage.genre, movieMessage.releaseYear, movieMessage.director, null, null);
        return movie;
    }

    // ****************************
    // *** Character converters ***
    // ****************************

    /**
     * Converts character model to character DTO
     * @param character Character model
     * @param loadChilds should references added to DTO
     * @return character DTO
     */
    public static CharacterMessage toCharacterDto(Character character, Boolean loadChilds) {
        CharacterMessage characterMessage = new CharacterMessage(character.getId(), character.getFullName(), character.getGender().toString(), character.getAlias());
        if (loadChilds) {
            characterMessage.setMovies(toMoviesDto(character.getMovies(), false));
        }
        return characterMessage;
    }

    /**
     * Converts list of character models to character DTO list
     * @param characters list of character models
     * @param loadChilds should references addeds to DTO
     * @return list of character DTOs
     */
    public static List<CharacterMessage> toCharactersDto(Set<Character> characters, Boolean loadChilds) {
        List<CharacterMessage> characterMessageList = new ArrayList<>();
        for (Character character : characters) {
            characterMessageList.add(toCharacterDto(character, loadChilds));
        }
        return characterMessageList;
    }

    /**
     * Loads data from character DTO to character model
     * @param characterMessage Character DTO
     * @param character character model
     * @return the initialized and filled character model
     */
    public static Character setCharacterFromDto(CharacterMessage characterMessage, Character character) {
        if (character == null)
            character = new Character();
        else
            character.setId(characterMessage.getId());

        character.setFullName(characterMessage.getFullName());
        character.setAlias(characterMessage.getAlias());
        character.setGender(Character.Gender.valueOf(characterMessage.getGender()));

        return character;
    }

    // ****************************
    // *** Franchise converters ***
    // ****************************

    /**
     * Converts franchise model to franchise DTO
     * @param franchise Franchise model
     * @param loadChilds should references added to DTO
     * @return franchise DTO
     */
    public static FranchiseMessage toFranchiseDto(Franchise franchise, Boolean loadChilds) {
        FranchiseMessage franchiseMessage = new FranchiseMessage(franchise.getId(), franchise.getName(), franchise.getDescription());
        if (loadChilds) {
            franchiseMessage.setMovies(toMoviesDto(franchise.getMovies(), false));
        }
        return franchiseMessage;
    }

    /**
     * Converts list of franchise models to franchise DTO list
     * @param franchises list of franchise models
     * @param loadChilds should references addeds to DTO
     * @return list of franchise DTOs
     */
    public static List<FranchiseMessage> toFranchisesDto(List<Franchise> franchises, Boolean loadChilds) {
        List<FranchiseMessage> franchiseMessage = new ArrayList<>();
        for (Franchise franchise : franchises) {
            franchiseMessage.add(toFranchiseDto(franchise, loadChilds));
        }
        return franchiseMessage;
    }

    /**
     * Converts franchise DTO back to franchise model
     * @param franchiseMessage Movie DTO
     * @param loadChilds should references addeds to DTO
     * @return Franchise model
     */
    public static Franchise fromFranchiseDto(FranchiseMessage franchiseMessage, Boolean loadChilds) {
        Franchise franchise = new Franchise(franchiseMessage.getName(), franchiseMessage.getDescription());
        return franchise;
    }

}
