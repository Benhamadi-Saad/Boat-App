package com.example.boatApp.boat.domain.service;

import com.example.boatApp.boat.domain.Boat;
import com.example.boatApp.boat.domain.exception.ResourceNotFoundException;
import com.example.boatApp.boat.domain.repository.BoatRepository;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
public class DomainBoatService implements BoatService {

    private final BoatRepository repository;

    @Override
    public List<Boat> getAllBoats() {
        return repository.findAll();
    }

    @Override
    public Boat getBoatById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(String.format("The boat with id %s not found",id)));
    }

    @Override
    public Long createBoat(Boat boat) {
        boat.validate();
        return repository.save(boat)
                .getId();
    }

    @Override
    public Boat updateBoat(Long id, Boat newBoat) {
        newBoat.validate();

        var boatFromDB = getBoatById(id);
        boatFromDB.setName(newBoat.getName());
        boatFromDB.setDescription(newBoat.getDescription());

        return repository.save(boatFromDB);
    }

    @Override
    public void deleteBoat(Long id) {
        final var boatById = getBoatById(id);
        repository.delete(boatById);
    }
}
