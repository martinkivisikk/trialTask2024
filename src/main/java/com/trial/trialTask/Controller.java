package com.trial.trialTask;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@RestController
public class Controller {
    @Autowired
    private WeatherConditionRepository weatherConditionRepository;
    FeeCalculationService feeCalculationService = new FeeCalculationService();

    /**
     * Example request: localhost:8080/get?city=Tallinn&vehicleType=Bike -> 3.5
     * @param city provided city
     * @param vehicleType provided vehicle type
     * @return total delivery fee or forbid delivery
     */
    @GetMapping("/get")
    public ResponseEntity<?> getDeliveryFee(@RequestParam String city, @RequestParam String vehicleType) {
        Map<String, String> cityStationMap = getCityStationMap();
        if (!cityStationMap.containsKey(city)) return ResponseEntity.badRequest().body("No such city: " + city);
        //Request latest weather data from closest station
        Optional<WeatherCondition> latestWeatherData = weatherConditionRepository.findTopByWeatherStationOrderByTimeStampDesc(cityStationMap.get(city));

        //Save the necessary information
        double airTemperature = latestWeatherData.get().getAirTemperature();
        double windSpeed = latestWeatherData.get().getWindSpeed();
        String phenomenon = latestWeatherData.get().getWeatherPhenomenon();

        //Calculate fees based on provided data
        double regionalBaseFee = feeCalculationService.getRegionalBaseFee(city, vehicleType);
        if (regionalBaseFee < 0) return ResponseEntity.badRequest().body("No such vehicle: " + vehicleType);
        double airTemperatureFee = feeCalculationService.getAirTemperatureFee(airTemperature, vehicleType);
        double windSpeedFee = feeCalculationService.getWindSpeedFee(windSpeed, vehicleType);
        double phenomenonFee = feeCalculationService.getWeatherPhenomenonFee(phenomenon, vehicleType);

        double totalDeliveryFee = regionalBaseFee + airTemperatureFee + windSpeedFee + phenomenonFee;

        if (windSpeedFee >= 0 && phenomenonFee >= 0) {
            return ResponseEntity.ok().body("Total delivery fee is: " + totalDeliveryFee);
        } else {
            // If either is -1, we know that delivering is forbidden.
            return ResponseEntity.ok().body("Usage of selected vehicle type is forbidden.");
        }
    }

    /**
     * @return Maps city name to weather station
     */
    public Map<String, String> getCityStationMap() {
        Map<String, String> cityStationMap = new HashMap<>();
        cityStationMap.put("Tartu", "Tartu-Tõravere");
        cityStationMap.put("Tallinn", "Tallinn-Harku");
        cityStationMap.put("Pärnu", "Pärnu");
        return cityStationMap;
    }
}
