package org.mule.examples;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNotNull;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.io.FileUtils;
import org.junit.Test;
import org.mule.api.MuleMessage;
import org.mule.tck.junit4.FunctionalTestCase;


public class UsingMuleAsServiceMediationLayerTest extends FunctionalTestCase  {

    @Override
    protected String getConfigResources() {
        return "using_mule_as_a_service_mediation_layer.xml";
    }


    @Test
    public void testCanProxyMessages() throws Exception {
        String order = FileUtils.readFileToString(new File("src/test/resources/order.xml"));

        Map<String, Object> properties = new HashMap<String, Object>();
        properties.put("Authorization","Basic am9objpqb2hu");

        MuleMessage response = muleContext.getClient().send("http://localhost:8080", order, properties);
        assertNotNull(response);
        assertEquals("SUCCESS", response.getPayloadAsString());
    }
}
