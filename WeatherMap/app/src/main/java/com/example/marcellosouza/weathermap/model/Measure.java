package com.example.marcellosouza.weathermap.model;

import org.parceler.Parcel;

/**
 * Created by Marcello Souza on 1/14/2017.
 */

@Parcel
public class Measure {

     String weatherMain, weatherDescription, weatherIcon, country;
     double temperature, tempMinimum, tempMaximun, seaLevel, preasurre,
            grndLevel, windSpeed, winddeg;
     int humidity, cloudsAll, weatherId;
     long data;


    public String getWeatherMain() {
        return weatherMain;
    }

    public void setWeatherMain(String weatherMain) {
        this.weatherMain = weatherMain;
    }

    public String getWeatherDescription() {
        return weatherDescription;
    }

    public void setWeatherDescription(String weatherDescription) {
        this.weatherDescription = weatherDescription;
    }

    public String getWeatherIcon() {
        return weatherIcon;
    }

    public void setWeatherIcon(String weatherIcon) {
        this.weatherIcon = weatherIcon;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public double getTemperature() {
        return temperature;
    }

    public void setTemperature(double temperature) {
        this.temperature = temperature;
    }

    public double getTempMinimum() {
        return tempMinimum;
    }

    public void setTempMinimum(double tempMinimum) {
        this.tempMinimum = tempMinimum;
    }

    public double getTempMaximun() {
        return tempMaximun;
    }

    public void setTempMaximun(double tempMaximun) {
        this.tempMaximun = tempMaximun;
    }

    public double getSeaLevel() {
        return seaLevel;
    }

    public void setSeaLevel(double seaLevel) {
        this.seaLevel = seaLevel;
    }

    public double getPreasurre() {
        return preasurre;
    }

    public void setPreasurre(double preasurre) {
        this.preasurre = preasurre;
    }

    public double getGrndLevel() {
        return grndLevel;
    }

    public void setGrndLevel(double grndLevel) {
        this.grndLevel = grndLevel;
    }

    public double getWindSpeed() {
        return windSpeed;
    }

    public void setWindSpeed(double windSpeed) {
        this.windSpeed = windSpeed;
    }

    public double getWinddeg() {
        return winddeg;
    }

    public void setWinddeg(double winddeg) {
        this.winddeg = winddeg;
    }

    public int getHumidity() {
        return humidity;
    }

    public void setHumidity(int humidity) {
        this.humidity = humidity;
    }

    public int getCloudsAll() {
        return cloudsAll;
    }

    public void setCloudsAll(int cloudsAll) {
        this.cloudsAll = cloudsAll;
    }

    public int getWeatherId() {
        return weatherId;
    }

    public void setWeatherId(int weatherId) {
        this.weatherId = weatherId;
    }

    public long getData() {
        return data;
    }

    public void setData(long data) {
        this.data = data;
    }
}
