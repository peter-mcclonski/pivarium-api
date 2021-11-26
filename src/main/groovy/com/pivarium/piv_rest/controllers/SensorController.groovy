package com.pivarium.piv_rest.controllers

import com.pivarium.piv_rest.apiclass.Sensor
import com.pivarium.piv_rest.apiclass.SensorReading
import com.pivarium.piv_rest.repos.ReadingRepository
import com.pivarium.piv_rest.repos.SensorRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
class SensorController {
    @Autowired
    private final SensorRepository repository;
    @Autowired
    private final ReadingRepository readingRepo;

    @GetMapping("/sensor/list")
    List<Sensor> sensorList(@RequestParam(value = "ident") String ident) {
        return repository.findByStationID(ident)
    }

    @PutMapping("/sensor/register")
    Sensor registerSensor(@RequestBody Sensor sensor) {
        Sensor current = repository.findByUuid(sensor.uuid)
        if (current == sensor) {
            return current
        } else {
            return repository.save(sensor);
        }
    }

    @PutMapping("/sensor/status")
    Sensor sensorStatus(@RequestBody SensorReading status) {
        if (status.status instanceof Map) {
            Map statMap = (Map)status.status
            for (String k : statMap.keySet()) {
                SensorReading subRead = new SensorReading(status.utime, status.sensor_id, k, statMap.get(k))
                readingRepo.save(subRead)
            }
        } else {
            readingRepo.save(status)
        }
        return repository.findByUuid(status.sensor_id)
    }
}
