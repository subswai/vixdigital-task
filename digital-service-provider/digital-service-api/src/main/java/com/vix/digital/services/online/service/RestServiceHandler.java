package com.vix.digital.services.online.service;

import com.vix.digital.services.online.model.Service;

import java.util.List;

public interface RestServiceHandler {

    List<Service> getAllServices();
    Service getService(Long id);
    Service addService(Service service);
    Service updateService(Service service, Long id);
    void deleteService(Long id);
}
