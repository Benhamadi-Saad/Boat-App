package com.example.boatApp.boat.domain.service;

import com.example.boatApp.boat.domain.Boat;

import java.util.List;

public interface BoatService {

    List<Boat> getAllBoats();

    Boat getBoatById(Long id);

    Long createBoat(Boat map);

    Boat updateBoat(Long id, Boat boat);

    void deleteBoat(Long id);
}
