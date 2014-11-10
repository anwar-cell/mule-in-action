
package com.prancingdonkey.service.v2;

import org.mule.api.MuleEventContext;
import org.mule.api.lifecycle.Callable;


public class EmailGateway implements Callable
{

	@Override
	public Object onCall(MuleEventContext eventContext) throws Exception {		
		return "more-processing";
	}
    
}
