package com.pivarium.quartz;

import com.pivarium.entity.Location;
import com.pivarium.entity.WeatherDescription;
import io.quarkus.scheduler.Scheduler;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.json.Json;
import jakarta.json.JsonObject;
import jakarta.transaction.Transactional;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.StringReader;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Instant;

@ApplicationScoped
public class WeatherRetrieveJob {
    @Inject
    Scheduler scheduler;

    @ConfigProperty(name = "openweathermap.apikey")
    String apiKey;

    public void schedule(Location location) {
        Logger log = LoggerFactory.getLogger(WeatherRetrieveJob.class);
        log.info("Scheduling...");
        scheduler.newJob(location.name)
                .setCron(String.format("0 */%d * * * ?", location.refreshRate))
                .setTask(scheduledExecution -> {
                    try {
                        retrieveCurrentWeather(location);
                    } catch (IOException | InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                })
                .schedule();
    }

    @Transactional
    public void retrieveCurrentWeather(Location location) throws IOException, InterruptedException {
        HttpClient client = HttpClient.newBuilder().build();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(String.format(
                        "http://api.openweathermap.org/data/2.5/forecast?lat=%f&lon=%f&units=imperial&appid=%s",
                        location.latitude,
                        location.longitude,
                        apiKey
                )))
                .GET()
                .build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        Logger log = LoggerFactory.getLogger(WeatherRetrieveJob.class);
        log.info(response.body());
        JsonObject obj = Json.createReader(new StringReader(response.body())).readObject();
        JsonObject main = obj.getJsonArray("list").getJsonObject(0).getJsonObject("main");
        JsonObject city = obj.getJsonObject("city");
        WeatherDescription description = new WeatherDescription();
        description.humidity = main.getJsonNumber("humidity").intValue();
        description.temperature = main.getJsonNumber("temp").intValue();
        description.location = location;
        long timezone = city.getJsonNumber("timezone").longValue();
        description.timestamp = Instant.ofEpochSecond(obj.getJsonArray("list").getJsonObject(0).getJsonNumber("dt").longValue() + timezone);
        description.sunrise = Instant.ofEpochSecond(city.getJsonNumber("sunrise").longValue() + timezone);
        description.sunset = Instant.ofEpochSecond(city.getJsonNumber("sunset").longValue() + timezone);
        description.persist();
    }
}
