package com.vix.digital.services.online.service;

import com.vix.digital.services.online.model.Service;
import com.vix.digital.services.online.repositories.ServiceRepository;
import jdk.nashorn.internal.ir.annotations.Ignore;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.Optional;

import static org.mockito.Mockito.atMost;
import static org.mockito.Mockito.verify;
@ExtendWith(MockitoExtension.class)
class ServiceConnectorImplTest {

    @InjectMocks
    ServiceConnectorImpl serviceConnector;

    @Mock
    protected RestTemplate restTemplate;

    @Mock
    private ServiceRepository repository;

    @Autowired
    private String baseUrl;

    @Test
    void testManageService() {
        Service service = new Service();
        service.setId(1l);
        Mockito.when(restTemplate.getForEntity(baseUrl, Service.class))
          .thenReturn(new ResponseEntity(service, HttpStatus.OK));
        Mockito.when(repository.findById(Mockito.anyLong())).thenReturn(Optional.ofNullable(service));
        Mockito.when(repository.save(service)).thenReturn(service);

        serviceConnector.manageService();
        verify(restTemplate, atMost(1)).getForEntity(baseUrl, Service.class);
        verify(repository, atMost(1)).findById(1l);
        verify(repository, atMost(2)).save(service);
    }
}