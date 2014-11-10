
package org.mule.examples;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNotNull;

import org.junit.Test;
import org.mule.api.MuleMessage;
import org.mule.tck.junit4.FunctionalTestCase;

public class PublishReportToJmsTopicTest extends FunctionalTestCase {
    @Override
    protected String getConfigResources() {
        return "publish_report_to_jms_topic.xml";
    }

    @Test
    public void testCanDispatchExpenseReports() throws Exception {
        muleContext.getClient().dispatch("http://localhost:8080/expenses/status", "foo", null);
        MuleMessage response = muleContext.getClient().request("jms://topic:expenses.status", 1500);
        assertNotNull(response);
        assertNotNull(response.getPayload());
        assertEquals("foo", response.getPayloadAsString());
    }
    
}
