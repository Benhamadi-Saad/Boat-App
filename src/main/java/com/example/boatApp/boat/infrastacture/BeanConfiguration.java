package com.example.boatApp.boat.infrastacture;

import com.example.boatApp.boat.domain.repository.BoatRepository;
import com.example.boatApp.boat.domain.service.BoatService;
import com.example.boatApp.boat.domain.service.DomainBoatService;
import com.example.boatApp.boat.infrastacture.repository.BoatJpa;
import com.example.boatApp.boat.infrastacture.repository.BoatRepositoryAdapter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BeanConfiguration {

    @Bean
    public BoatRepository boatRepository(BoatJpa boatJpa){
        return new BoatRepositoryAdapter(boatJpa);
    }

    @Bean
    public BoatService boatService(BoatRepository repository){
        return new DomainBoatService(repository);
    }
}
