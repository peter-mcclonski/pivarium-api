package com.pivarium.piv_rest.apiclass

class SwitchStatus {
    final String id;
    final boolean status;

    SwitchStatus(String id, boolean status) {
        this.id = id;
        this.status = status;
    }
}
