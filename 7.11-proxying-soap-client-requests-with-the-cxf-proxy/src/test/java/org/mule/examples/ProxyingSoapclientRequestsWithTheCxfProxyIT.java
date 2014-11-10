package org.mule.examples;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.io.File;

import org.apache.commons.io.FileUtils;
import org.junit.Test;
import org.mule.api.MuleMessage;
import org.mule.api.client.MuleClient;
import org.mule.tck.junit4.FunctionalTestCase;

public class ProxyingSoapclientRequestsWithTheCxfProxyIT extends FunctionalTestCase {

    @Override
    protected String getConfigResources() {
        return "proxying_soap_client_requests_with_the_cxf_proxy.xml";
    }

    @Test
    public void testCanProxySOAPRequests() throws Exception {
        MuleClient client = muleContext.getClient();
        String request = FileUtils.readFileToString(new File("src/test/resources/brew.soap.request.xml"));
        MuleMessage response = client.send("http://localhost:8090/soap", request, null);
        assertNotNull(response);
        System.out.println(response.getPayloadAsString());
        assertTrue(response.getPayloadAsString().contains("<ns2:description>Hobbit IPA"));
        assertTrue(response.getPayloadAsString().contains("<ns2:name>Frodos Lager"));
    }



}
