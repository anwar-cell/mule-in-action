package org.mule.examples;



import static org.junit.Assert.assertNotNull;

import org.apache.commons.lang.StringUtils;
import org.junit.Test;
import org.mule.api.MuleMessage;
import org.mule.module.client.MuleClient;
import org.mule.tck.junit4.FunctionalTestCase;

public class BackEndOrderProcessingTest extends FunctionalTestCase
{
	
    @Override
    protected String getConfigResources()
    {
        return "back-end_order_processing.xml";
    }

    @Test
    public void testObjectStore() throws Exception
    {
        final MuleClient muleClient = new MuleClient(muleContext);
        muleClient.dispatch("jms://order.submit", StringUtils.EMPTY, null);
        MuleMessage response = muleClient.request("jms://events.orders.completed", 1500);
        assertNotNull(response);
        assertNotNull(response.getPayload());
               
    }

}
