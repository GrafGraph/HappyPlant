package com.fherfurt.HappyPlant.model;

public class Plant {

    private Integer id;
    private String name;
    private String status;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Plant() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Plant(String name, String status) {
        this.name = name;
        this.status = status;
    }

    public String howAmI(){
        return "My Name is " + this.name + " and i am " + this.status;
    }
}
