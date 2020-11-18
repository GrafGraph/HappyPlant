package com.fherfurt.HappyPlant.repository;

import com.fherfurt.HappyPlant.model.Plant;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface PlantRepository extends CrudRepository<Plant, Integer> {
    Optional<Plant> findByID(Integer ID);
}