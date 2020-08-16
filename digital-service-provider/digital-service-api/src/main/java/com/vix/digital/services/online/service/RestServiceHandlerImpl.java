package com.vix.digital.services.online.service;

import com.vix.digital.services.online.exception.ServiceNotFoundException;
import com.vix.digital.services.online.model.Service;
import com.vix.digital.services.online.repositories.ServiceRepository;
import com.vix.digital.services.online.scheduler.ScheduledTasks;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Objects;

/**
 * Service handler is the gateway to manage and handle all requests from DigitalServiceController
 *
 * @Author Subhasis Swain
 */
@org.springframework.stereotype.Service
public class RestServiceHandlerImpl implements RestServiceHandler {

    private static final Logger log = LoggerFactory.getLogger(ScheduledTasks.class);

    @Autowired
    private ServiceRepository repository;

    @Override
    public List<Service> getAllServices() {
        log.info("Get All Services invoked");
        return repository.findAll();
    }

    @Override
    public Service getService(Long id) {
        log.info("Get Service invoked for id: {}", id);
        return repository.findById(id)
            .orElseThrow(() -> new ServiceNotFoundException(id));
    }

    @Override
    public Service addService(Service service) {
        log.info("Add Service invoked for name: {}", service.getName());
        if (Objects.nonNull(service.getId())) {
            service.setId(null);
        }
        return repository.save(service);
    }

    @Override
    public Service updateService(Service newService, Long id) {
        log.info("Update Service invoked for service name: {} and id: {}", newService.getName(),id);
        return repository.findById(id)
                .map(dbService -> {
                    dbService.setName(newService.getName());
                    dbService.setServiceState(newService.getServiceState());
                    dbService.setMethodType(newService.getMethodType());
                    dbService.setUrl(newService.getUrl());
                    log.info("Service updated for service name: {} and id: {}", newService.getName(),id);
                    return repository.save(dbService);
                })
                .orElseGet(() -> {
                    return repository.save(newService);
                });
    }

    @Override
    public void deleteService(Long id) {
        repository.deleteById(id);
        log.info("Service Deleted for id: {}", id);
    }
}
