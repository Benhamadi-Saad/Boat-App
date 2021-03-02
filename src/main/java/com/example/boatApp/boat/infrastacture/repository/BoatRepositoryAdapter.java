package com.example.boatApp.boat.infrastacture.repository;

import com.example.boatApp.boat.domain.Boat;
import com.example.boatApp.boat.domain.repository.BoatRepository;
import com.example.boatApp.boat.infrastacture.repository.mapper.BoatEntityMapper;
import lombok.RequiredArgsConstructor;
import org.mapstruct.factory.Mappers;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RequiredArgsConstructor
public class BoatRepositoryAdapter implements BoatRepository {

    private final BoatJpa boatJpa;

    private BoatEntityMapper mapper = Mappers.getMapper(BoatEntityMapper.class);

    @Override
    public List<Boat> findAll() {
        return boatJpa.findAll()
                .stream()
                .map(mapper::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<Boat> findById(Long id) {
        return boatJpa.findById(id)
                .map(mapper::toDomain);
    }

    @Override
    public Boat save(Boat boat) {
        final var entitySaved = boatJpa.save(mapper.toEntity(boat));

        return mapper.toDomain(entitySaved);
    }

    @Override
    public void delete(Boat boat) {
        boatJpa.delete(mapper.toEntity(boat));
    }
}
