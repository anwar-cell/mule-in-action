package org.mule.examples;

import static org.junit.Assert.assertTrue;
import static org.junit.Assume.assumeNotNull;
import static org.junit.Assume.assumeTrue;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

import org.apache.commons.io.FileUtils;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.mule.module.client.MuleClient;
import org.mule.tck.junit4.FunctionalTestCase;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import redis.clients.jedis.Jedis;

public class ConfiguringRedisTest extends FunctionalTestCase
{
	private static final String PATH_TO_TEST_PROPERTIES = "./src/test/resources/mule.test.properties";
	private static Logger log = LoggerFactory.getLogger(ConfiguringRedisTest.class);
    
	private static String REDIS_HOST;
    private static final String KEY = "1234";
    private static String MESSAGE;
    
    private Jedis jedis;

    @BeforeClass
    public static void init(){
    	try {
			MESSAGE = FileUtils.readFileToString(new File("./src/test/resources/message.xml"));
		} catch (IOException e) {
			e.printStackTrace();
		}
    	
    	final Properties props = new Properties();
    	try {
    		props.load(new FileInputStream(PATH_TO_TEST_PROPERTIES));
    	} catch (Exception e) {
    		log.error("Error occured while reading mule.test.properties", e);
    	}
    	REDIS_HOST = props.getProperty("redis.host");
    }
    
    public ConfiguringRedisTest()
    {
        setStartContext(false);
    }

    @Override
    protected String getConfigResources()
    {
        return "configuring_redis.xml";
    }

    @Before
    public void doSetup() throws Exception
    {
        jedis = new Jedis(REDIS_HOST);

        try
        {
            jedis.connect();
        }
        catch (final Exception e)
        {
        }

        // Tests should not fail if Redis is not reachable
        assumeNotNull(jedis);
        assumeTrue(jedis.isConnected());

        setStartContext(true);
        setUpMuleContext();
    }

    @Test
    public void testObjectStore() throws Exception
    {
        final MuleClient muleClient = new MuleClient(muleContext);
        muleClient.send("http://localhost:8081", MESSAGE, null);
        
        System.out.println(new String(jedis.get(KEY.getBytes())));
        assertTrue(new String(jedis.get(KEY.getBytes())).contains("1234"));
        assertTrue(new String(jedis.get(KEY.getBytes())).contains("John"));
        assertTrue(new String(jedis.get(KEY.getBytes())).contains("USA"));
    }

}
