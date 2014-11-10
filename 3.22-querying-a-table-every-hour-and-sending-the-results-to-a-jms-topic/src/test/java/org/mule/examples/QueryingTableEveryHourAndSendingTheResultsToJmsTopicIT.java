package org.mule.examples;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNotNull;

import java.io.FileInputStream;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.Map;
import java.util.Properties;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.mule.api.MuleMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class QueryingTableEveryHourAndSendingTheResultsToJmsTopicIT extends AbstractTemplateTestCase {

	private static final String PATH_TO_TEST_PROPERTIES = "./src/test/resources/mule.test.properties";
	private static final Logger log = LoggerFactory.getLogger(QueryingTableEveryHourAndSendingTheResultsToJmsTopicIT.class); 

    private static String MYSQL_URL;
    private static String ADMIN;
    private static String PASSWORD;
    

	@Override
    protected String getConfigResources() {
        return "querying_a_table_every_hour_and_sending_the_results_to_a_jms_topic.xml";
    }

	@BeforeClass
	public static void init() {
		final Properties props = new Properties();
    	try {
    	props.load(new FileInputStream(PATH_TO_TEST_PROPERTIES));
    	} catch (Exception e) {
    		log.error("Error occured while reading mule.test.properties", e);
    	}
    	System.setProperty("jdbc.url", props.getProperty("jdbc.url"));
    	MYSQL_URL = props.getProperty("database.url");
    	ADMIN = props.getProperty("database.user");
    	PASSWORD = props.getProperty("database.password");
        createDatabase();				
	}
	
    @Before
    public void doSetUp() throws Exception {
        super.doSetUp();
        stopFlowSchedulers(POLL_FLOW_NAME);
        
        registerListeners();
        
    }
    
    
    @After
    public void doTearDown() throws Exception {
    	stopFlowSchedulers(POLL_FLOW_NAME);    	
    	super.doTearDown();
    }
    
    @AfterClass
    public static void destroyDB() throws Exception {
    	java.sql.Connection con = DriverManager.getConnection(MYSQL_URL, ADMIN, PASSWORD);
    	
		Statement stmt = con.createStatement();
		
		stmt.execute("DROP DATABASE company");
		
    }

    @Test
    public void testCanPollForStuckOrders() throws Exception {
    	
        MuleMessage results = muleContext.getClient().request("jms://orders.status.stuck",15000);
        assertNotNull(results);
        assertNotNull(results.getPayload());
        assertEquals(1, ((Map<Object, Object>)results.getPayload()).keySet().size());
    }


    private static void createDatabase() {        
    	java.sql.Connection con = null;
    	try {
			con = DriverManager.getConnection(MYSQL_URL, ADMIN, PASSWORD);
			Statement stmt = con.createStatement();
			stmt.addBatch("CREATE DATABASE company");
			stmt.addBatch("USE company");			
			stmt.addBatch("CREATE TABLE orders " +
                "(id BIGINT NOT NULL, details VARCHAR(4096), timestamp TIMESTAMP)");
			java.util.Date dt = new java.util.Date();
			Timestamp timestamp = new Timestamp(dt.getTime());			
			stmt.addBatch("INSERT INTO orders VALUES (0, \"foo\", \"" + timestamp + "\")");
			stmt.executeBatch();			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally{
			if (con != null){
				try {
					con.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
        
    }
}
