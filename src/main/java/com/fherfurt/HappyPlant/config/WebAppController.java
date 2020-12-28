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
        SensorData sensorData = new SensorData();   // Creates FakeData for testing

        // Prepare datasets for chart.js
        String[] labels = sensorData.getTimestamps();   // "labels" is the default name for the values of the x-axis Hint: Could have also been "timestamps" for better understanding
        String[] moistureValues = sensorData.getMoistureValues();
        double wateringBorder = 32;     // Hint: Should be read from plant object in later versions of prototype

        model.addAttribute("size", sensorData.getEntries().size());     // Needed to create the horizontal line of watering border
        model.addAttribute("labels", labels);
        model.addAttribute("moistureValues", moistureValues);
        model.addAttribute("wateringBorder", wateringBorder);
        return "index";
    }

    // Testing purpose: Page for viewing testdata as table without styles
    @GetMapping("/dataFaker")
    public String dataFaker(Model model) {
        SensorData sensorData = new SensorData();
        model.addAttribute("sensorData", sensorData.getEntries());
        return "dataFaker";
    }
}
