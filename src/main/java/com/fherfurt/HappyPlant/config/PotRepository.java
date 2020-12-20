package com.fherfurt.HappyPlant.config;

import com.fherfurt.HappyPlant.model.Pot;
import org.springframework.data.repository.CrudRepository;

public interface PotRepository extends CrudRepository<Pot, Integer> {
}
