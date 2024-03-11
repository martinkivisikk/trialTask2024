package com.trial.trialTask;

import org.springframework.data.jpa.repository.JpaRepository;

public interface WeatherConditionRepository extends JpaRepository<WeatherCondition, Long> {
}
