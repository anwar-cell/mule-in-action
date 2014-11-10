package org.mule.examples;

import static junit.framework.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.HashMap;
import java.util.Map;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mule.api.MuleMessage;
import org.mule.tck.junit4.FunctionalTestCase;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.Mongo;

import de.flapdoodle.embed.mongo.MongodExecutable;
import de.flapdoodle.embed.mongo.MongodProcess;
import de.flapdoodle.embed.mongo.MongodStarter;
import de.flapdoodle.embed.mongo.config.MongodConfig;
import de.flapdoodle.embed.mongo.distribution.Version;
import de.flapdoodle.embed.process.runtime.Network;

public class QueryMongodbcollectionOverHttpTest extends FunctionalTestCase {

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
        return "query_a_mongodb_collection_over_http.xml";
    }
    
    @SuppressWarnings("deprecation")
	@Before
    public void setup() throws Exception {
        MongodStarter runtime = MongodStarter.getDefaultInstance();
        mongodExe = runtime.prepare(new MongodConfig(Version.V2_0_5, MONGO_PORT, Network.localhostIsIPv6()));
        mongod = mongodExe.start();
        mongo = new Mongo(MONGO_HOST, MONGO_PORT); 
        db = mongo.getDB(DB_NAME);
        DBCollection col = db.createCollection(COLLECTION, new BasicDBObject("capped", true).append("size", 1024));
		col.insert(new BasicDBObject("my_key", "123"));
		col.insert(new BasicDBObject("my_key", "456"));
    }
    
    @Test
    public void testCanQueryForAlerts() throws Exception {
        Map<String, Object>  properties = new HashMap<String, Object>();
        MuleMessage response = muleContext.getClient().send("http://localhost:8080/alerts/cooling","{}",properties);
        assertNotNull(response);
        assertNotNull(response.getPayloadAsString());
        System.out.println(response.getPayloadAsString());
        assertTrue(response.getPayloadAsString().contains("123"));
        assertTrue(response.getPayloadAsString().contains("456"));
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
