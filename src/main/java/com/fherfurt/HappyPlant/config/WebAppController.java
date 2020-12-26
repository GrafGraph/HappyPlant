package com.fherfurt.HappyPlant.config;

import com.fherfurt.HappyPlant.model.SensorData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;


@Controller
public class WebAppController {

    @GetMapping("/")
    public String index(Model model) {
        SensorData sensorData = new SensorData();
        String[] labels = sensorData.getTimestamps();
        String[] moistureValues = sensorData.getMoistureValues();
        double wateringBorder = 32;     // Hint: Should be read from plant object in later versions of prototype

        model.addAttribute("size", sensorData.getEntries().size());
        model.addAttribute("labels", labels);
        model.addAttribute("moistureValues", moistureValues);
        model.addAttribute("wateringBorder", wateringBorder);
        return "index";
    }

    @GetMapping("/dataFaker")
    public String dataFaker(Model model) {
        SensorData sensorData = new SensorData();
        model.addAttribute("sensorData", sensorData.getEntries());
        return "dataFaker";
    }
}
