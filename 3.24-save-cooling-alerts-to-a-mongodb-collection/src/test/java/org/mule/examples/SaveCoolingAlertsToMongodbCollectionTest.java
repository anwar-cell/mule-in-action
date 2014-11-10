package org.mule.examples;

import static org.junit.Assert.assertEquals;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CountDownLatch;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mule.tck.junit4.FunctionalTestCase;

import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.Mongo;

import de.flapdoodle.embed.mongo.MongodExecutable;
import de.flapdoodle.embed.mongo.MongodProcess;
import de.flapdoodle.embed.mongo.MongodStarter;
import de.flapdoodle.embed.mongo.config.MongodConfig;
import de.flapdoodle.embed.mongo.distribution.Version;
import de.flapdoodle.embed.process.runtime.Network;

/**
 * ToDo These tests are currently disabled until there is a way for Cloud Connectors to participate in
 * Mule's notification
 * system.  Need the notifications to count down the latch for the test cases.
 */
public class SaveCoolingAlertsToMongodbCollectionTest extends FunctionalTestCase {

	
    CountDownLatch insertLatch;
    private static DB db = null;
	private final static String COLLECTION = "cooling_alerts";
	private static String DB_NAME = "customers";
		
	private static final String MONGO_HOST = "localhost";
    private static final int MONGO_PORT = 27017;
    
    private MongodExecutable mongodExe;
    private MongodProcess mongod;
    private Mongo mongo;
    
    @Override
    protected String getConfigResources() {
        return "save_cooling_alerts_to_a_mongodb_collection.xml";
    }
  
    @SuppressWarnings("deprecation")
	@Before
    public void setup() throws Exception {
        MongodStarter runtime = MongodStarter.getDefaultInstance();
        mongodExe = runtime.prepare(new MongodConfig(Version.V2_0_5, MONGO_PORT, Network.localhostIsIPv6()));
        mongod = mongodExe.start();
        mongo = new Mongo(MONGO_HOST, MONGO_PORT); 
        db = mongo.getDB(DB_NAME);
    }
    
    @Test
    public void testCanSaveCoolingAlerts() throws Exception {

        Map<String,Object> coolingAlert = new HashMap<String, Object>();
        coolingAlert.put("location","NYC");
        coolingAlert.put("temperature",65.00);
        muleContext.getClient().dispatch("jms://topic:cooling.alerts", "{'location': 'NYC', 'temperature': 65.00}", null);

        Thread.sleep(2000);
        DBCollection collection = db.getCollection(COLLECTION);
        assertEquals(1, collection.getCount());

    }
   
    @After
    public void teardown() {
        if (mongod != null) {
        	try{
        		mongod.stop();
        	}
        	catch (Exception e){
        		e.printStackTrace();
        	}
            mongodExe.stop();
        }
    }

}
