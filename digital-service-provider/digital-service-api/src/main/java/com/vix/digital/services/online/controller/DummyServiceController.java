package com.vix.digital.services.online.controller;

import com.vix.digital.services.online.model.HttpMethodType;
import com.vix.digital.services.online.model.Service;
import com.vix.digital.services.online.model.ServiceState;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/*
 * This is a dummy service which is polled by a scheduler.
 * This service will be replaced by actual vix.digital service
 */
@RestController
@RequestMapping("/api/vix/digital/v1/dummy")
public class DummyServiceController {

    @GetMapping("/services")
    public ResponseEntity<Service> getDummyService() {
        return new ResponseEntity(Service.builder()
                .id(1l)
                .name("VIX.DIGITAL")
                .methodType(HttpMethodType.GET)
                .serviceState(ServiceState.RUNNING)
                .url("http://vix.digital/dummy")
                .build(), HttpStatus.OK);
    }
}
