package com.vix.digital.services.online.scheduler;

import com.vix.digital.services.online.service.ServiceConnector;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Date;

@Component
public class ScheduledTasks {

	private static final Logger log = LoggerFactory.getLogger(ScheduledTasks.class);

	private static final SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");

	@Autowired
	private ServiceConnector serviceConnector;

	/**
	 * Scheduler to invoke an web service call
	 */
	@Scheduled(fixedRateString = "${fixedRate.in.milliseconds}")
	public void pollDigitalService() {
		try {
			serviceConnector.manageService();
		} catch (Exception e) {
			log.error("Exception occurred in Scheduler"+e.getMessage());
		}
	}
}