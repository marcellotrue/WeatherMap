package com.example.marcellosouza.weathermap.model;

import org.parceler.Parcel;

/**
 * Created by Marcello Souza on 1/14/2017.
 */

@Parcel
public class City {

     int id;
     String name;
     double longetude, latitude;
     Measure measure;


    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public double getLongetude() {
        return longetude;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setLongetude(double longetude) {
        this.longetude = longetude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public Measure getMeasure() {
        return measure;
    }

    public void setMeasure(Measure measure) {
        this.measure = measure;
    }
}
