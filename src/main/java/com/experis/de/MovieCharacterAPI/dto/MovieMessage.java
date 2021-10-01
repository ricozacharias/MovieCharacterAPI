package com.experis.de.MovieCharacterAPI.dto;

import com.fasterxml.jackson.annotation.JsonGetter;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class MovieMessage {

    public Long id;
    public String title;
    public String genre;
    public Integer releaseYear;
    public String director;
    public FranchiseMessage franchise;
    public List<CharacterMessage> characters = new ArrayList<>();

    public MovieMessage() {
    }

    public MovieMessage(Long id, String title, String genre, Integer releaseYear, String director) {
        this.id = id;
        this.title = title;
        this.genre = genre;
        this.releaseYear = releaseYear;
        this.director = director;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public Integer getReleaseYear() {
        return releaseYear;
    }

    public void setReleaseYear(Integer releaseYear) {
        this.releaseYear = releaseYear;
    }

    public String getDirector() {
        return director;
    }

    public void setDirector(String director) {
        this.director = director;
    }

    @JsonGetter("franchise")
    public String franchiseGetter() {
        if (franchise != null)
            return "/api/v1/franchises/" + franchise.getId();
        else
            return null;
    }

    @JsonGetter("characters")
    public List<String> charactersGetter() {
        if(characters != null){
            return characters.stream()
                    .map(character -> {
                        return "/api/v1/characters/" + character.getId();
                    }).collect(Collectors.toList());
        }
        return null;
    }

    public void setFranchise(FranchiseMessage franchise) {
        this.franchise = franchise;
    }

    public void setCharacters(List<CharacterMessage> characters) {
        this.characters = characters;
    }
}
