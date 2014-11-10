package org.mule.examples;

import static org.junit.Assert.assertEquals;

import java.io.File;
import java.io.FileInputStream;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.mule.api.config.MuleProperties;
import org.mule.tck.junit4.FunctionalTestCase;
import org.mule.util.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class UsingDbConnectorToInsertRowsIntoTableIT extends FunctionalTestCase {

	private static final String PATH_TO_TEST_PROPERTIES = "./src/test/resources/mule.test.properties";
	private static final Logger log = LoggerFactory.getLogger(UsingDbConnectorToInsertRowsIntoTableIT.class); 

    private static String MYSQL_URL;
    private static String ADMIN;
    private static String PASSWORD;
    
    private static java.sql.Connection con;
    
    @Override
    protected String getConfigResources() {
        return "using_a_db_connector_to_insert_rows_into_a_table.xml";
    }

    @BeforeClass
    public static void setupDirectories() throws Exception {
        File dataDirectory = new File("./data");
        if (dataDirectory.exists()) {
            FileUtils.deleteDirectory(dataDirectory);
        }
        dataDirectory.mkdirs();
        
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

    private static final String MAPPINGS_FOLDER_PATH = "./mappings";

	@Override
	protected Properties getStartUpProperties() {
		Properties properties = new Properties(super.getStartUpProperties());

		String pathToResource = MAPPINGS_FOLDER_PATH;
		File graphFile = new File(pathToResource);

		properties.put(MuleProperties.APP_HOME_DIRECTORY_PROPERTY,
				graphFile.getAbsolutePath());

		return properties;
	}
	     
    @AfterClass
    public static void destroyDB() throws Exception {
    	con = DriverManager.getConnection(MYSQL_URL, ADMIN, PASSWORD);
    	
		Statement stmt = con.createStatement();
		
		stmt.execute("DROP DATABASE company");
		con.close();
    }
    
    @Test
    public void testCanInsertProducts() throws Exception {
        FileUtils.copyFileToDirectory(new File("src/test/resources/products.csv"),new File("./data"));
        Thread.sleep(5000);
        Statement stmt = con.createStatement();
        stmt.execute("SELECT * FROM products");
        int count = 0;
        while (stmt.getResultSet().next()){        
        	count++;
        }
        assertEquals(2, count);
    }
    
    private static void createDatabase() {    	
		try {
			con = DriverManager.getConnection(MYSQL_URL, ADMIN, PASSWORD);
			Statement stmt = con.createStatement();
			stmt.addBatch("CREATE DATABASE company");
			stmt.addBatch("USE company");			
			stmt.executeBatch();
			stmt.execute("CREATE TABLE products (id BIGINT NOT NULL, name VARCHAR(256), acv DOUBLE, cost DOUBLE, description VARCHAR(4096))");
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		
    }
}
