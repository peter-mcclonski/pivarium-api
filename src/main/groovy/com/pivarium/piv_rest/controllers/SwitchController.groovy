package com.pivarium.piv_rest.controllers

import com.pivarium.piv_rest.apiclass.SwitchStatus
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
class SwitchController {
    @GetMapping("/switchstat")
    SwitchStatus switchStatus(@RequestParam(value = "ident") String ident) {
        return new SwitchStatus(ident, true)
    }
}