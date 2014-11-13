
package org.mule.examples;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.junit.Before;
import org.junit.Test;
import org.mule.api.MuleMessage;
import org.mule.module.client.MuleClient;
import org.mule.tck.junit4.FunctionalTestCase;

public class SubflowIT extends FunctionalTestCase
{
	private String MESSAGE;
	private String MESSAGE_TEXT = "test";
	
	@Before
	public void setUp(){
		try {
			MESSAGE = FileUtils.readFileToString(new File("./src/test/resources/message.xml"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
    @Override
    protected String getConfigResources()
    {
        return "sub-flow.xml";
    }

    @Test
    public void mainFlow1Test() throws Exception
    {
        MuleClient muleClient = new MuleClient(muleContext);
        
        MuleMessage response = muleClient.send("http://localhost:8080", MESSAGE, null);
        assertNotNull(response);
        assertEquals(response.getPayloadAsString(), "more-processing: " + MESSAGE_TEXT);
                         
    }
    
    @Test
    public void mainFlow2Test() throws Exception
    {
        MuleClient muleClient = new MuleClient(muleContext);
        
        MuleMessage response = muleClient.send("jms://v1.email", MESSAGE, null);
        assertNotNull(response);
        assertEquals(response.getPayloadAsString(), "more-processing: " + MESSAGE_TEXT);
                        
    }
    
    
}
