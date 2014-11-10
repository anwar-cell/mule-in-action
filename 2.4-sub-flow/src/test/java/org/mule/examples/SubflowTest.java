
package org.mule.examples;

import static org.junit.Assert.assertNotNull;

import org.junit.Test;
import org.mule.api.MuleMessage;
import org.mule.module.client.MuleClient;
import org.mule.tck.junit4.FunctionalTestCase;

public class SubflowTest extends FunctionalTestCase
{
    @Override
    protected String getConfigResources()
    {
        return "sub-flow.xml";
    }

    @Test
    public void subFlowTest() throws Exception
    {
        MuleClient muleClient = new MuleClient(muleContext);
        
        muleClient.send("http://localhost:8080", "subflow1", null);
        
        MuleMessage response = muleContext.getClient().request("jms://subflow1", 2500);        
        assertNotNull(response);
        assertNotNull(response.getPayload()); 
    }
}
