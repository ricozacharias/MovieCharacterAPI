package com.experis.de.MovieCharacterAPI.repositories;

import com.experis.de.MovieCharacterAPI.models.Character;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CharacterRepository extends JpaRepository<Character,Long> {

}
