
package org.mule.examples;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import org.junit.Test;
import org.mule.DefaultMuleMessage;
import org.mule.tck.functional.FunctionalTestComponent;
import org.mule.tck.junit4.FunctionalTestCase;

public class MessageAttachmentsTest extends FunctionalTestCase
{
    @Override
    protected String getConfigResources()
    {
        return "message-attachments.xml";
    }
    
    @Test
    public void processAttachments() throws Exception
    {
        muleContext.getClient().dispatch("http://localhost:8081",
        		new DefaultMuleMessage("",
        	            muleContext));

        FunctionalTestComponent ftc = getFunctionalTestComponent("email-order-processor");
        while (ftc.getReceivedMessagesCount() < 2)
        {
            Thread.yield();
            Thread.sleep(100L);
        }

        assertThat(
            ftc.getReceivedMessage(1) instanceof javax.activation.DataHandler,
            is(true));
        
        assertThat(
            ftc.getReceivedMessage(2) instanceof javax.activation.DataHandler,
            is(true));
    }

}
