package com.trial.trialTask;

import java.util.*;

public class FeeCalculationService {
    /**
     * Calculates regional base fee based on business rules
     *
     * @param city        available cities: Tallinn, Tartu, Pärnu
     * @param vehicleType available vehicles: car, scooter, bike
     * @return regional base fee for the selected city and vehicle type
     */
    public double getRegionalBaseFee(String city, String vehicleType) {
        switch (city) {
            case "Tallinn" -> {
                switch (vehicleType) {
                    case "Car" -> {
                        return 4;
                    }
                    case "Scooter" -> {
                        return 3.5;
                    }
                    case "Bike" -> {
                        return 3;
                    }
                }
            }
            case "Tartu" -> {
                switch (vehicleType) {
                    case "Car" -> {
                        return 3.5;
                    }
                    case "Scooter" -> {
                        return 3;
                    }
                    case "Bike" -> {
                        return 2.5;
                    }
                }
            }
            case "Pärnu" -> {
                switch (vehicleType) {
                    case "Car" -> {
                        return 3;
                    }
                    case "Scooter" -> {
                        return 2.5;
                    }
                    case "Bike" -> {
                        return 2;
                    }
                }
            }
        }
        return -1;
    }

    /**
     * Calculates fee based on air temperature and vehicle type
     *
     * @param airTemperature provided latest air temperature
     * @param vehicleType    provided vehicle type
     * @return air temperature fee based on latest weather conditions and vehicle type
     */
    public double getAirTemperatureFee(double airTemperature, String vehicleType) {
        if (vehicleType.equals("Scooter") || vehicleType.equals("Bike")) {
            if (airTemperature < -10) return 1;
            if (airTemperature >= -10 && airTemperature <= 0) return 0.5;
        }
        return 0;
    }

    /**
     * Calculates fee based on wind speed and vehicle type
     *
     * @param windSpeed   provided latest wind speed
     * @param vehicleType provided vehicle type
     * @return wind speed fee based on latest weather conditions and vehicle type
     */
    public double getWindSpeedFee(double windSpeed, String vehicleType) {
        if (vehicleType.equals("Bike")) {
            if (windSpeed >= 10 && windSpeed <= 20) return 0.5;
            if (windSpeed > 20) return -1; //Usage of vehicle is forbidden
        }
        return 0;
    }

    /**
     * Calculates fee based on weather phenomenons
     *
     * @param phenomenon  current weather phenomenon like rain or snow
     * @param vehicleType provided vehicle type
     * @return phenomenon fee based on latest weather conditions and vehicle type
     */
    public double getWeatherPhenomenonFee(String phenomenon, String vehicleType) {
        // snow or sleet = 1; rain = 0.5; glaze,hail,thunder = -1
        List<String> dangerous = new ArrayList<>();
        Collections.addAll(dangerous, "Glaze", "Hail", "Thunder", "Thunderstorm");

        List<String> snow = new ArrayList<>();
        Collections.addAll(snow, "Light sleet", "Moderate sleet", "Light snowfall", "Moderate snowfall", "Heavy snowfall", "Blowing snow", "Drifting snow", "Light snow shower", "Moderate snow shower", "Heavy snow shower");

        List<String> rain = new ArrayList<>();
        Collections.addAll(rain, "Light shower", "Moderate shower", "Heavy shower", "Light rain", "Moderate rain", "Heavy rain");
        if (vehicleType.equals("Bike") || vehicleType.equals("Scooter")) {
            if (dangerous.contains(phenomenon)) return -1; // Usage of vehicle is forbidden
            else if (snow.contains(phenomenon)) return 1;
            else if (rain.contains(phenomenon)) return 0.5;
        }
        return 0;
    }
}
