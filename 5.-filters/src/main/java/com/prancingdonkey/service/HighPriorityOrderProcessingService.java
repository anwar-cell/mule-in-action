package com.prancingdonkey.service;

import org.mule.api.MuleEventContext;
import org.mule.api.lifecycle.Callable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HighPriorityOrderProcessingService implements Callable {

	Logger LOGGER = LoggerFactory.getLogger(HighPriorityOrderProcessingService.class);
   
	@Override
	public Object onCall(MuleEventContext eventContext) throws Exception {
		LOGGER.info("Processing order.");
		return "Processing order.";
	}

}
