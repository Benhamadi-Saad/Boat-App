package com.example.boatApp.boat.application.rest;


import com.example.boatApp.boat.application.dto.BoatRequest;
import com.example.boatApp.boat.application.dto.BoatResponse;
import com.example.boatApp.boat.application.dto.DetailBoatResponse;
import com.example.boatApp.boat.application.dto.ErrorResponse;
import com.example.boatApp.boat.application.mapper.BoatDtoMapper;
import com.example.boatApp.boat.domain.service.BoatService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.mapstruct.factory.Mappers;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping("/api/boats")
@RequiredArgsConstructor
public class BoatController {

    private final BoatService boatService;

    private BoatDtoMapper mapper = Mappers.getMapper(BoatDtoMapper.class);


    @Operation(summary = "Create a boat")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Create a boat",
                    content = {@Content(mediaType = APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = DetailBoatResponse.class))})})
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Long createBoat(@Valid @RequestBody BoatRequest boatRequest) {
        return boatService.createBoat(mapper.toDomain(boatRequest));
    }

    @Operation(summary = "Get all boat")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Found the boats",
                    content = {@Content(mediaType = APPLICATION_JSON_VALUE,
                            array = @ArraySchema(arraySchema = @Schema(implementation = BoatResponse.class)))}),
            @ApiResponse(responseCode = "404", description = "Boat not found", content = {@Content(mediaType = APPLICATION_JSON_VALUE,
                    schema = @Schema(implementation = ErrorResponse.class))})})
    @GetMapping
    public List<BoatResponse> getAllBoats() {
        return mapper.map(boatService.getAllBoats());
    }


    @Operation(summary = "Get a boat by its id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Found the boat",
                    content = {@Content(mediaType = APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = DetailBoatResponse.class))}),
            @ApiResponse(responseCode = "400", description = "Invalid id supplied", content = {@Content(mediaType = APPLICATION_JSON_VALUE,
                    schema = @Schema(implementation = ErrorResponse.class))}),
            @ApiResponse(responseCode = "404", description = "Boat not found", content = {@Content(mediaType = APPLICATION_JSON_VALUE,
                    schema = @Schema(implementation = ErrorResponse.class))})})
    @GetMapping("/{id}")
    public DetailBoatResponse getBoat(@PathVariable("id") Long boatId) {

        return mapper.toDetailBoatResponse(boatService.getBoatById(boatId));
    }

    @Operation(summary = "Update a boat by its id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Update the boat",
                    content = {@Content(mediaType = APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = DetailBoatResponse.class))}),
            @ApiResponse(responseCode = "400", description = "Invalid id supplied", content = {@Content(mediaType = APPLICATION_JSON_VALUE,
                    schema = @Schema(implementation = ErrorResponse.class))}),
            @ApiResponse(responseCode = "404", description = "Boat not found", content = {@Content(mediaType = APPLICATION_JSON_VALUE,
                    schema = @Schema(implementation = ErrorResponse.class))})})
    @PutMapping("/{id}")
    public DetailBoatResponse updateBoat(@PathVariable("id") Long boatId, @Valid @RequestBody BoatRequest boatRequest) {
        var boat = boatService.updateBoat(boatId, mapper.toDomain(boatRequest));
        return mapper.toDetailBoatResponse(boat);
    }


    @Operation(summary = "delete a boat by its id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "delete the boat",
                    content = {@Content(mediaType = APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = DetailBoatResponse.class))}),
            @ApiResponse(responseCode = "400", description = "Invalid id supplied", content = {@Content(mediaType = APPLICATION_JSON_VALUE,
                    schema = @Schema(implementation = ErrorResponse.class))}),
            @ApiResponse(responseCode = "404", description = "Boat not found", content = {@Content(mediaType = APPLICATION_JSON_VALUE,
                    schema = @Schema(implementation = ErrorResponse.class))})})
    @DeleteMapping("/{id}")
    public void deleteBoat(@PathVariable("id") Long boatId) {
        boatService.deleteBoat(boatId);
    }


}
