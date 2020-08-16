package com.vix.digital.services.online;

import com.vix.digital.services.online.controller.DigitalServiceController;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;

@SpringBootTest
class DigitalServiceProviderApplicationTests {

	@Autowired
	DigitalServiceController controller;

	@Test
	public void assertThatAppContextLoads() throws Exception {
		assertThat(controller).isNotNull();
	}

}
