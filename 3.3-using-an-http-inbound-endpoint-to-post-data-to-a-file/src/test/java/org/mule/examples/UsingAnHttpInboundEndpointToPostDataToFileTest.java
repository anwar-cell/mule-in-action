package org.mule.examples;

import static org.junit.Assert.assertEquals;

import java.io.File;
import java.util.concurrent.CountDownLatch;

import org.junit.BeforeClass;
import org.junit.Test;
import org.mule.api.context.notification.EndpointMessageNotificationListener;
import org.mule.api.context.notification.ServerNotification;
import org.mule.tck.junit4.FunctionalTestCase;
import org.mule.util.FileUtils;

public class UsingAnHttpInboundEndpointToPostDataToFileTest extends FunctionalTestCase {

    CountDownLatch copyExpenseReportLatch;
    CountDownLatch callbackLatch;

    @BeforeClass
    public static void setupDirectories() throws Exception {
        File dataDirectory = new File("./data");
        if (dataDirectory.exists()) {
            FileUtils.deleteDirectory(dataDirectory);
        }
        dataDirectory.mkdirs();
        new File("./data/expenses/1/in").mkdirs();
        new File("./data/expenses/out").mkdirs();
        new File("./data/expenses/2/in").mkdirs();
        new File("./data/expenses/status").mkdirs();
    }

    @Override
    protected void doSetUp() throws Exception {
        super.doSetUp();
        copyExpenseReportLatch = new CountDownLatch(1);
        callbackLatch = new CountDownLatch(1);
        muleContext.registerListener(new EndpointMessageNotificationListener() {
            public void onNotification(final ServerNotification notification) {
                if ("copyExpenseReports".equals(notification.getResourceIdentifier())
                        && "receive".equals(notification.getActionName())) {
                    copyExpenseReportLatch.countDown();
                }

                if ("expenseReportCallback".equals(notification.getResourceIdentifier())
                        && "end dispatch".equals(notification.getActionName())) {
                    callbackLatch.countDown();
                }
            }
        });
    }

    @Override
    protected String getConfigResources() {
        return "using_an_http_inbound_endpoint_to_post_data_to_a_file.xml";
    }

        
    @Test
    public void testCanPerformCallback() throws Exception {
        assertEquals(0,FileUtils.listFiles(new File("./data/expenses/status"), new String[]{"xml"}, false).size());
        muleContext.getClient().dispatch("http://localhost:8080/expenseReportCallback","FOO",null);
        Thread.sleep(1500);
        assertEquals(1,FileUtils.listFiles(new File("./data/expenses/status"), new String[]{"xml"}, false).size());

    }


}
