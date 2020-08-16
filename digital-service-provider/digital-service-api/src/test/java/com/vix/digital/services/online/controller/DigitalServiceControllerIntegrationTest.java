package com.vix.digital.services.online.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.vix.digital.services.online.service.RestServiceHandler;
import com.vix.digital.services.online.model.Service;
import com.vix.digital.services.online.model.ServiceState;
import jdk.nashorn.internal.ir.annotations.Ignore;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.nio.charset.Charset;

import static com.vix.digital.services.online.model.HttpMethodType.POST;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
public class DigitalServiceControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    RestServiceHandler restServiceHandler;

    private static final String BASE_URL = "/api/vix/digital/v1/services";

    @Test
    public void given_valid_get_ServiceURI_thenReturns_Success() throws Exception {
        ResultMatcher resultMatcher = content().string("{\"id\":1000000001,\"name\":\"NHS_SERVICE_1\",\"url\":\"http://vix.digital/\",\"serviceState\":\"RUNNING\",\"methodType\":\"GET\",\"createdTime\":\"11-08-2020 11:12:12\",\"lastUpdatedTime\":\"11-08-2020 23:00:00\"}");
        mockMvc.perform(get(BASE_URL + "/{id}", 1000000002l))
                .andDo(print())
                .andExpect(status().is2xxSuccessful())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                //.andExpect(resultMatcher)
                .andReturn();
    }

    @Test
    public void given_valid_getAll_ServiceURI_thenReturns_Success() throws Exception {
        mockMvc.perform(get(BASE_URL)).andDo(print())
                .andExpect(status().is2xxSuccessful())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andReturn();
    }

    @Test
    public void given_valid_put_ServiceURI_thenReturns_Success() throws Exception {
        ResultMatcher resultMatcher = MockMvcResultMatchers.status()
                .isOk();
        MockHttpServletRequestBuilder builder =
                MockMvcRequestBuilders.put(BASE_URL+"/{id}",1000000001l)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(
                                asJsonString(Service.
                                        builder()
                                        .name("NEW_NHS_SERVICE")
                                        .methodType(POST)
                                        .serviceState(ServiceState.RUNNING)
                                        .url("http://vix.digital/new/service")
                                        .build())
                        );

        this.mockMvc.perform(builder).andExpect(resultMatcher).
                andExpect(content().contentType("application/json")).
                andExpect(jsonPath("$.name").value("NEW_NHS_SERVICE")).
                andExpect(jsonPath("$.url").value("http://vix.digital/new/service")).
                andExpect(jsonPath("$.serviceState").value("RUNNING")).
                andExpect(jsonPath("$.methodType").value("POST"))
                .andReturn();

    }

    @Test
    public void given_valid_delete_ServiceURI_thenReturns_Success() throws Exception {
        //ResultMatcher resultMatcher = content().string("{\"id\":1000000001,\"name\":\"NHS_SERVICE_1\",\"url\":\"http://vix.digital/\",\"serviceState\":\"RUNNING\",\"methodType\":\"GET\",\"createdTime\":\"11-08-2020 11:12:12\",\"lastUpdatedTime\":\"11-08-2020 23:00:00\"}");
        mockMvc.perform(delete(BASE_URL + "/{id}", 1000000001l))
                .andDo(print())
                .andExpect(status().is2xxSuccessful())
                .andReturn();
    }


    @Test
    void given_invalid_id_get_ServiceURI_thenReturns_BadRequest() throws Exception {
        mockMvc.perform(get(BASE_URL + "/{id}", 999999l)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.timestamp").isNotEmpty())
                .andExpect(jsonPath("$.message").isNotEmpty())
                .andExpect(jsonPath("$.message").value("Could not find Service for ID: 999999"))
                .andExpect(jsonPath("$.status").value(HttpStatus.NOT_FOUND.value()))
                .andReturn();
    }

    public static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


}
