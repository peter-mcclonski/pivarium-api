package com.pivarium.piv_rest.repos

import com.pivarium.piv_rest.apiclass.Station
import org.springframework.data.mongodb.repository.MongoRepository

interface StationRepository extends MongoRepository<Station, String> {
    Station findByUuid(String uuid);
}
