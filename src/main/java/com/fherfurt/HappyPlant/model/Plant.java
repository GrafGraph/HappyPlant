package com.fherfurt.HappyPlant.model;

import com.sun.istack.NotNull;

import javax.persistence.*;
import java.util.ArrayList;

@Entity
public class Plant {

    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private long id;

    @NotNull
    private String name;

    @NotNull
    private PlantStatus status;

    @NotNull
    private double wateringBorder;

    public Plant(String name, PlantStatus status, double wateringBorder) {
        this.name = name;
        this.status = status;
        this.wateringBorder = wateringBorder;
    }
    // Constructor without Parameters for JPA.
    public Plant() {
    }

    public String howAmI(){
        return "My Name is " + this.name + " and i am " + this.status;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public PlantStatus getStatus() {
        return status;
    }

    public double getWateringBorder() {
        return wateringBorder;
    }

    public void setStatus(PlantStatus status) {
        this.status = status;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setWateringBorder(double wateringBorder) {
        this.wateringBorder = wateringBorder;
    }

    // Changes Plantstatus if the plant is thirsty
    public void isPlantThirsty(Plant plant, SensorData data){
        ArrayList<SensorDataEntry> SensorData =  data.getEntries();

        SensorDataEntry lastSensorData = SensorData.get(SensorData.size() - 1);
        if(lastSensorData.getMoisture() <= 32)
        {
            plant.setStatus(PlantStatus.THIRSTY);
        };
    }
}
