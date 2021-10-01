package com.experis.de.MovieCharacterAPI.repositories;

import com.experis.de.MovieCharacterAPI.models.Franchise;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface FranchiseRepository extends JpaRepository<Franchise,Long> {

    @Query("select c from Character c JOIN Movie m JOIN Franchise f where f.id = ?1")
    List<Character> getCharactersByFranchiseId(Long id);

}
