package com.pivarium.piv_rest.controllers

import com.mongodb.client.MongoClient
import com.pivarium.piv_rest.apiclass.Sensor
import com.pivarium.piv_rest.apiclass.SensorReading
import com.pivarium.piv_rest.repos.ReadingRepository
import com.pivarium.piv_rest.repos.SensorRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.mongodb.core.MongoTemplate
import static org.springframework.data.mongodb.core.query.Criteria.where;
import static org.springframework.data.mongodb.core.query.Query.query
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
class SensorController {
    @Autowired
    private final MongoClient mongoClient
    @Autowired
    private final SensorRepository repository;
    @Autowired
    private final ReadingRepository readingRepo;

    @GetMapping("/sensor/list")
    List<Sensor> sensorList(@RequestParam(value = "ident") String ident) {
        return repository.findByStationID(ident)
    }

    @GetMapping("/sensor/recent")
    List<SensorReading> readings(@RequestParam(value = "ident") String ident) {
        MongoTemplate mt = new MongoTemplate(mongoClient, "test")
        println(System.currentTimeSeconds())
        List<SensorReading> results = mt.query(SensorReading.class)
                .matching(query(where("sensor_id").is(ident).and('utime').gt(System.currentTimeSeconds()-1800000)))
                .all()
        println(results)
        return results
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
