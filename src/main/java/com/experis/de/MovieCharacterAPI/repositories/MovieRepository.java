package com.experis.de.MovieCharacterAPI.repositories;

import com.experis.de.MovieCharacterAPI.models.Character;
import com.experis.de.MovieCharacterAPI.models.Movie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Set;

public interface MovieRepository extends JpaRepository<Movie,Long> {

    @Query("select m from Movie m JOIN m.characters c where c.id = ?1")
    List<Movie> getMoviesByCharacterId(Long characterId);

    @Query("select m from Movie m where m.franchise.id = ?1")
    List<Movie> getMoviesByFranchiseId(Long franchiseId);

}
