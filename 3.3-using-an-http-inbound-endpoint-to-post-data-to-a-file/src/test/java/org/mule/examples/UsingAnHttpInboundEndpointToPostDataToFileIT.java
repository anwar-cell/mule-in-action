package org.mule.examples;

import static org.junit.Assert.assertEquals;

import java.io.File;

import org.junit.BeforeClass;
import org.junit.Test;
import org.mule.tck.junit4.FunctionalTestCase;
import org.mule.util.FileUtils;

public class UsingAnHttpInboundEndpointToPostDataToFileIT extends FunctionalTestCase {

    @BeforeClass
    public static void setupDirectories() throws Exception {
        File dataDirectory = new File("./data");
        if (dataDirectory.exists()) {
            FileUtils.deleteDirectory(dataDirectory);
        }    
        new File("./data").mkdirs();
    }   

    @Override
    protected String getConfigResources() {
        return "using_an_http_inbound_endpoint_to_post_data_to_a_file.xml";
    }

        
    @Test
    public void testCanPerformCallback() throws Exception {
        assertEquals(0, FileUtils.listFiles(new File("./data"), new String[]{"xml"}, false).size());
        muleContext.getClient().dispatch("http://localhost:8080/storedata", "FOO", null);
        Thread.sleep(1500);
        assertEquals(1, FileUtils.listFiles(new File("./data"), new String[]{ "xml" }, false).size());

    }


}
