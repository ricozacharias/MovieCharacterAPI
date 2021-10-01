package com.experis.de.MovieCharacterAPI.controllers;

import com.experis.de.MovieCharacterAPI.dto.CharacterMessage;
import com.experis.de.MovieCharacterAPI.services.MovieCharacterService;
import io.swagger.v3.oas.annotations.*;
import io.swagger.v3.oas.annotations.media.*;
import io.swagger.v3.oas.annotations.responses.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/api/v1/characters")
public class CharacterController {

    private final MovieCharacterService movieCharacterService;

    @Autowired
    public CharacterController(MovieCharacterService movieCharacterService) {
        this.movieCharacterService = movieCharacterService;
    }

    @Operation(summary = "Get all characters without any filter")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "List of characters",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = CharacterMessage.class)) }),
            @ApiResponse(responseCode = "404", description = "No characters found",
                    content = @Content) })
     @GetMapping()
    public ResponseEntity<List<CharacterMessage>> getAllCharacters(){
        List<CharacterMessage> characterList;
        HttpStatus status;

        characterList = movieCharacterService.getAllCharacters();
        status = (characterList != null) ? HttpStatus.OK : HttpStatus.NOT_FOUND;

        return new ResponseEntity<>(characterList, status);
    }

    @Operation(summary = "Get single character by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Found the character",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = CharacterMessage.class)) }),
            @ApiResponse(responseCode = "404", description = "Character not found",
                    content = @Content) })
    @GetMapping("/{id}")
    public ResponseEntity<CharacterMessage> getCharacter(@PathVariable Long id){
        CharacterMessage character;
        HttpStatus status;

        character = movieCharacterService.getCharacter(id);
        status = (character != null) ? HttpStatus.OK : HttpStatus.NOT_FOUND;

        return new ResponseEntity<>(character, status);
    }

    @Operation(summary = "Create character by input data, optional with movie ID's")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Character created",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = CharacterMessage.class)) }),
            @ApiResponse(responseCode = "404", description = "Character could not be created",
                    content = @Content) })
    @PostMapping(consumes = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<CharacterMessage> createCharacter(@RequestBody CharacterMessage characterMessage) {
        HttpStatus status;

        characterMessage = movieCharacterService.createOrUpdateCharacter(characterMessage);
        status = (characterMessage != null) ? HttpStatus.OK : HttpStatus.NOT_FOUND;

        return new ResponseEntity<>(characterMessage, status);
    }

    @Operation(summary = "Update character by input data, optional with movie IDs")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Character updated",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = CharacterMessage.class)) }),
            @ApiResponse(responseCode = "404", description = "Character could not be updated",
                    content = @Content) })
    @PutMapping(consumes = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<CharacterMessage> updateCharacter(@RequestBody CharacterMessage characterMessage) {
        HttpStatus status;

        characterMessage = movieCharacterService.createOrUpdateCharacter(characterMessage);
        status = (characterMessage != null) ? HttpStatus.OK : HttpStatus.NOT_FOUND;

        return new ResponseEntity<>(characterMessage, status);
    }

    @Operation(summary = "Delete character and remove references to movies")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Character deleted"),
            @ApiResponse(responseCode = "404", description = "Character could not be deleted")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Boolean> deleteCharacter(@PathVariable Long id) {

        if (movieCharacterService.deleteCharacter(id))
        {
            return new ResponseEntity<>(true, HttpStatus.OK);
        }

        return new ResponseEntity<>(false, HttpStatus.NOT_FOUND);
    }

}
