package com.pivarium.piv_rest.controllers

import com.pivarium.piv_rest.apiclass.PivUuid
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class UuidController {
    @GetMapping("/reqUuid")
    PivUuid genUuid() {
        return new PivUuid(UUID.randomUUID().toString().replace("-", ""));
    }
}
