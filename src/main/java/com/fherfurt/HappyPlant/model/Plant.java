package com.fherfurt.HappyPlant.model;

import javax.persistence.*;

@Entity
public class Plant {

    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private long id;

    private String name;
    private String status;

    public Plant(String name, String status) {
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }
}
