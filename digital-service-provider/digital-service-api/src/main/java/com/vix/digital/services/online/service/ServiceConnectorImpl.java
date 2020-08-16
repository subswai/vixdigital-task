package com.vix.digital.services.online.service;

import com.vix.digital.services.online.model.Service;
import com.vix.digital.services.online.repositories.ServiceRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;

@org.springframework.stereotype.Service
public class ServiceConnectorImpl implements ServiceConnector {

    private static final Logger log = LoggerFactory.getLogger(ServiceConnectorImpl.class);

    @Value("${service.api.url}")
    protected String baseUrl;

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private ServiceRepository repository;

    @Override
    public void manageService() throws RestClientException {
        // Get service from vix.digital
        ResponseEntity<Service> response = restTemplate.getForEntity(baseUrl, Service.class);
        Service service = response.getBody();

        // Persist the information to an database
        if (response != null) {
            Service updatedService = repository.findById(service.getId())
                    .map(dbService -> {
                        dbService.setName(service.getName());
                        dbService.setServiceState(service.getServiceState());
                        dbService.setMethodType(service.getMethodType());
                        dbService.setUrl(service.getUrl());
                        return repository.save(dbService);
                    })
                    .orElse(repository.save(service));
            log.info("Dummy service last updated at {} for id {} ", LocalDateTime.now(), updatedService.getId());
        } else {
            log.info("Unable to retrieve Dummy service {}", LocalDateTime.now());
        }
    }
}
