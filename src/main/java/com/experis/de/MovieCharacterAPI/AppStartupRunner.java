package com.experis.de.MovieCharacterAPI;

import com.experis.de.MovieCharacterAPI.models.*;
import com.experis.de.MovieCharacterAPI.models.Character;
import com.experis.de.MovieCharacterAPI.repositories.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


@Component
public class AppStartupRunner implements ApplicationRunner {
    @Autowired
    private final CharacterRepository characterRepository;
    @Autowired
    private final MovieRepository movieRepository;
    @Autowired
    private final FranchiseRepository franchiseRepository;

    public AppStartupRunner(CharacterRepository characterRepository, MovieRepository movieRepository, FranchiseRepository franchiseRepository) {
        this.characterRepository = characterRepository;
        this.movieRepository = movieRepository;
        this.franchiseRepository = franchiseRepository;
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {

        this.movieRepository.deleteAll();
        this.franchiseRepository.deleteAll();
        this.characterRepository.deleteAll();

        var movieList = new HashSet<Movie>();
        var franchiseList = new HashSet<Franchise>();
        var characterList1 = new HashSet<Character>();

        // Lord of the Rings Trilogy
        Franchise franchise = new Franchise("Lord of the Rings", "Everything about middle earth");
        franchiseList.add(franchise);

        characterList1.add(new Character("Elijah Wood", "frodo", Character.Gender.Male, "https://www.imdb.com/name/nm0000704/mediaviewer/rm2331620608?ref_=nm_ov_ph"));
        characterList1.add(new Character("Ian McKellen", "gandalf", Character.Gender.Male, "https://www.imdb.com/name/nm0005212/mediaviewer/rm230854144?ref_=nm_ov_ph"));
        characterList1.add(new Character("Sean Astin", "sam", Character.Gender.Male, "https://www.imdb.com/name/nm0000276/mediaviewer/rm3770064128?ref_=nm_ov_ph"));

        Movie movie;
        movie = new Movie("The Lord of the Rings: The Fellowship of the Ring", "Fantasy,Adventure", 2001, "Peter Jackson",
                "https://www.imdb.com/title/tt0120737/mediaviewer/rm3592958976/?ref_=tt_ov_i",
                "https://www.imdb.com/video/vi2084551193?playlistId=tt0120737&ref_=tt_ov_vi");
        movie.setCharacters(characterList1);
        movie.setFranchise(franchise);
        movieList.add(movie);

        movie = new Movie("The Lord of the Rings: The Two Towers", "Fantasy,Adventure", 2002, "Peter Jackson",
                "https://www.imdb.com/title/tt0167261/mediaviewer/rm306845440/",
                "https://www.imdb.com/video/vi2073101337?playlistId=tt0167261&ref_=tt_pr_ov_vi");
        movie.setCharacters(characterList1);
        movie.setFranchise(franchise);
        movieList.add(movie);

        movie = new Movie("The Lord of the Rings: The Return of the King", "Fantasy,Adventure", 2003, "Peter Jackson",
                "https://www.imdb.com/title/tt0167260/mediaviewer/rm584928512/?ref_=tt_ov_i",
                "https://www.imdb.com/video/vi2073101337?playlistId=tt0167260&ref_=tt_ov_vi");
        movie.setCharacters(characterList1);
        movie.setFranchise(franchise);
        movieList.add(movie);

        // (First) Star Wars Trigology
        franchise = new Franchise("Star Wars", "Everything about the Star Wars universe");
        franchiseList.add(franchise);

        var characterList2 = new HashSet<Character>();
        characterList2.add(new Character("Mark Hamill", "luke", Character.Gender.Male, "https://www.imdb.com/name/nm0000704/mediaviewer/rm2331620608?ref_=nm_ov_ph"));
        characterList2.add(new Character("Harrison Ford", "solo", Character.Gender.Male, "https://www.imdb.com/name/nm0005212/mediaviewer/rm230854144?ref_=nm_ov_ph"));
        characterList2.add(new Character("Carrie Fisher", "leia", Character.Gender.Female, "https://www.imdb.com/name/nm0000276/mediaviewer/rm3770064128?ref_=nm_ov_ph"));

        movie = new Movie("Star Wars", "Fantasy,Science-Fiction,Action", 1977, "George Lucas",
                "https://www.imdb.com/title/tt0167260/mediaviewer/rm584928512/?ref_=tt_ov_i",
                "https://www.imdb.com/video/vi2073101337?playlistId=tt0167260&ref_=tt_ov_vi");
        movie.setCharacters(characterList2);
        movie.setFranchise(franchise);
        movieList.add(movie);

        movie = new Movie("The Empire Strikes Back", "Fantasy,Science-Fiction,Action", 1980, "Irvin Kershner",
                "https://www.imdb.com/title/tt0167260/mediaviewer/rm584928512/?ref_=tt_ov_i",
                "https://www.imdb.com/video/vi2073101337?playlistId=tt0167260&ref_=tt_ov_vi");
        movie.setCharacters(characterList2);
        movie.setFranchise(franchise);
        movieList.add(movie);

        movie = new Movie("Return of the Jedi", "Fantasy,Science-Fiction,Action", 1983, "Richard Marquand",
                "https://www.imdb.com/title/tt0167260/mediaviewer/rm584928512/?ref_=tt_ov_i",
                "https://www.imdb.com/video/vi2073101337?playlistId=tt0167260&ref_=tt_ov_vi");
        movie.setCharacters(characterList2);
        movie.setFranchise(franchise);
        movieList.add(movie);

        this.franchiseRepository.saveAll(franchiseList);
        this.characterRepository.saveAll(characterList1);
        this.characterRepository.saveAll(characterList2);
        this.movieRepository.saveAll(movieList);

    }
}