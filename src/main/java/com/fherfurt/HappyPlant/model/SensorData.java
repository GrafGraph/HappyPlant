package com.fherfurt.HappyPlant.model;

import com.fherfurt.HappyPlant.helper.DataFaker;

import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.ArrayList;
import java.util.Locale;

public class SensorData {
    private String label;                           // Name for Chart
    private ArrayList<SensorDataEntry> entries;     // Entries formatted similar to real sensordata

    /*
    Creates a SensorData object with defined testdata
     */
    public SensorData() {
        this.label = "Moisture";
        this.entries = new DataFaker().createSensorDataHistory(DataFaker.DEFAULT_BACKTRACK_LENGTH,32);  // Hint: Outsource the hardcoded wateringBorder
    }

    public SensorData(String label, ArrayList<SensorDataEntry> entries) {
        this.label = label;
        this.entries = entries;
    }


    public ArrayList<SensorDataEntry> getEntries() {
        return entries;
    }

    public void setEntries(ArrayList<SensorDataEntry> entries) {
        this.entries = entries;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public void setEntriesRandom() {
        this.entries = new DataFaker().createSensorDataHistory(DataFaker.DEFAULT_BACKTRACK_LENGTH,32);  // Hint; Outsource the hardcoded wateringBorder
    }

    // formats the timestamps stored in the entries as array of strings for use in Chart.js
    public String[] getTimestamps()
    {
        int size = this.entries.size();
        String[] timestamps = new String[size];
        ArrayList<SensorDataEntry> entries = this.entries;
        // Format the Datetime to german standard: 24.12.20 13:37
        DateTimeFormatter formatter = DateTimeFormatter.ofLocalizedDateTime(FormatStyle.SHORT).withLocale(new Locale("de"));
        for (int i = 0; i < size; i++)
        {
            timestamps[i] = entries.get(i).getTimestamp().format(formatter);
        }
        return timestamps;
    }

    // formats the moisture values stored in the entries as array of strings for use in Chart.js
    public String[] getMoistureValues()
    {
        int size = this.entries.size();
        String[] moistureValues = new String[size];
        ArrayList<SensorDataEntry> entries = this.entries;

        for (int i = 0; i < size; i++)
        {
            moistureValues[i] = String.valueOf(entries.get(i).getMoisture());
        }
        return moistureValues;
    }
}
