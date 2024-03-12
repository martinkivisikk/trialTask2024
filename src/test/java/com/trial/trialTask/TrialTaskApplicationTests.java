package com.trial.trialTask;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.anyString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class TrialTaskApplicationTests {
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private WeatherConditionRepository weatherConditionRepository;

    @Test
    void contextLoads() {
    }

    @Test
    public void test01TallinnCar() throws Exception {
        Mockito.when(weatherConditionRepository.findTopByWeatherStationOrderByTimeStampDesc(anyString()))
                .thenReturn(Optional.of(new WeatherCondition("Tallinn-Harku", 1, 0.0, 21.0, "Thunder", LocalDateTime.now())));
        mockMvc.perform(get("/get")
                        .param("city", "Tallinn")
                        .param("vehicleType", "Car"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.TEXT_PLAIN_VALUE + ";charset=UTF-8"))
                .andExpect(content().string("Total delivery fee is: 4.0"));
    }

    @Test
    public void test02PärnuCar() throws Exception {
        Mockito.when(weatherConditionRepository.findTopByWeatherStationOrderByTimeStampDesc(anyString()))
                .thenReturn(Optional.of(new WeatherCondition("Pärnu", 1, 1.0, 5.0, "Light snowfall", LocalDateTime.now())));
        mockMvc.perform(get("/get")
                        .param("city", "Pärnu")
                        .param("vehicleType", "Car"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.TEXT_PLAIN_VALUE + ";charset=UTF-8"))
                .andExpect(content().string("Total delivery fee is: 3.0"));
    }

    @Test
    public void test03TartuExample() throws Exception {
        Mockito.when(weatherConditionRepository.findTopByWeatherStationOrderByTimeStampDesc(anyString()))
                .thenReturn(Optional.of(new WeatherCondition("Tartu-Tõravere", 1, -2.1, 4.7, "Light snow shower", LocalDateTime.now())));
        mockMvc.perform(get("/get")
                        .param("city", "Tartu")
                        .param("vehicleType", "Bike"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.TEXT_PLAIN_VALUE + ";charset=UTF-8"))
                .andExpect(content().string("Total delivery fee is: 4.0"));
    }
}
