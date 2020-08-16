package com.vix.digital.services.online.controller;

import com.vix.digital.services.online.service.RestServiceHandler;
import com.vix.digital.services.online.model.Service;
import com.vix.digital.services.online.model.ServiceState;
import jdk.nashorn.internal.ir.annotations.Ignore;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

import static com.vix.digital.services.online.controller.DigitalServiceControllerIntegrationTest.asJsonString;
import static com.vix.digital.services.online.model.HttpMethodType.POST;
import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(DigitalServiceController.class)
public class DigitalServiceControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    RestServiceHandler restServiceHandler;

    @MockBean
    RestTemplate restTemplate;

    private static final String BASE_URL = "/api/vix/digital/v1/services";
    private static final String INVALID_BASE_URL = "/api/vix/digital/INVALID/services";

    @Test
    public void given_getAll_valid_URI_whenMockMVC_thenResponse_OK() throws Exception {
        Mockito.when(restServiceHandler.getAllServices()).thenReturn(getServicesList());
        this.mockMvc.perform(get(BASE_URL))
                .andDo(print())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(status().isOk());
    }

    @Test
    public void given_get_valid_URI_whenMockMVC_thenResponse_OK() throws Exception {
        Mockito.when(restServiceHandler.getService(1l)).thenReturn(getServicesList().get(0));
        this.mockMvc.perform(get(BASE_URL + "/{id}", 1l))
                .andDo(print())
                .andExpect(status().isOk()).
                andExpect(jsonPath("$.name").value("TEST_SERVICE")).
                andExpect(jsonPath("$.url").value("http://vix.digital/new/service")).
                andExpect(jsonPath("$.serviceState").value("STOPPING")).
                andExpect(jsonPath("$.methodType").value("POST"));
    }

    @Test
    public void given_post_valid_URI_whenMockMVC_thenResponse_CREATED() throws Exception {
        Mockito.when(restServiceHandler.addService(getServicesList().get(0))).thenReturn(getServicesList().get(0));
        mockMvc.perform(MockMvcRequestBuilders
                .post(BASE_URL)
                .content(asJsonString(
                        getServicesList().get(0)))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(MockMvcResultMatchers.content().string("New Service added with ID: 1"))
        ;
    }

    @Test
    public void given_put_valid_URI_whenMockMVC_thenResponse_OK() throws Exception {
        Mockito.when(restServiceHandler.updateService(getServicesList().get(0), 1l)).thenReturn(getServicesList().get(0));

        mockMvc.perform(MockMvcRequestBuilders
                .put(BASE_URL + "/{id}", 1l)
                .content(asJsonString(
                        getServicesList().get(0)))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.url").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.serviceState").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.methodType").exists())
                .andExpect(jsonPath("$.name").value("TEST_SERVICE"));
        ;
    }

    @Test
    public void given_delete_valid_URI_whenMockMVC_thenResponse_OK() throws Exception {
        this.mockMvc.perform(delete(BASE_URL + "/{id}", 1l)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
        ;
    }

    @Test
    void given_getAll_invalid_URI_whenMockMVC_thenResponse_NOT_FOUND() throws Exception {
        mockMvc.perform(get(INVALID_BASE_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andReturn();
    }

    @Test
    public void given_get_invalid_URI_whenMockMVC_thenResponse_NOT_FOUND() throws Exception {
        Mockito.when(restServiceHandler.getService(1l)).thenReturn(getServicesList().get(0));
        this.mockMvc.perform(get(INVALID_BASE_URL + "/{id}", 1l))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    @Test
    public void given_post_invalid_URI_whenMockMVC_thenResponse_CLIENT_ERROR() throws Exception {
        this.mockMvc.perform(post(INVALID_BASE_URL + "/{id}", 1l)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().is4xxClientError());
    }

    @Test
    public void given_put_invalid_URI_whenMockMVC_thenResponse_CLIENT_ERROR() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                .put(BASE_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().is4xxClientError());
    }

    @Test
    public void given_delete_invalid_URI_whenMockMVC_thenResponse_NOT_FOUND() throws Exception {
        this.mockMvc.perform(delete(INVALID_BASE_URL + "/{id}", 1l)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    private List<Service> getServicesList() {
        Service service = new Service();
        service.setId(1l);
        service.setName("TEST_SERVICE");
        service.setUrl("http://vix.digital/new/service");
        service.setServiceState(ServiceState.STOPPING);
        service.setMethodType(POST);
        List<Service> serviceList = new ArrayList<>();
        serviceList.add(service);
        return serviceList;
    }


}
