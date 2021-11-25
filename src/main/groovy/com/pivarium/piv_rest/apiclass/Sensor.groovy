package com.pivarium.piv_rest.apiclass


import org.springframework.data.annotation.Id

class Sensor {
    @Id
    String id

    String uuid
    String stationID
    String stype

    Sensor() {}

    Sensor(String uuid, String stype, String stationID) {
        this.uuid = uuid
        this.stype = stype
        this.stationID = stationID
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
