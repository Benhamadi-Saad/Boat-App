package com.example.boatApp.boat.domain;

import com.example.boatApp.boat.domain.exception.BoatDomainException;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class BoatDomainTest {
    @Test
    public void shouldValidateName_thenThrowBoatDomainException() {
        // Arrange
        Boat boat = new Boat();

        // Act
        final var thrown = catchThrowable(() ->  boat.validate());

        // Assert
        assertThat(thrown)
                .isExactlyInstanceOf(BoatDomainException.class)
                .hasMessage("The boat should have a name");
    }

    @Test
    public void shouldValidateDescription_thenThrowBoatDomainException() {
        // Arrange
        Boat boat = new Boat();
        boat.setId(123L);
        boat.setName("The boat should have a name");

        // Act
        final var thrown = catchThrowable(() ->  boat.validate());

        // Assert
        assertThat(thrown)
                .isExactlyInstanceOf(BoatDomainException.class)
                .hasMessage("The boat should have a description");
    }

    @Test
    public void shouldValidate_thenOK() {
        // Arrange
        Boat boat = new Boat();
        boat.setDescription("The characteristics of boat");
        boat.setName("The name of boat");

        // Act
        boat.validate();

        // Assert that nothing has changed
        assertEquals("The name of boat", boat.getName());
        assertEquals("The characteristics of boat", boat.getDescription());
    }
}

