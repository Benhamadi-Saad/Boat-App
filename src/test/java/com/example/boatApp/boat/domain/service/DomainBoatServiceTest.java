package com.example.boatApp.boat.domain.service;

import com.example.boatApp.boat.domain.Boat;
import com.example.boatApp.boat.domain.exception.ResourceNotFoundException;
import com.example.boatApp.boat.domain.repository.BoatRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static java.util.Collections.singletonList;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.mockito.Mockito.*;

public class DomainBoatServiceTest {

    private DomainBoatService domainBoatService;
    private BoatRepository repository;

    @BeforeEach
    void setup() {
        repository = mock(BoatRepository.class);
        domainBoatService = new DomainBoatService(repository);
    }

    @Test
    public void shouldGetAllBoats_thenReturnListOfOneBoat() {
        // Arrange
        long id = 123L;
        final var boat = factoryBoatDomain(id);

        when(repository.findAll())
                .thenReturn(singletonList(boat));

        // Act
        final var boats = domainBoatService.getAllBoats();

        // Assert
        assertThat(boats).containsExactly(boat);
        verify(repository).findAll();
    }

    @Test
    public void shouldGetBoatById_thenReturnBoat() {
        // Arrange
        long id = 123L;
        final var boat = factoryBoatDomain(id);

        when(repository.findById(any()))
                .thenReturn(Optional.of(boat));

        // Act
        Boat actualBoatById = domainBoatService.getBoatById(id);

        // Assert
        assertThat(actualBoatById).isEqualTo(boat);

        verify(repository).findById(any());
    }

    @Test
    public void shouldGetBoatById_thenThowResourceNotFoundException() throws Exception {
        // Arrange
        long id = 123L;
        when(repository.findById(any()))
                .thenReturn(Optional.empty());

        // Act
        final var thrown = catchThrowable(() -> domainBoatService.getBoatById(id));

        // Assert
        assertThat(thrown)
                .isExactlyInstanceOf(ResourceNotFoundException.class)
                .hasMessage("The boat with id 123 not found");

        verify(repository).findById(any());

    }

    @Test
    public void shouldCreateBoat_thenReturnIdOfBoat() {
        // Arrange
        final var boat = factoryBoatDomain(null);

        var mockReturn = new Boat();
        mockReturn.setId(123L);

        when(repository.save(any()))
                .thenReturn(mockReturn);

        // Act
        final var boatId = domainBoatService.createBoat(boat);

        // Assert
        assertThat(boatId).isNotNull().isEqualTo(123L);
        verify(repository).save(any());
    }

    @Test
    public void shouldUpdateBoat_thenReturnThem() {
        // Arrange
        long id = 123L;
        final var newBoat = factoryBoatDomain(id);
        when(repository.save(any())).thenReturn(newBoat);
        when(repository.findById(any())).thenReturn(Optional.of(newBoat));

        // Act
        final var updatedBoat = domainBoatService.updateBoat(id, newBoat);

        // Assert
        assertThat(updatedBoat).isNotNull().isEqualTo(newBoat);
        verify(repository).findById(any());
        verify(repository).save(any());
        verifyNoMoreInteractions(repository);
    }

    @Test
    public void shouldDeleteBoat() {
        long id = 123L;
        final var boat = factoryBoatDomain(id);
        when(repository.findById(any()))
                .thenReturn(Optional.of(boat));
        // Arrange
        doNothing().when(repository).delete(any());

        // Act
        domainBoatService.deleteBoat(123L);

        // Assert
        verify(repository).delete(any());
    }

    private Boat factoryBoatDomain(Long id) {
        Boat boat = new Boat();
        boat.setId(id);
        boat.setName("Name");
        boat.setDescription("The characteristics of boat");
        return boat;
    }
}

