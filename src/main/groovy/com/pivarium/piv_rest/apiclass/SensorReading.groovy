package com.pivarium.piv_rest.apiclass;

import org.springframework.data.annotation.Id;

import java.util.Date;

public class SensorReading {
    @Id
    String id

    Double utime
    String sensor_id
    Object status
    Object stype

    SensorReading() {}

    SensorReading(double utime, String sensor_id, status, stype) {
        this.utime = utime
        this.sensor_id = sensor_id
        this.status = status
        this.stype = stype
    }
}
