package com.pivarium.piv_rest.controllers

import com.pivarium.piv_rest.apiclass.Station
import com.pivarium.piv_rest.repos.StationRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@RestController
class StationController {
    @Autowired
    private final StationRepository repository

    @GetMapping("/station/list")
    List<Station> stationList() {
        return repository.findAll()
    }

    @PutMapping("/station/register")
    Station registerStation(@RequestBody Station station) {
        Station current = repository.findByUuid(station.uuid)
        if (current == station) {
            return current
        } else {
            return repository.save(station)
        }
    }
}
