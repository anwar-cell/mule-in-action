package org.mule.examples;

import static org.junit.Assert.assertTrue;

import java.io.File;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import org.junit.BeforeClass;
import org.junit.Test;
import org.mule.api.MuleEventContext;
import org.mule.tck.functional.EventCallback;
import org.mule.tck.junit4.FunctionalTestCase;
import org.mule.util.FileUtils;

public class PostingDataUsingHttpTransportIT extends FunctionalTestCase {

    CountDownLatch copyExpenseReportLatch;
    CountDownLatch callbackLatch;

    @BeforeClass
    public static void setupDirectories() throws Exception {
        File dataDirectory = new File("./data");
        if (dataDirectory.exists()) {
            FileUtils.deleteDirectory(dataDirectory);
        }
        dataDirectory.mkdirs();
        new File("./data/expenses/in").mkdirs();        
    }    

    @Override
    protected String getConfigResources() {
        return "posting_data_using_http_transport.xml";
    }

    
    @Test
    public void testCanPostExpenseReportsFromADirectory() throws Exception {
        final CountDownLatch latch = new CountDownLatch(1);
        EventCallback callback = new EventCallback()
        {
            public void eventReceived(MuleEventContext context, Object component)
                    throws Exception
            {
                latch.countDown();
            }
        };

        getFunctionalTestComponent("dummyHttpServer").setEventCallback(callback);

        FileUtils.writeStringToFile(new File("./data/expenses/in/foo.xls"),"Foo");
        assertTrue(latch.await(15000,TimeUnit.SECONDS));
    }

}
