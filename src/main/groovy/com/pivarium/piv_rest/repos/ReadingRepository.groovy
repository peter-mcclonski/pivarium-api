package com.pivarium.piv_rest.repos;

import com.pivarium.piv_rest.apiclass.SensorReading;
import org.springframework.data.mongodb.repository.MongoRepository;


interface ReadingRepository extends MongoRepository<SensorReading, String> {

}

