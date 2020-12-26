package com.fherfurt.HappyPlant.model;

import com.sun.istack.NotNull;

import javax.persistence.*;

@Entity
public class Plant {

    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private long id;

    @NotNull
    private String name;

    @NotNull
    private PlantStatus status;

    public Plant(String name, PlantStatus status) {
        this.name = name;
        this.status = status;
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

    public void setStatus(PlantStatus status) {
        this.status = status;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    // If the plant is thirsty then true
    public boolean isThirsty(Plant plant)
    {
        boolean isThirsty;
        PlantStatus status = plant.getStatus();
        if(status == PlantStatus.THIRSTY)
        {
            isThirsty = true;
        }
        else
        {
            isThirsty = false;
        }
        return isThirsty;
    }
}
