package org.mule.examples;

import static junit.framework.Assert.assertNotNull;

import org.junit.Test;
import org.mule.api.MuleMessage;
import org.mule.tck.junit4.FunctionalTestCase;

public class DispatchCoolingAlertsFunctionalTestCase extends FunctionalTestCase {


    @Override
    protected String getConfigResources() {
        return "filters.xml";
    }
        
    @Test
    public void testCanDispatchCoolingAlerts() throws Exception {
    	muleContext.getClient().dispatch("http://localhost:8081", "SEVERE", null);
    	MuleMessage response =  muleContext.getClient().request("jms://topic:cooling.alerts", 15000);
        assertNotNull(response);
    }
}


