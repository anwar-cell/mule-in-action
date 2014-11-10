
package org.mule.examples;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.junit.BeforeClass;
import org.junit.Test;
import org.mule.api.MuleMessage;
import org.mule.module.client.MuleClient;
import org.mule.tck.junit4.FunctionalTestCase;

public class UsingXPathTest extends FunctionalTestCase
{

    private static String PAYLOAD;
    
    @Override
    protected String getConfigResources()
    {
        return "xpath.xml";
    }

    @BeforeClass
    public static void init(){
    	try {
			PAYLOAD = FileUtils.readFileToString(new File("./src/test/resources/message.xml"));
		} catch (IOException e) {
			e.printStackTrace();
		}
    }
    
    @Test
    public void testXpathExpression() throws Exception
    {
        final MuleClient muleClient = new MuleClient(muleContext);

        final MuleMessage result = muleClient.send("http://localhost:8081", PAYLOAD, null);
        assertThat(result, is(notNullValue()));
        assertThat(result.getInboundProperty("productId"), is(notNullValue()));
    }

    @Test
    public void testAlias() throws Exception
    {
        final MuleClient muleClient = new MuleClient(muleContext);

        final MuleMessage result = muleClient.send("http://localhost:8082", PAYLOAD, null);
        assertThat(result, is(notNullValue()));
        assertThat(result.getPayload(), is(notNullValue()));
        assertTrue(result.getPayloadAsString().contains("DTMNodeList"));
    }

}
