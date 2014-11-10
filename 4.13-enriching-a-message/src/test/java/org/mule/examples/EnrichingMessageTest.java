
package org.mule.examples;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.mule.api.MuleMessage;
import org.mule.module.client.MuleClient;
import org.mule.tck.junit4.FunctionalTestCase;

public class EnrichingMessageTest extends FunctionalTestCase
{
    @Override
    protected String getConfigResources()
    {
        return "message-enricher.xml";
    }

    @Test
    public void singleEnrichment() throws Exception
    {
    	MuleClient muleClient = new MuleClient(muleContext);
    	MuleMessage result = muleClient.send("http://localhost:8081", "ignored", null);
    	assertThat(result, is(notNullValue()));
        assertThat(result.getPayload(), is(notNullValue()));
        assertThat(result.getPayloadAsString(), is("USD"));
    }

    @Test
    public void multipleEnrichment() throws Exception
    {
    	MuleClient muleClient = new MuleClient(muleContext);
    	MuleMessage result = muleClient.send("http://localhost:8082", "ignored", null);
    	assertThat(result, is(notNullValue()));
    	assertTrue(result.getPayloadAsString().contains("USD"));
        assertTrue(result.getPayloadAsString().contains("ABC123"));
    }
}
