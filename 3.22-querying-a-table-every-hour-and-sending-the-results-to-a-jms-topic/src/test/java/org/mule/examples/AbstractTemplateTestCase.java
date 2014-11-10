/**
 * Mule Anypoint Template
 * Copyright (c) MuleSoft, Inc.
 * All rights reserved.  http://www.mulesoft.com
 */

package org.mule.examples;

import java.util.Map;

import org.junit.Rule;
import org.mule.MessageExchangePattern;
import org.mule.api.MuleEvent;
import org.mule.context.notification.NotificationException;
import org.mule.processor.chain.SubflowInterceptingChainLifecycleWrapper;
import org.mule.tck.junit4.FunctionalTestCase;
import org.mule.tck.junit4.rule.DynamicPort;
import org.mule.tck.probe.PollingProber;
import org.mule.tck.probe.Prober;
import org.mule.transport.NullPayload;

public abstract class AbstractTemplateTestCase extends FunctionalTestCase {
	
	protected static final int TIMEOUT_SEC = 120;
	protected static final String POLL_FLOW_NAME = "pollForStuckOrders";
	
	protected final Prober pollProber = new PollingProber(60000, 1000l);
	protected final PipelineSynchronizeListener pipelineListener = new PipelineSynchronizeListener(POLL_FLOW_NAME);

	@Rule
	public DynamicPort port = new DynamicPort("http.port");

	@Override
	protected String getConfigResources() {
		return "sampling_the_twitter_public_status_update_stream.xml";
	}
	

	protected void registerListeners() throws NotificationException {
		muleContext.registerListener(pipelineListener);
	}

	protected void waitForPollToRun() {
		System.out.println("Waiting for poll to run ones...");
		pollProber.check(new ListenerProbe(pipelineListener));
		System.out.println("Poll flow done");
	}

	@SuppressWarnings("unchecked")
	protected Map<String, Object> invokeRetrieveFlow(SubflowInterceptingChainLifecycleWrapper flow, Map<String, Object> payload) throws Exception {
		MuleEvent event = flow.process(getTestEvent(payload, MessageExchangePattern.REQUEST_RESPONSE));
		Object resultPayload = event.getMessage()
									.getPayload();

		if (resultPayload instanceof NullPayload) {
			return null;
		} else {
			return (Map<String, Object>) resultPayload;
		}
	}	

}