
package org.mule.examples;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;

import org.junit.Test;
import org.mule.api.MuleMessage;
import org.mule.module.client.MuleClient;
import org.mule.tck.junit4.FunctionalTestCase;

public class UsingXstreamTest extends FunctionalTestCase
{

    @Override
    protected String getConfigResources()
    {
        return "xstream.xml";
    }

    @Test
    public void testMuleMessage() throws Exception
    {
        MuleClient muleClient = new MuleClient(muleContext);

        MuleMessage result = muleClient.send("http://localhost:8081/simple", "dummy", null);
        assertThat(result, is(notNullValue()));
        assertThat(result.getPayload(), is(notNullValue()));        
    }

}
