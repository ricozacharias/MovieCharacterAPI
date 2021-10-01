package com.experis.de.MovieCharacterAPI.models;

import org.hibernate.annotations.Cascade;

import javax.persistence.*;
import java.util.*;

@Entity
public class Character {

    public enum Gender {
        Male,
        Female,
        Divers
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 100)
    private String fullName;
    private String alias;
    private Gender gender;
    private String pictureUrl;

    public Character() {

    }

    public Character(String fullName, String alias, Gender gender, String pictureUrl) {
        this.fullName = fullName;
        this.alias = alias;
        this.gender = gender;
        this.pictureUrl = pictureUrl;
    }

    @ManyToMany(mappedBy = "characters")
    @Cascade(org.hibernate.annotations.CascadeType.SAVE_UPDATE)
    private Set<Movie> movies = new HashSet<>();

    public void setMovies(Set<Movie> movies) {
        this.movies = movies;
    }

    public Set<Movie> getMovies() {
        return this.movies;
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

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public String getPictureUrl() {
        return pictureUrl;
    }

    public void setPictureUrl(String pictureUrl) {
        this.pictureUrl = pictureUrl;
    }

}
