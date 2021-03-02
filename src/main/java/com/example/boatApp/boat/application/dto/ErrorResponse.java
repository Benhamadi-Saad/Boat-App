package com.example.boatApp.boat.application.dto;

import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Collections;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ErrorResponse {

    @ArraySchema(arraySchema = @Schema(description = "List of error messages"))
    private  List<String> messages;

    public ErrorResponse(String message) {
        this.messages = Collections.singletonList(message);
    }
}
