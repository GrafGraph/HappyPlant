package com.fherfurt.HappyPlant.config;

import com.fherfurt.HappyPlant.model.Sensor;
import org.springframework.data.repository.CrudRepository;

public interface SensorRepository extends CrudRepository<Sensor, Integer> {
}
