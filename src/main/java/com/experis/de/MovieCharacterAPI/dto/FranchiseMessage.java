package com.experis.de.MovieCharacterAPI.dto;

import com.fasterxml.jackson.annotation.JsonGetter;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class FranchiseMessage {

    public Long id;
    public String name;
    public String description;
    public List<MovieMessage> movies = new ArrayList<>();

    public FranchiseMessage() {
    }

    public FranchiseMessage(Long id, String name, String description) {
        this.id = id;
        this.name = name;
        this.description = description;
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

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setMovies(List<MovieMessage> movies) {
        this.movies = movies;
    }
}
