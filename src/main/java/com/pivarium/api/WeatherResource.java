package com.pivarium.api;

import com.pivarium.entity.WeatherDescription;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

import java.util.List;

@Path("/api/v1/weather")
public class WeatherResource {

    @GET
    @Path("/current")
    @Produces(MediaType.APPLICATION_JSON)
    public List<WeatherDescription> weather() {
        return WeatherDescription.listAll();
    }
}
