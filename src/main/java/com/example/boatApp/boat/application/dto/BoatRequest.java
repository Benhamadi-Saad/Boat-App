package com.example.boatApp.boat.application.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;


@Getter
@Setter
@AllArgsConstructor
public class BoatRequest {

    @Schema(description="Name of Boat")
    @NotBlank(message = "Name may not be blank")
    private String name;

    @Schema(description="Description of Boat")
    @NotBlank(message = "Description may not be blank")
    private String description;
}
