
package org.mule.examples;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.io.FileUtils;
import org.junit.BeforeClass;
import org.junit.Test;
import org.mule.api.MuleMessage;
import org.mule.module.client.MuleClient;
import org.mule.tck.junit4.FunctionalTestCase;

public class XsltTestCase extends FunctionalTestCase
{
	private static String PAYLOAD;
    
    @Override
    protected String getConfigResources()
    {
        return "xslt.xml";
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
    public void testSimple() throws Exception
    {
        MuleClient muleClient = new MuleClient(muleContext);

        MuleMessage result = muleClient.send("http://localhost:8081/simple", PAYLOAD, null);
        assertThat(result, is(notNullValue()));
        assertThat(result.getPayload(), is(notNullValue()));
    }

    @Test
    public void testParam() throws Exception
    {
        MuleClient muleClient = new MuleClient(muleContext);

        MuleMessage result = muleClient.send("http://localhost:8081/param", PAYLOAD, null);
        assertThat(result, is(notNullValue()));
        assertThat(result.getPayload(), is(notNullValue()));
    }

    @Test
    public void testParamExpr() throws Exception
    {
        MuleClient muleClient = new MuleClient(muleContext);

        Map<String, Object> headers = new HashMap<String, Object>(1);
        headers.put("discount", "10");

        MuleMessage result = muleClient.send("http://localhost:8081/paramexpr", PAYLOAD, headers);
        assertThat(result, is(notNullValue()));
        assertThat(result.getPayload(), is(notNullValue()));
    }

    @Test
    public void testIdle() throws Exception
    {
        MuleClient muleClient = new MuleClient(muleContext);

        MuleMessage result = muleClient.send("http://localhost:8081/idle", PAYLOAD, null);
        assertThat(result, is(notNullValue()));
        assertThat(result.getPayload(), is(notNullValue()));
    }

}
