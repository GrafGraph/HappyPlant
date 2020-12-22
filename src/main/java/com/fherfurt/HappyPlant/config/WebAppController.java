package com.fherfurt.HappyPlant.config;

import com.fherfurt.HappyPlant.helper.DataFaker;
import com.fherfurt.HappyPlant.model.Plant;
import com.fherfurt.HappyPlant.model.SensorData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.Vector;

@Controller
public class WebAppController {

    @Autowired
    public PlantRepository plantRepository;

    @GetMapping("/plants")
    public String getAllPlants(Model model) {
        Plant plant = new Plant("Willie", "Happy as a honeycake horse");
        model.addAttribute(plant);
//        Iterable<Plant> plants = plantRepository.findAll();   TODO: Read and Write Plant objects into DB
        return "plants";
    }

//    @GetMapping(value="/plant/{plantId}")
//    public @ResponseBody
//    String getOnePlant(@PathVariable Integer  plantId, Model model) {
//        // This returns a JSON or XML with the one user
//       model.addAttribute("plantName", plantRepository.findById(plantId).get().getName());
//        return "plant";
//    }

    @GetMapping("/")
    public String greeting() {
        return "greeting";
    }

    @GetMapping("/dataFaker")
    public String dataFaker(Model model) {
        // Hardcoded wateringBorder of 32 for testing purposes. TODO: change to GET Parameter?
        Vector<SensorData> sensorDataVector = new DataFaker().createSensorDataHistory(DataFaker.DEFAULT_BACKTRACK_LENGTH,32);
        model.addAttribute("sensorDataVector", sensorDataVector);
        return "dataFaker";
    }
}
