package com.vix.digital.services.online.service;

import org.springframework.web.client.RestClientException;

public interface ServiceConnector {

    void manageService() throws RestClientException;
}
