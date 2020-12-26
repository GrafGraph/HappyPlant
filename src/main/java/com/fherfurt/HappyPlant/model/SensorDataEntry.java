package com.fherfurt.HappyPlant.model;

import java.time.LocalDateTime;

public class SensorDataEntry {
    // Not needed yet
    // private String name;             // Identifier of Sensor for Plant/Pot

    private LocalDateTime timestamp;    // Local is fine as long as there are no Timezones to consider (Fine for Testing)
    private double moisture;            // Measured by Sensor

    public SensorDataEntry(LocalDateTime timestamp, double moisture) {
        this.timestamp = timestamp;
        this.moisture = moisture;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    public double getMoisture() {
        return moisture;
    }

    public void setMoisture(double moisture) {
        this.moisture = moisture;
    }
}
