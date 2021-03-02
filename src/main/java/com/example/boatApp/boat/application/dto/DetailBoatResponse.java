package com.example.boatApp.boat.application.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DetailBoatResponse {

    @Schema(description="Id of Boat")
    private Long id;

    @Schema(description="Name of Boat")
    private String name;

    @Schema(description="Description of Boat")
    private String description;
}
