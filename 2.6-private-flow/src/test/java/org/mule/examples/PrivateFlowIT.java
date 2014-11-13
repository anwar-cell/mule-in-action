package org.mule.examples;



import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.junit.Before;
import org.junit.Test;
import org.mule.api.MuleEvent;
import org.mule.api.MuleMessage;
import org.mule.api.client.MuleClient;
import org.mule.tck.functional.FunctionalTestComponent;
import org.mule.tck.junit4.FunctionalTestCase;

public class PrivateFlowIT extends FunctionalTestCase
{
    private static final String SYNC_PRIVATE_FLOW_NAME = "sync-private-flow";
    private static final String ASYNC_PRIVATE_FLOW_NAME = "async-private-flow";
    private static final String SYNC_OR_ASYNC_PRIVATE_FLOW_NAME = "sync-or-async-private-flow";

    private static final String SYNC_PRIVATE_FLOW_MSG = "spf";
    private static final String ASYNC_PRIVATE_FLOW_MSG = "apf";
    private static final String SYNC_OR_ASYNC_PRIVATE_FLOW_MSG = "soapf";

    private static final String UNITTEST_THREAD_NAME = "ActiveMQ Session Task-1";
    private static final String UNITTEST_THREAD = "ActiveMQ Session Task";
    private static final String ASYNC_PRIVATE_FLOW_THREAD_NAME = "async-private-flow.stage1.01";
    private static final String SYNC_OR_ASYNC_PRIVATE_FLOW_THREAD_NAME = "sync-or-async-private-flow.stage1.02";
    private static final String ASYNC_MAIN_FLOW_THREAD_NAME = "main-flow.stage1.02";

    private static final String ONE_WAY_ENDPOINT = "jms://main-flow.one-way";
    private static final String REQUEST_RESPONSE_ENDPOINT = "jms://main-flow.request-response";
    private MuleClient muleClient;
	private String MESSAGE;
	private String MESSAGE_TEXT = "test";

    @Override
    protected String getConfigResources()
    {
        return "private-flow.xml";
    }

    @Override
    protected void doSetUp() throws Exception
    {
        super.doSetUp();
        muleClient = muleContext.getClient();
        try {
			MESSAGE = FileUtils.readFileToString(new File("./src/test/resources/message.xml"));
		} catch (IOException e) {
			e.printStackTrace();
		}
    }

    @Before
    public void setThreadName()
    {
        Thread.currentThread().setName(UNITTEST_THREAD_NAME);
    }

    @Test
    public void syncEventToSyncPrivateFlow() throws Exception
    {
        muleClient.send(REQUEST_RESPONSE_ENDPOINT,
            SYNC_PRIVATE_FLOW_MSG, null);

        String dispatched = ponderForMessageInTestComponent(SYNC_PRIVATE_FLOW_NAME);
        assertTrue(dispatched.contains(SYNC_PRIVATE_FLOW_NAME + "@" + UNITTEST_THREAD_NAME));
        assertTrue(dispatched.contains(SYNC_PRIVATE_FLOW_MSG + ".main1@" + UNITTEST_THREAD_NAME));
        

    }

    @Test
    public void syncEventToAsyncPrivateFlow() throws Exception
    {
        MuleMessage response = muleClient.send(REQUEST_RESPONSE_ENDPOINT,
            ASYNC_PRIVATE_FLOW_MSG, null);
        assertTrue(response.getPayloadAsString().contains(ASYNC_PRIVATE_FLOW_MSG + ".main1@" + UNITTEST_THREAD));
        
        String dispatched = ponderForMessageInTestComponent(ASYNC_PRIVATE_FLOW_NAME);
        assertTrue(dispatched.contains(ASYNC_PRIVATE_FLOW_NAME + "@" + ASYNC_PRIVATE_FLOW_THREAD_NAME));
        assertTrue(dispatched.contains(ASYNC_PRIVATE_FLOW_MSG + ".main1@" + UNITTEST_THREAD));
    }

    @Test
    public void syncEventToSyncOrAsyncPrivateFlow() throws Exception
    {
        MuleMessage response = muleClient.send(REQUEST_RESPONSE_ENDPOINT,
            SYNC_OR_ASYNC_PRIVATE_FLOW_MSG, null);

        String dispatched = ponderForMessageInTestComponent(SYNC_OR_ASYNC_PRIVATE_FLOW_NAME);
        
        assertTrue(dispatched.contains(SYNC_OR_ASYNC_PRIVATE_FLOW_NAME + "@" + UNITTEST_THREAD_NAME));
        assertTrue(dispatched.contains(SYNC_OR_ASYNC_PRIVATE_FLOW_MSG + ".main1@" + UNITTEST_THREAD_NAME));
                
        assertThat(response.getPayloadAsString(),
            is(dispatched + ".main2@" + UNITTEST_THREAD_NAME));
    }

    @Test
    public void asyncEventToSyncPrivateFlow() throws Exception
    {
        muleClient.dispatch(ONE_WAY_ENDPOINT, SYNC_PRIVATE_FLOW_MSG, null);

        String dispatched = ponderForMessageInTestComponent(SYNC_PRIVATE_FLOW_NAME);
        assertThat(dispatched, is(SYNC_PRIVATE_FLOW_MSG + ".main1@"
                                  + ASYNC_MAIN_FLOW_THREAD_NAME + "."
                                  + SYNC_PRIVATE_FLOW_NAME + "@"
                                  + ASYNC_MAIN_FLOW_THREAD_NAME));
    }

    @Test
    public void asyncEventToAsyncPrivateFlow() throws Exception
    {
        muleClient.dispatch(ONE_WAY_ENDPOINT, ASYNC_PRIVATE_FLOW_MSG, null);

        String dispatched = ponderForMessageInTestComponent(ASYNC_PRIVATE_FLOW_NAME);
        assertThat(dispatched, is(ASYNC_PRIVATE_FLOW_MSG + ".main1@"
                                  + ASYNC_MAIN_FLOW_THREAD_NAME + "."
                                  + ASYNC_PRIVATE_FLOW_NAME + "@"
                                  + ASYNC_PRIVATE_FLOW_THREAD_NAME));
    }

    @Test
    public void asyncEventToSyncOrAsyncPrivateFlow() throws Exception
    {
        muleClient.dispatch(ONE_WAY_ENDPOINT,
            SYNC_OR_ASYNC_PRIVATE_FLOW_MSG, null);

        String dispatched = ponderForMessageInTestComponent(SYNC_OR_ASYNC_PRIVATE_FLOW_NAME);
        assertThat(dispatched,
            is(SYNC_OR_ASYNC_PRIVATE_FLOW_MSG + ".main1@"
               + ASYNC_MAIN_FLOW_THREAD_NAME + "."
               + SYNC_OR_ASYNC_PRIVATE_FLOW_NAME + "@"
               + SYNC_OR_ASYNC_PRIVATE_FLOW_THREAD_NAME));
    }

    private String ponderForMessageInTestComponent(String flowName)
        throws Exception
    {
        FunctionalTestComponent ftc = getFunctionalTestComponent(flowName);

        do
        {
            int count = ftc.getReceivedMessagesCount();
            if (count != 0)
            {
                return ftc.getLastReceivedMessage().toString();
            }
            Thread.sleep(50L);
        }
        while (true);
    }

    @Test
    public void legacyAdapterPrivateFlow() throws Exception{
    	MuleEvent result = runFlow("legacyAdapterPrivateFlow", MESSAGE);
    	assertEquals(result.getMessage().getPayloadAsString(), MESSAGE_TEXT);
    }
}
