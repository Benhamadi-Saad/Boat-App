package com.example.boatApp.boat.application.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class BoatResponse {

    @Schema(description="Id of Boat")
    private Long id;

    @Schema(description="Description of Boat")
    private String name;
}
