
package org.mule.examples;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.io.FileUtils;
import org.junit.Test;
import org.mule.api.MuleMessage;
import org.mule.api.client.MuleClient;
import org.mule.tck.junit4.FunctionalTestCase;

public class ExposingTheBrewListingOverRestUsingJaxRsTest extends FunctionalTestCase {
    @Override
    protected String getConfigResources() {
        return "exposing_the_brew_listing_over_rest_using_jax-rs.xml";
    }

    @Test
    public void testCanConsumeRESTfulService() throws Exception {
        MuleClient client = muleContext.getClient();
        Map<String, Object> parameters = new HashMap<String, Object>();
        parameters.put("Content-Type", "application/json");
        parameters.put("http.method", "GET");

        MuleMessage response = client.send("http://localhost:8091/rest/brews", "", parameters);

        assertNotNull(response);
        assertEquals(FileUtils.readFileToString(new File("src/test/resources/brew.rest.response.js")),
                response.getPayloadAsString());
    }

    
}
