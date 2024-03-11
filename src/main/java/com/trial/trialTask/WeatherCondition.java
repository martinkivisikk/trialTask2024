package com.trial.trialTask;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "weatherData")
public class WeatherCondition {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "weatherStation")
    private String weatherStation;
    @Column(name = "WMOcode")
    private int WMOcode;
    @Column(name = "airTemperature")
    private double airTemperature;
    @Column(name = "windSpeed")
    private double windSpeed;
    @Column(name = "phenomenon")
    private String weatherPhenomenon;
    @Column(name = "time")
    private LocalDateTime timeStamp;

    public WeatherCondition() {
    }

    public WeatherCondition(String weatherStation, int WMOcode, double airTemperature, double windSpeed, String weatherPhenomenon, LocalDateTime timeStamp) {
        this.weatherStation = weatherStation;
        this.WMOcode = WMOcode;
        this.airTemperature = airTemperature;
        this.windSpeed = windSpeed;
        this.weatherPhenomenon = weatherPhenomenon;
        this.timeStamp = timeStamp;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public String getWeatherStation() {
        return weatherStation;
    }

    public void setWeatherStation(String weatherStation) {
        this.weatherStation = weatherStation;
    }

    public int getWMOcode() {
        return WMOcode;
    }

    public void setWMOcode(int WMOcode) {
        this.WMOcode = WMOcode;
    }

    public double getAirTemperature() {
        return airTemperature;
    }

    public void setAirTemperature(double airTemperature) {
        this.airTemperature = airTemperature;
    }

    public double getWindSpeed() {
        return windSpeed;
    }

    public void setWindSpeed(double windSpeed) {
        this.windSpeed = windSpeed;
    }

    public String getWeatherPhenomenon() {
        return weatherPhenomenon;
    }

    public void setWeatherPhenomenon(String weatherPhenomenon) {
        this.weatherPhenomenon = weatherPhenomenon;
    }

    public LocalDateTime getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(LocalDateTime timeStamp) {
        this.timeStamp = timeStamp;
    }

    @Override
    public String toString() {
        return "WeatherCondition{" +
                "id=" + id +
                ", weatherStation='" + weatherStation + '\'' +
                ", WMOcode=" + WMOcode +
                ", airTemperature=" + airTemperature +
                ", windSpeed=" + windSpeed +
                ", weatherPhenomenon='" + weatherPhenomenon + '\'' +
                ", timeStamp=" + timeStamp +
                '}';
    }
}
