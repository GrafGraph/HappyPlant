package com.fherfurt.HappyPlant.repository;

import com.fherfurt.HappyPlant.model.Plant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class PlantController {

    @Autowired
    public PlantRepository plantRepository;

    @GetMapping(path = "/plants")
    public @ResponseBody
    Iterable<Plant> getAllPlants() {
        // This returns a JSON or XML with the Plants
        return plantRepository.findAll();
    }
}
