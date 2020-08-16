package com.vix.digital.services.online.service;

import com.vix.digital.services.online.model.HttpMethodType;
import com.vix.digital.services.online.model.Service;
import com.vix.digital.services.online.model.ServiceState;
import com.vix.digital.services.online.repositories.ServiceRepository;
import com.vix.digital.services.online.service.RestServiceHandlerImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class RestServiceHandlerImplTest {

    @InjectMocks
    RestServiceHandlerImpl restServiceHandler;

    @Mock
    ServiceRepository repository;

    @Test
    void getAllService() {
        Service mockService = Mockito.mock(Service.class);
        List<Service> serviceList = new ArrayList<>();
        serviceList.add(mockService);
        Mockito.when(repository.findAll()).thenReturn(serviceList);
        assertThat(restServiceHandler.getAllServices()).isNotNull();
        assertThat(restServiceHandler.getAllServices().size()).isNotZero();
    }

    @Test
    void getService() {
        Mockito.when(repository.findById(1l)).thenReturn(createService());
        assertThat(restServiceHandler.getService(1l)).isNotNull();
        assertThat(restServiceHandler.getService(1l).getId()).isEqualTo(1l);
        assertThat(restServiceHandler.getService(1l).getName()).isEqualTo("TestService");
        assertThat(restServiceHandler.getService(1l).getMethodType()).isEqualTo(HttpMethodType.POST);
        assertThat(restServiceHandler.getService(1l).getServiceState()).isEqualTo(ServiceState.RUNNING);
        assertThat(restServiceHandler.getService(1l).getUrl()).isEqualTo("http://localhost:8080/api/docs");
        assertThat(restServiceHandler.getService(1l).getCreatedTime()).isEqualTo(LocalDateTime.of(2020, 8, 12, 1, 00, 00));
        assertThat(restServiceHandler.getService(1l).getCreatedTime()).isEqualTo(LocalDateTime.of(2020, 8, 12, 1, 00, 00));
    }

    @Test
    void addService() {
        Service mockService = Mockito.mock(Service.class);
        Mockito.when(repository.save(mockService)).thenReturn(createService().get());
        assertThat(restServiceHandler.addService(mockService)).isNotNull();
        assertThat(restServiceHandler.addService(mockService).getId()).isEqualTo(1l);
        assertThat(restServiceHandler.addService(mockService).getName()).isEqualTo("TestService");
        assertThat(restServiceHandler.addService(mockService).getMethodType()).isEqualTo(HttpMethodType.POST);
        assertThat(restServiceHandler.addService(mockService).getServiceState()).isEqualTo(ServiceState.RUNNING);
        assertThat(restServiceHandler.addService(mockService).getUrl()).isEqualTo("http://localhost:8080/api/docs");
        assertThat(restServiceHandler.addService(mockService).getCreatedTime()).isEqualTo(LocalDateTime.of(2020, 8, 12, 1, 00, 00));
        assertThat(restServiceHandler.addService(mockService).getCreatedTime()).isEqualTo(LocalDateTime.of(2020, 8, 12, 1, 00, 00));
    }

    @Test
    void updateService() {
        Service mockService = Mockito.mock(Service.class);
        mockService.setId(1l);
        Mockito.when(repository.findById(1l)).thenReturn(Optional.of(mockService));
        Mockito.when(repository.save(mockService)).thenReturn(createService().get());
        assertThat(restServiceHandler.updateService(mockService, 1l)).isNotNull();
        assertThat(restServiceHandler.updateService(mockService, 1l).getId()).isEqualTo(1l);
        mockService.setId(2l);
        Mockito.when(repository.findById(2l)).thenReturn(Optional.empty());
        assertThat(restServiceHandler.updateService(mockService, 2l)).isNotNull();
    }

    @Test
    void deleteService() {
        restServiceHandler.deleteService(1l);
        verify(repository, atLeast(1)).deleteById(1l);
    }

    private Optional<Service> createService() {
        return Optional.ofNullable(Service.builder()
                .id(1l).name("TestService").serviceState(ServiceState.RUNNING)
                .methodType(HttpMethodType.POST)
                .url("http://localhost:8080/api/docs")
                .createdTime(LocalDateTime.of(2020, 8, 12, 1, 00, 00))
                .lastUpdatedTime(LocalDateTime.of(2020, 8, 12, 1, 00, 00))
                .build());
    }
}