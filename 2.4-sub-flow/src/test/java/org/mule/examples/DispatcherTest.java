
package org.mule.examples;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;
import org.mule.module.client.MuleClient;
import org.mule.tck.junit4.FunctionalTestCase;

public class DispatcherTest extends FunctionalTestCase
{
    @Override
    protected String getConfigResources()
    {
        return "choice-dispatcher-stubs.xml";
    }

    @Test
    public void requestDispatcherSubFlow() throws Exception
    {
        MuleClient muleClient = new MuleClient(muleContext);
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("valid", true);
        
        assertThat(
            muleClient.send("http://localhost:8081", "foo",
            		map)
                .getPayloadAsString(), is("foo:valid"));
        map.put("valid", false);
        assertThat(
            muleClient.send("http://localhost:8082", "foo",
                map)
                .getPayloadAsString(), is("foo:invalid"));
    }
}
