
package org.mule.examples;

import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.junit.Assert.assertThat;

import java.io.File;
import java.io.IOException;
import java.util.Map;

import org.apache.commons.io.FileUtils;
import org.external.ExternalItem;
import org.junit.BeforeClass;
import org.junit.Test;
import org.mule.api.MuleMessage;
import org.mule.module.client.MuleClient;
import org.mule.tck.junit4.FunctionalTestCase;

import com.prancingdonkey.model.json.Provider;

public class JsonRequestsTest extends FunctionalTestCase
{
	private static String PAYLOAD;
	
    @Override
    protected String getConfigResources()
    {
        return "json.xml";
    }
    
    @BeforeClass
    public static void init(){
    	try {
			PAYLOAD = FileUtils.readFileToString(new File("./src/test/resources/message.json"));
		} catch (IOException e) {
			e.printStackTrace();
		}

    }

    @Test
    public void testRegularJson() throws Exception
    {
        MuleClient muleClient = new MuleClient(muleContext);

        Provider payload = new Provider();
        payload.setName("test name");

        MuleMessage result = muleClient.send("vm://json-marshalling.in", payload, null);
        assertThat(result, is(notNullValue()));
        assertThat(result.getPayload(), is(notNullValue()));
        assertThat(result.getPayload(), is(instanceOf(Provider.class)));
    }

    @Test
    public void testMixinJson() throws Exception
    {
        MuleClient muleClient = new MuleClient(muleContext);

        ExternalItem payload = new ExternalItem();

        payload.setItemNumber("1234");
        payload.setUnwantedValue("notWanted");

        MuleMessage result = muleClient.send("vm://json-marshalling-mixin.in", payload, null);
        assertThat(result, is(notNullValue()));
        assertThat(result.getPayload(), is(notNullValue()));
        assertThat(result.getPayload(), is(instanceOf(ExternalItem.class)));
        assertThat(((ExternalItem) result.getPayload()).getUnwantedValue(), is(nullValue()));
    }
    
	@Test
	@SuppressWarnings({ "rawtypes" })
    public void testJsonQuery() throws Exception
    {
        MuleClient muleClient = new MuleClient(muleContext);

        
        MuleMessage result = muleClient.send("vm://json-query.in", PAYLOAD, null);
        assertThat(result, is(notNullValue()));
        assertThat(result.getPayload(), is(notNullValue()));
        assertThat(result.getPayload(), is(instanceOf(Map.class)));
        
        Map mapResult = (Map) result.getPayload();
        assertThat(mapResult.get("requestType"), is(instanceOf(String.class)));
        assertThat((String) mapResult.get("requestType"), is("availability"));
    }

}
