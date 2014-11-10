package org.mule.examples;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.io.File;
import java.util.concurrent.CountDownLatch;

import org.junit.Test;
import org.mule.DefaultMuleMessage;
import org.mule.api.MuleMessage;
import org.mule.api.context.notification.ComponentMessageNotificationListener;
import org.mule.api.context.notification.ServerNotification;
import org.mule.tck.junit4.FunctionalTestCase;
import org.mule.util.FileUtils;


public class IdempotentOrderRouterFunctionalTestCase extends FunctionalTestCase {

    private static final String REPLY = "Processing order.";

	@Override
    protected String getConfigResources() {
        return "filters.xml";
    }

    CountDownLatch latch = new CountDownLatch(2);

    @Override
    protected void doSetUp() throws Exception {
        super.doSetUp();

        muleContext.registerListener(new ComponentMessageNotificationListener() {
            public void onNotification(final ServerNotification notification) {
                latch.countDown();
            }
        });
    }


    @Test
    public void testCannotProcessMultipleMessages() throws Exception {
        MuleMessage request = new DefaultMuleMessage(FileUtils.readFileToString(
                new File("src/test/resources/orders.xml")),muleContext);
        System.out.println("req: " + FileUtils.readFileToString(
                new File("src/test/resources/orders.xml")));
        MuleMessage result = muleContext.getClient().send("jms://topic:orders", request);

//        assertTrue(latch.await(15, TimeUnit.SECONDS));
        assertNotNull(result);
        assertEquals(REPLY, result.getPayloadAsString());
    }

}
