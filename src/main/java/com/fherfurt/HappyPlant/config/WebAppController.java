package com.fherfurt.HappyPlant.config;

import com.fherfurt.HappyPlant.helper.DataFaker;
import com.fherfurt.HappyPlant.model.Plant;
import com.fherfurt.HappyPlant.model.SensorData;
import com.fherfurt.HappyPlant.model.SensorDataEntry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;


@Controller
public class WebAppController {

    @Autowired
    public PlantRepository plantRepository;

    @GetMapping("/")
    public String greeting(Model model) {
        //  = new DataFaker().createSensorDataHistoryString(DataFaker.DEFAULT_BACKTRACK_LENGTH,32);
        SensorData sensorData = new SensorData();
        String[] labels = sensorData.getTimestamps();
        String[] moistureValues = sensorData.getMoistureValues();
        double wateringBorder = 32;     // TODO: Should be read from plant object

        model.addAttribute("size", sensorData.getEntries().size());
        model.addAttribute("labels", labels);
        model.addAttribute("moistureValues", moistureValues);
        model.addAttribute("wateringBorder", wateringBorder);
        return "greeting";
    }

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

    @GetMapping("/dataFaker")
    public String dataFaker(Model model) {
        // Hardcoded wateringBorder of 32 for testing purposes. TODO: change to GET Parameter?
        // Vector<SensorDataEntry> sensorData = new DataFaker().createSensorDataHistory(DataFaker.DEFAULT_BACKTRACK_LENGTH,32);
        SensorData sensorData = new SensorData();
        model.addAttribute("sensorData", sensorData.getEntries());
        return "dataFaker";
    }
}
