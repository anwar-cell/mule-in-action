
package org.mule.examples;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;

import org.junit.Test;
import org.mule.api.MuleMessage;
import org.mule.module.client.MuleClient;
import org.mule.tck.junit4.FunctionalTestCase;

public class CompressingPayloadUsingGzipTest extends FunctionalTestCase
{

    private static final String PAYLOAD = "The quick fox jumped over the lazy dog";

	@Override
    protected String getConfigResources()
    {
        return "compress.xml";
    }

    @Test
    public void testCompress() throws Exception
    {
        MuleClient muleClient = new MuleClient(muleContext);

        muleClient.dispatch("http://localhost:8081", PAYLOAD, null);

        MuleMessage result = muleClient.request("jms://uncompressedDataQueue", 10000);
        assertThat(result, is(notNullValue()));
        assertThat(result.getPayload(), is(notNullValue()));
        assertThat((String) result.getPayload(), is(equalTo(PAYLOAD)));
    }

}
