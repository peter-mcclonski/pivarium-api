package com.pivarium.piv_rest.apiclass


import org.springframework.data.annotation.Id

class Sensor {
    @Id
    String id

    String uuid
    String stationID
    String[] stype
    String connType
    String hwAddress
    int frequency

    Sensor() {
        this.uuid = UUID.randomUUID().toString().replace("-", "")
        this.frequency = 20
    }

    String toString() {
        return String.format(
                "Sensor[uuid=%s, stationID=%s, stype=%s]", this.id, this.stationID, this.stype
        )
    }

    boolean equals(Sensor other) {
        return this.uuid == other.uuid && this.stationID == other.stationID && this.stype == other.stype
    }
}
