package com.pivarium.piv_rest.repos

import com.pivarium.piv_rest.apiclass.Sensor
import org.springframework.data.mongodb.repository.MongoRepository

interface SensorRepository extends MongoRepository<Sensor, String> {
    Sensor findByUuid(String uuid);
    List<Sensor> findByStationID(String stationID);
}
