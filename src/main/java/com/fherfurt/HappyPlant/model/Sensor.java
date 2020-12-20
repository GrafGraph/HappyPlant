package com.fherfurt.HappyPlant.model;

import com.sun.istack.NotNull;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Sensor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @NotNull
    private String type;

    public Sensor(String type) {
        this.type = type;
    }
    public Sensor(){}

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
