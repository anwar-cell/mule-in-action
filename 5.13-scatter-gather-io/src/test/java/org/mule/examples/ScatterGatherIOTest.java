package org.mule.examples;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.mule.api.MuleMessage;
import org.mule.tck.junit4.FunctionalTestCase;

public class ScatterGatherIOTest extends FunctionalTestCase {

    @Override
    protected String getConfigResources() {
        return  "scatter_gather_io.xml";
    }

    @Test
    public void testCanAggregateResponses() throws Exception {
    	MuleMessage response = muleContext.getClient().send("http://localhost:8081", "", null);
    	assertEquals("32.8", response.getPayloadAsString());
    }
}
