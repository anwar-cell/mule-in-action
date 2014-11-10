package org.mule.examples;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;
import org.mule.api.MuleMessage;
import org.mule.module.client.MuleClient;
import org.mule.tck.junit4.FunctionalTestCase;
import org.mule.transport.NullPayload;

public class ReliabilityRoutingWithTheUntilSuccessfulMessageProcessor extends FunctionalTestCase
{
	private String REPLY = "Completed";
	
	@Override
    protected String getConfigResources()
    {
        return "reliability_routing_with_the_until-successful_message_processor.xml";
    }

    @Test
    public void httpGetToFlowUrlEchoesSentMessage() throws Exception
    {
        MuleClient client = new MuleClient(muleContext);
        Map<String, Object> props = new HashMap<String, Object>();
        props.put("http.method", "GET");
        MuleMessage result = client.send("http://localhost:8081", "", props);
        assertNotNull(result);
        assertFalse(result.getPayload() instanceof NullPayload);
        assertEquals(REPLY, result.getPayloadAsString());
    }

}
