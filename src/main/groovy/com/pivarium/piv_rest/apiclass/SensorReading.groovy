package com.pivarium.piv_rest.apiclass;

import org.springframework.data.annotation.Id;

import java.util.Date;

public class SensorReading {
    @Id
    String id

    double utime
    String sensor_id
    Map status

    SensorReading() {}
}
