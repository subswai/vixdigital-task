package com.vix.digital.services.online.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class ServiceNotFoundException extends RuntimeException {

    public ServiceNotFoundException(Long id) {
        super("Could not find Service for ID: " + id);
    }

    public ServiceNotFoundException() {
        super();
    }
}
