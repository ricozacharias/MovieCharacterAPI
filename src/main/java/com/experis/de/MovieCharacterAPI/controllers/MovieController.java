package com.experis.de.MovieCharacterAPI.controllers;

import com.experis.de.MovieCharacterAPI.dto.CharacterMessage;
import com.experis.de.MovieCharacterAPI.dto.FranchiseMessage;
import com.experis.de.MovieCharacterAPI.dto.MovieMessage;
import com.experis.de.MovieCharacterAPI.services.MovieCharacterService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/api/v1/movies")
public class MovieController {

    private final MovieCharacterService movieCharacterService;

    @Autowired
    public MovieController(MovieCharacterService movieCharacterService) {
        this.movieCharacterService = movieCharacterService;
    }

    @Operation(summary = "Get all movies without any filter")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "List of movies",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = MovieMessage.class)) }),
            @ApiResponse(responseCode = "404", description = "No movies found",
                    content = @Content) })
    @GetMapping()
    public ResponseEntity<List<MovieMessage>> getAllMovies(){
        List<MovieMessage> movieList;
        HttpStatus status;

        movieList = movieCharacterService.getAllMovies();
        status = (movieList != null) ? HttpStatus.OK : HttpStatus.NOT_FOUND;

        return new ResponseEntity<>(movieList, status);
    }

    @Operation(summary = "Get single movie by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Found the movie",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = MovieMessage.class)) }),
            @ApiResponse(responseCode = "404", description = "Movie not found",
                    content = @Content) })
    @GetMapping("/{id}")
    public ResponseEntity<MovieMessage> getMovie(@PathVariable Long id){
        MovieMessage movie;
        HttpStatus status;

        movie = movieCharacterService.getMovie(id);
        status = (movie != null) ? HttpStatus.OK : HttpStatus.NOT_FOUND;

        return new ResponseEntity<>(movie, status);
    }

    @Operation(summary = "Create movie by input data")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Movie created",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = MovieMessage.class)) }),
            @ApiResponse(responseCode = "404", description = "Movie could not be created",
                    content = @Content) })
    @PostMapping(consumes = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<MovieMessage> createMovie(@RequestBody MovieMessage movieMessage) {
        HttpStatus status;

        movieMessage = movieCharacterService.createOrUpdateMovie(movieMessage);
        status = (movieMessage != null) ? HttpStatus.OK : HttpStatus.NOT_FOUND;

        return new ResponseEntity<>(movieMessage, status);
    }

    @Operation(summary = "Update movie by input data")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Movie updated",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = MovieMessage.class)) }),
            @ApiResponse(responseCode = "404", description = "Movie could not be updated",
                    content = @Content) })
    @PutMapping(consumes = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<MovieMessage> updateMovie(@RequestBody MovieMessage movieMessage) {
        HttpStatus status;

        movieMessage = movieCharacterService.createOrUpdateMovie(movieMessage);
        status = (movieMessage != null) ? HttpStatus.OK : HttpStatus.NOT_FOUND;

        return new ResponseEntity<>(movieMessage, status);
    }

    @Operation(summary = "Update movie characters by list of character ID's")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Movie characters updated",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = MovieMessage.class)) }),
            @ApiResponse(responseCode = "404", description = "Movie characters could not be updated",
                    content = @Content) })
    @PutMapping(path="/{id}/characters")
    public ResponseEntity updateMovieCharacters(@PathVariable Long id, @RequestParam Long[] ids) {

        if (movieCharacterService.setMovieCharacters(id, ids)) {
            return new ResponseEntity(HttpStatus.OK);
        }

        return new ResponseEntity(HttpStatus.NOT_FOUND);
    }

}
