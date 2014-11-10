/**
 * Mule Anypoint Template
 * Copyright (c) MuleSoft, Inc.
 * All rights reserved.  http://www.mulesoft.com
 */

package org.mule.examples;

import static org.junit.Assert.assertNotNull;

import java.io.FileInputStream;
import java.util.Properties;

import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.mule.api.MuleMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * The objective of this class is to validate the correct behavior of the flows
 * for this Mule Template that make calls to external systems.
 */
public class SamplingTheTwitterPublicStatusUpdateStreamIT extends AbstractTemplateTestCase {
	private static final String PATH_TO_TEST_PROPERTIES = "./src/test/resources/mule.test.properties";
	private static final Logger log = LoggerFactory.getLogger(SamplingTheTwitterPublicStatusUpdateStreamIT.class); 
	protected static final int TIMEOUT = 60;
	
	@BeforeClass
	public static void init() {
		final Properties props = new Properties();
    	try {
    	props.load(new FileInputStream(PATH_TO_TEST_PROPERTIES));
    	} catch (Exception e) {
    		log.error("Error occured while reading mule.test.properties", e);
    	}
		System.setProperty("twitter.accessKey", props.getProperty("twitter.accessKey"));
		System.setProperty("twitter.accessSecret", props.getProperty("twitter.accessSecret"));
		System.setProperty("twitter.apiKey", props.getProperty("twitter.apiKey"));
		System.setProperty("twitter.apiSecret", props.getProperty("twitter.apiSecret"));
		System.setProperty("twitter.userid", props.getProperty("twitter.userid"));			
	}

	@Before
	public void setUp() throws Exception {
		stopFlowSchedulers(POLL_FLOW_NAME);
		registerListeners();		
	}

	@After
	public void tearDown() throws Exception {
		stopFlowSchedulers(POLL_FLOW_NAME);		
	}

	@Test
	public void testMainFlow() throws Exception {
		runSchedulersOnce(POLL_FLOW_NAME);
		waitForPollToRun();
		
		Thread.sleep(8000);
		MuleMessage response = muleContext.getClient().request("jms://tweets", 1500);
		assertNotNull(response);
		assertNotNull(response.getPayload());
					
	}

}
