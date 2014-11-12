
package org.mule.examples;

import org.mule.api.MuleEventContext;
import org.mule.api.lifecycle.Callable;


public class CustomProcessor implements Callable
{

	@Override
	public Object onCall(MuleEventContext eventContext) throws Exception {		
		return "more-processing: " + eventContext.getMessage().getPayloadAsString();
	}
    
}
