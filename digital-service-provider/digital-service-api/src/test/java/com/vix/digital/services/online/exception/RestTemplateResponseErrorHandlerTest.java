package com.vix.digital.services.online.exception;

import com.vix.digital.services.online.model.Service;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.client.RestClientTest;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.client.ExpectedCount;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.web.client.RestTemplate;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.method;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withStatus;

@ExtendWith(MockitoExtension.class)
@ContextConfiguration(classes = {ServiceNotFoundException.class, Service.class})
@RestClientTest
public class RestTemplateResponseErrorHandlerTest {

    @Autowired
    private MockRestServiceServer server;

    @Autowired
    private RestTemplateBuilder builder;

    @Test
    public void givenRestTemplateCallwhen404Error_thenServiceNotFound() {
        assertThat(this.builder).isNotNull();
        assertThat(this.server).isNotNull();
        RestTemplate restTemplate = this.builder
                .errorHandler(new RestTemplateResponseErrorHandler())
                .build();
        this.server
                .expect(ExpectedCount.once(), requestTo("/api/vix/digital/v1/dummy/services"))
                .andExpect(method(HttpMethod.GET))
                .andRespond(withStatus(HttpStatus.NOT_FOUND));
        assertThrows(ServiceNotFoundException.class,
                () -> {

                    ResponseEntity<Service> response = restTemplate
                            .getForEntity("/api/vix/digital/v1/dummy/services", Service.class);
                });
        this.server.verify();
    }
}
