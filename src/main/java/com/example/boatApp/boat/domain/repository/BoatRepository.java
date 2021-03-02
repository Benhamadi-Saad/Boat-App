package com.example.boatApp.boat.domain.repository;

import com.example.boatApp.boat.domain.Boat;

import java.util.List;
import java.util.Optional;

public interface BoatRepository {

    List<Boat> findAll();

    Optional<Boat> findById(Long id);

    Boat save(Boat boat);

    void delete(Boat boat);
}
