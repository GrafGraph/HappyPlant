package com.fherfurt.HappyPlant.config;

import com.fherfurt.HappyPlant.model.Plant;
import jdk.nashorn.internal.objects.annotations.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Controller
public class WebAppController {

    @Autowired
    public PlantRepository plantRepository;

    @GetMapping(path = "/plants")
    public @ResponseBody
    Iterable<Plant> getAllPlants() {
        // This returns a JSON or XML with the Plants
        return plantRepository.findAll();
    }

    @GetMapping(value="/plant/{plantId}")
    public @ResponseBody
    String getOnePlant(@PathVariable Integer  plantId, Model model) {
        // This returns a JSON or XML with the one user
       model.addAttribute("plantName", plantRepository.findById(plantId).get().getName());
        return "plant";
    }
    @GetMapping("/")
    public String greeting(@RequestParam(name="name", required=false, defaultValue="World") String name, Model model) {
        model.addAttribute("name", name);
        return "greeting";
    }
}
