package com.vix.digital.services.online.controller;

import com.vix.digital.services.online.service.RestServiceHandler;
import com.vix.digital.services.online.model.Service;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * {@link DigitalServiceController} Represents REST endpoint to manage
 * (add/remove/update) services for vix digital,
 * *
 * GET http://{FQDN}/api/vix/digital/v1/services            - Returns all available Services
 * GET http://{FQDN}/api/vix/digital/v1/services/{id}       - Returns service for a specific ID
 * POST http://{FQDN}/api/vix/digital/v1/services           - Add a new service
 * PUT http://{FQDN}/api/vix/digital/v1/services            - Update an existing service for a specific ID
 * DELETE http://{FQDN}/api/vix/digital/v1/services/{id}    - Delete an existing service for a specific ID
 *
 * @author Subhasis Swain
 */

@RestController
@RequestMapping("/api/vix/digital/v1")
@CrossOrigin(origins = "*")
@OpenAPIDefinition(
        info = @Info(
                title = "VIX Digital Service Provider", version = "1.0",
                description = "We are a full service digital agency specialising in providing digital services to the " +
                        "public sector with teams in Warrington, London, Leeds and Sheffield. Our clients include NHS " +
                        "Digital, NHS England and Public Health England. This API manages all our services and its " +
                        "availability, performance and quality.Consumer to this service should be able to add/remove/update services",
                contact = @Contact(name = "Subhasis Swain", email = "swain.subhasis@gmail.com")
        )
)
public class DigitalServiceController {

    @Autowired
    RestServiceHandler restServiceHandler;

    @GetMapping("/services")
    public List<Service> getAllServices() {
        return restServiceHandler.getAllServices();
    }

    @GetMapping("/services/{id}")
    public Service getService(@PathVariable Long id) {
        return restServiceHandler.getService(id);
    }

    @PostMapping("/services")
    public ResponseEntity<String> addService(@Valid @RequestBody Service service) {
        Service dbService = restServiceHandler.addService(service);
        return new ResponseEntity("New Service added with ID: "+dbService.getId(), HttpStatus.CREATED);
    }

    @PutMapping("/services/{id}")
    public Service updateService(@Valid @RequestBody Service service, @PathVariable Long id) {
        return restServiceHandler.updateService(service, id);
    }

    @DeleteMapping("/services/{id}")
    public List<Service> deleteService(@PathVariable Long id) {
        restServiceHandler.deleteService(id);
        return restServiceHandler.getAllServices();
    }

}
