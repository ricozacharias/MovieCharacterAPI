package com.experis.de.MovieCharacterAPI.dto;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class CharacterMessage {

    private Long id;
    private String fullName;
    private String gender;
    private String alias;
    private List<MovieMessage> movies = new ArrayList<>();

    public CharacterMessage() {
    }

    public CharacterMessage(Long id, String fullName, String gender, String alias) {
        this.id = id;
        this.fullName = fullName;
        this.gender = gender;
        this.alias = alias;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    public void setMovies(List<MovieMessage> movies) {
        this.movies = movies;
    }

    public List<MovieMessage> getMovies() {
        return this.movies;
    }

    @JsonGetter("movies")
    public List<String> moviesGetter() {
        if(movies != null){
            return movies.stream()
                    .map(movie -> {
                        return "/api/v1/movies/" + movie.id;
                    }).collect(Collectors.toList());
        }
        return null;
    }
}
