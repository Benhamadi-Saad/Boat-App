package com.example.boatApp.boat.infrastacture.repository;

import com.example.boatApp.boat.domain.Boat;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import java.util.Collections;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

public class BoatRepositoryAdapterTest {

    private BoatRepositoryAdapter boatRepositoryAdapter;
    private BoatJpa boatJpaMock;

    @BeforeEach
    void setup() {
        boatJpaMock = mock(BoatJpa.class);
        boatRepositoryAdapter = new BoatRepositoryAdapter(boatJpaMock);
    }

    @Test
    public void shouldFindAll_thenReturnListOfOneBoat() {
        // Arrange
        BoatEntity boatEntity = factoryBoatEntity();

        when(this.boatJpaMock.findAll())
                .thenReturn(Collections.singletonList(boatEntity));
        // Act
        final var boats = this.boatRepositoryAdapter.findAll();

        // Assert
        assertThat(boats)
                .isNotNull()
                .doesNotContainNull();
        assertThat(boats).size().isEqualTo(1);

        final var boat = boats.get(0);
        assertThat(boat.getId()).isEqualTo(123L);
        assertThat(boat.getName()).isEqualTo("Name");
        assertThat(boat.getDescription()).isEqualTo("The characteristics of boat");

        verify(this.boatJpaMock).findAll();
    }

    @Test
    public void shouldFindById_thenReturnBoat() {
        // Arrange
        BoatEntity boatEntity = factoryBoatEntity();
        Optional<BoatEntity> ofResult = Optional.of(boatEntity);

        when(this.boatJpaMock.findById(any()))
                .thenReturn(ofResult);

        // Act
        var result = this.boatRepositoryAdapter
                .findById(123L)
                .get();

        // Assert
        assertThat(result).isNotNull();
        assertThat(result.getId().longValue()).isEqualTo(123L);
        assertThat(result.getName()).isEqualTo("Name");
        assertThat(result.getDescription()).isEqualTo("The characteristics of boat");

        verify(this.boatJpaMock).findById(any());
    }

    @Test
    public void shouldSave_thenReturnBoat() {
        // Arrange

        BoatEntity boatEntity = factoryBoatEntity();
        when(this.boatJpaMock.save(any()))
                .thenReturn(boatEntity);

        // Act
        Boat actualSaveResult;
        actualSaveResult = this.boatRepositoryAdapter.save(new Boat());

        // Assert
        assertThat(actualSaveResult).isNotNull();
        assertThat(actualSaveResult.getId().longValue()).isEqualTo(123L);
        assertThat(actualSaveResult.getName()).isEqualTo("Name");
        assertThat(actualSaveResult.getDescription()).isEqualTo("The characteristics of boat");

        verify(this.boatJpaMock).save(any());
    }

    @Test
    public void shouldDeleteById_thenDoNothing() {
        // Arrange
        ArgumentCaptor<BoatEntity> boatArgumentCaptor = ArgumentCaptor.forClass(BoatEntity.class);
        var boatDomain = new Boat();
        boatDomain.setId(1L);
        boatDomain.setName("name");
        boatDomain.setDescription("description of boat");
        doNothing().when(this.boatJpaMock)
                .deleteById(any());

        // Act
        this.boatRepositoryAdapter.delete(boatDomain);

        // Assert
        verify(this.boatJpaMock).delete(boatArgumentCaptor.capture());
        assertThat(boatArgumentCaptor.getValue().getName()).isEqualTo("name");
        assertThat(boatArgumentCaptor.getValue().getDescription()).isEqualTo("description of boat");

    }

    private BoatEntity factoryBoatEntity() {
        BoatEntity boatEntity = new BoatEntity();
        boatEntity.setId(123L);
        boatEntity.setName("Name");
        boatEntity.setDescription("The characteristics of boat");
        return boatEntity;
    }
}

