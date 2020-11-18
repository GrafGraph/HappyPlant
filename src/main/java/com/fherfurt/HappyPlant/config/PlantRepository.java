package com.fherfurt.HappyPlant.config;

import com.fherfurt.HappyPlant.model.Plant;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface PlantRepository extends CrudRepository<Plant, Integer> {
}