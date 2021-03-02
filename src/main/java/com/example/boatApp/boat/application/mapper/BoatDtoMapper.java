package com.example.boatApp.boat.application.mapper;

import com.example.boatApp.boat.application.dto.BoatRequest;
import com.example.boatApp.boat.application.dto.BoatResponse;
import com.example.boatApp.boat.application.dto.DetailBoatResponse;
import com.example.boatApp.boat.domain.Boat;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper
public interface BoatDtoMapper {

    Boat toDomain(BoatRequest source);

    BoatResponse toBoatResponse(Boat source);

    DetailBoatResponse toDetailBoatResponse(Boat source);

    List<BoatResponse> map(List<Boat> source);
}
