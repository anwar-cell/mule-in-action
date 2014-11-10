package org.mule.examples;

import java.io.File;

import org.apache.commons.io.FileUtils;
import org.junit.Assert;
import org.junit.Test;
import org.mule.api.MuleMessage;
import org.mule.tck.junit4.FunctionalTestCase;


public class ValidateAndDispatchCoolingAlertsFunctionalTestCase extends FunctionalTestCase {

    @Override
    protected String getConfigResources() {
        return "filters.xml";
    }

    @Test
    public void testCanValidate() throws Exception {
        String orders = FileUtils.readFileToString(new File("src/test/resources/orders.xml"));
        muleContext.getClient().dispatch("http://localhost:8081/orders",orders,null);
        MuleMessage result = muleContext.getClient().request("jms://topic:cooling.alerts", 15000);
        Assert.assertNotNull(result);

    }
}
