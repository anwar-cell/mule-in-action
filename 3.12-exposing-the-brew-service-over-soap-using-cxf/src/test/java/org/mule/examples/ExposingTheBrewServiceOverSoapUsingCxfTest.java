
package org.mule.examples;

import static junit.framework.Assert.assertTrue;
import static org.junit.Assert.assertNotNull;

import java.io.File;

import org.apache.commons.io.FileUtils;
import org.custommonkey.xmlunit.Diff;
import org.junit.Test;
import org.mule.api.MuleMessage;
import org.mule.api.client.MuleClient;
import org.mule.tck.junit4.FunctionalTestCase;

public class ExposingTheBrewServiceOverSoapUsingCxfTest extends FunctionalTestCase {
    @Override
    protected String getConfigResources() {
        return "exposing_the_brew_service_over_soap_using_cxf.xml";
    }
    
    @Test
    public void testCanConsumeSOAPService() throws Exception {
        MuleClient client = muleContext.getClient();
        String request = FileUtils.readFileToString(new File("src/test/resources/brew.soap.request.xml"));
        MuleMessage response = client.send("http://localhost:8090/soap", request, null);
        assertNotNull(response);
        Diff diff = new Diff(FileUtils.readFileToString(new File("src/test/resources/brew.soap.response.xml")),
                response.getPayloadAsString());
        assertTrue( diff.toString(),diff.similar());
    }
}
