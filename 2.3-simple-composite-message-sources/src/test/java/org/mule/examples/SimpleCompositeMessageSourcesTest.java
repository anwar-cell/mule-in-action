
package org.mule.examples;

import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.mule.api.MuleMessage;
import org.mule.module.client.MuleClient;
import org.mule.tck.junit4.FunctionalTestCase;

public class SimpleCompositeMessageSourcesTest extends FunctionalTestCase
{
    private static final String MESSAGE = "test";

	@Override
    protected String getConfigResources()
    {
        return "message-sources.xml";
    }
    
    @Test
    public void testCompositeSource() throws Exception{
    	
    	MuleClient muleClient = new MuleClient(muleContext);
    	MuleMessage response = muleClient.send("jms://main-flow.request-response", MESSAGE, null);
    	assertTrue(response.getPayloadAsString().equals(MESSAGE));
    	response = muleClient.send("jms://main-flow.one-way", MESSAGE, null);
    	assertTrue(response.getPayloadAsString().equals(MESSAGE));
    }
}
