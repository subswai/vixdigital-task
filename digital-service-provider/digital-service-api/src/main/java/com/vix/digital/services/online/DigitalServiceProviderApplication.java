package com.vix.digital.services.online;

import com.vix.digital.services.online.exception.RestTemplateResponseErrorHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.client.RestTemplate;

import java.time.Duration;

@SpringBootApplication
@EnableScheduling
public class DigitalServiceProviderApplication {
	private static final Logger log = LoggerFactory.getLogger(DigitalServiceProviderApplication.class);
	public static void main(String[] args) {
		SpringApplication.run(DigitalServiceProviderApplication.class, args);
	}

	@Value("${service.api.connection.timeout}")
	private int timeOut;

	@Bean
	public RestTemplate restTemplate(RestTemplateBuilder builder) {
		return builder
				.errorHandler(new RestTemplateResponseErrorHandler())
				.setConnectTimeout(Duration.ofMillis(timeOut))
				.build();
	}

}
