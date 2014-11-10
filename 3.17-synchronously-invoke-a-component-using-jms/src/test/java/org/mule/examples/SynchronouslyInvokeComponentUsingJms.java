
package org.mule.examples;

import static junit.framework.Assert.assertNotNull;
import static junit.framework.Assert.assertTrue;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.junit.BeforeClass;
import org.junit.Test;
import org.mule.api.MuleMessage;
import org.mule.tck.junit4.FunctionalTestCase;

public class SynchronouslyInvokeComponentUsingJms extends FunctionalTestCase {
    
	private static String MESSAGE;

	@Override
    protected String getConfigResources() {
        return "synchronously_invke_a_component_using_jms.xml";
    }
    
	@BeforeClass
	public static void init(){
		try {
			MESSAGE = FileUtils.readFileToString(new File("./src/test/resources/message.json"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
    @Test
    public void testCanListBrews() throws Exception {
        MuleMessage response = muleContext.getClient().send("jms://brews.list", null, null, 15000);
        assertNotNull(response);              
        assertTrue(response.getPayloadAsString().equals(MESSAGE));
    }

}
