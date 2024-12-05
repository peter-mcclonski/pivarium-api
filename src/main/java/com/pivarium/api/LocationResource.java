package com.pivarium.api;

import com.pivarium.entity.Location;
import com.pivarium.quartz.WeatherRetrieveJob;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

@Path("/api/v1/location")
@ApplicationScoped
public class LocationResource {
    @Inject
    WeatherRetrieveJob weatherRetrieveJob;

    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Location getLocation(@PathParam("id") String id) {
        return Location.findById(id);
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Location> getLocations() {
        return Location.listAll();
    }

    @POST
    @Transactional
    public void createLocation(Location location) {
        Logger log = LoggerFactory.getLogger(LocationResource.class);
        log.info("Creating location: {}", location);
        location.persistAndFlush();
        weatherRetrieveJob.schedule(location);
    }
}
