package com.pivarium.piv_rest.apiclass

import org.springframework.data.annotation.Id

class Station {
    @Id
    String id

    String uuid

    Station() {}

    Station(String uuid) {
        this.uuid = uuid
    }

    String toString() {
        return String.format(
                "Station[uuid=%s]", this.uuid
        )
    }

    boolean equals(Station other) {
        return this.uuid == other.uuid
    }
}
