package com.trial.trialTask;

import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.io.StringReader;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Component
public class FetchService {

    @Autowired
    private WeatherConditionRepository weatherConditionRepository;

    //cron = second, minute, hour, day of month, month, day of week
    @Scheduled(cron = "0 15 * * * *")
    //@PostConstruct
    public void fetchDataAndSave() throws IOException, ParserConfigurationException {
        RestTemplate restTemplate = new RestTemplate();
        String xmlData = restTemplate.getForObject("https://www.ilmateenistus.ee/ilma_andmed/xml/observations.php", String.class);
        List<String> requiredStations = new ArrayList<>();
        Collections.addAll(requiredStations, "Tallinn-Harku", "Tartu-Tõravere", "Pärnu");

        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document document = builder.parse(new InputSource(new StringReader(xmlData)));

            NodeList observations = document.getElementsByTagName("observations");
            Element observation = (Element) observations.item(0);
            long timestamp = Long.parseLong(observation.getAttribute("timestamp"));
            Instant instant = Instant.ofEpochMilli(timestamp * 1000);
            //System.out.println("Timestamp: " + timestamp);

            NodeList stations = document.getElementsByTagName("station");

            for (int i = 0; i < stations.getLength(); i++) {
                Element station = (Element) stations.item(i);
                String stationName = station.getElementsByTagName("name").item(0).getTextContent();
                if (requiredStations.contains(stationName)) {
                    //System.out.println(stationName);
                    int WMOcode = Integer.parseInt(station.getElementsByTagName("wmocode").item(0).getTextContent());
                    double airTemperature = Double.parseDouble(station.getElementsByTagName("airtemperature").item(0).getTextContent());
                    double windSpeed = Double.parseDouble(station.getElementsByTagName("windspeed").item(0).getTextContent());
                    String phenomenon = station.getElementsByTagName("phenomenon").item(0).getTextContent();
                    WeatherCondition weatherCondition = new WeatherCondition(stationName, WMOcode, airTemperature, windSpeed, phenomenon, LocalDateTime.ofInstant(instant, ZoneId.of("UTC+2")));
                    System.out.println("Saving weather data: " + weatherCondition);
                    weatherConditionRepository.save(weatherCondition);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
