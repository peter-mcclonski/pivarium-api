package com.pivarium.entity;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

import java.time.Instant;

@Entity
public class WeatherDescription extends PanacheEntity {
    @ManyToOne
    public Location location;
    public Integer temperature;
    public Integer humidity;
    public Instant timestamp;
    public Instant sunrise;
    public Instant sunset;

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }
}
