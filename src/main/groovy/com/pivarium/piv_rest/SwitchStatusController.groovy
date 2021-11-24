package com.pivarium.piv_rest

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
class SwitchStatusController {
    @GetMapping("/switchstat")
    SwitchStatus switchStatus(@RequestParam(value = "ident") String ident) {
        return new SwitchStatus(ident, true)
    }
}
