package com.example.boatApp.boat.infrastacture.repository.mapper;

import com.example.boatApp.boat.domain.Boat;
import com.example.boatApp.boat.infrastacture.repository.BoatEntity;
import org.mapstruct.Mapper;

@Mapper
public interface BoatEntityMapper {

    Boat toDomain(BoatEntity source);

    BoatEntity toEntity(Boat source);
}
