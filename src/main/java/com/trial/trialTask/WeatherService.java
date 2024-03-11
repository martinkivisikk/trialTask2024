package com.trial.trialTask;

import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.xml.sax.InputSource;

import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Component
public class WeatherService {

    @Autowired
    private WeatherConditionRepository weatherConditionRepository;

    //@Scheduled(cron = "0 38 * * * *")
    @PostConstruct
    public void fetchDataAndSave() throws IOException, ParserConfigurationException {
        RestTemplate restTemplate = new RestTemplate();
        String xmlData = restTemplate.getForObject("https://www.ilmateenistus.ee/ilma_andmed/xml/observations.php", String.class);
        List<String> names = new ArrayList<>();
        Collections.addAll(names,"Tallinn-Harku","Tartu-Tõravere","Pärnu");

        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document document = builder.parse(new InputSource(new StringReader(xmlData)));

            NodeList observations = document.getElementsByTagName("observations");
            Element observation = (Element) observations.item(0);
            String timestamp = observation.getAttribute("timestamp");
            System.out.println("Timestamp: " + timestamp);

            NodeList stations = document.getElementsByTagName("station");

            for (int i = 0; i < stations.getLength(); i++) {
                Element station = (Element) stations.item(i);
                String name = station.getElementsByTagName("name").item(0).getTextContent();
                if (names.contains(name)) {
                    System.out.println(name);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }


        //WeatherCondition weatherData;
        //weatherConditionRepository.save(weatherData);
    }
}
