package com.trial.trialTask;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface WeatherConditionRepository extends JpaRepository<WeatherCondition, Long> {
    Optional<WeatherCondition> findTopByWeatherStationOrderByTimeStampDesc(String stationName);
}
