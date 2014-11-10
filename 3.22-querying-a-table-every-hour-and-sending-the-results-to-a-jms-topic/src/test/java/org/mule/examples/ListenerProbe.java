/**
 * Mule Anypoint Template
 * Copyright (c) MuleSoft, Inc.
 * All rights reserved.  http://www.mulesoft.com
 */

package org.mule.examples;

import org.mule.tck.probe.Probe;

public class ListenerProbe implements Probe {
	private PipelineSynchronizeListener pipelineListener;

	public ListenerProbe(PipelineSynchronizeListener pipelineListener) {
		super();
		this.pipelineListener = pipelineListener;
	}

	@Override
	public boolean isSatisfied() {
		return pipelineListener.readyToContinue();
	}

	@Override
	public String describeFailure() {
		return "The listener never flaged that the notification of flow completion was received";
	}

}
