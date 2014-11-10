/*
 * $Id: EchoTestCase.java 24098 2012-03-16 14:41:55Z asequeira $
 * --------------------------------------------------------------------------------------
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 *
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */
package org.mule.examples;

import static org.junit.Assert.assertEquals;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

import javax.mail.Flags;
import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Store;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.mule.tck.junit4.FunctionalTestCase;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.sun.mail.imap.IMAPFolder;


public class UsingSmtpEndpointToSendAnEmailIT extends FunctionalTestCase
{
	
	private static final String PATH_TO_TEST_PROPERTIES = "./src/test/resources/mule.test.properties";
	private static final Logger log = LoggerFactory.getLogger(UsingSmtpEndpointToSendAnEmailIT.class); 
	
	private static String USER;
	private static String IMAP_HOST;	
	private static String PASSWORD;
		
	@Override
    protected String getConfigResources()
    {
        return "using_smtp_endpoint_to_send_an_email.xml";
    }
	
    @BeforeClass
    public static void prepareTest() throws Exception {
    	final Properties props = new Properties();
    	try {
    	props.load(new FileInputStream(PATH_TO_TEST_PROPERTIES));
    	} catch (Exception e) {
    		log.error("Error occured while reading mule.test.properties", e);
    	} 

    	PASSWORD = props.getProperty("smtp.password");
    	IMAP_HOST = props.getProperty("imap.host");
    	USER = props.getProperty("smtp.user");
      	System.setProperty("smtp.host", props.getProperty("smtp.host"));
       	System.setProperty("smtp.password", props.getProperty("smtp.password"));
       	System.setProperty("smtp.user", props.getProperty("smtp.user_enc"));       	
       	System.setProperty("smtp.port", props.getProperty("smtp.port"));       	
      	
      	    
    }
    
    @Test
    public void testCanSendEmail() throws Exception {
        Store store = null;
    	int count = openFolder(store).getMessages().length;
        muleContext.getClient().dispatch("jms://topic:expenses.status","Expense Report",null);
        Thread.sleep(10000);
        assertEquals(count + 1, openFolder(store).getMessages().length);
        openFolder(store).close(true);
    }

   
    @AfterClass
    public static void tearDown(){    	
    	try {
			deleteSentEmail();
		} catch (MessagingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
    
    private static IMAPFolder openFolder(Store store) throws MessagingException{
    	
        Properties props = System.getProperties();
        props.setProperty("mail.store.protocol", "imaps");

        Session session = Session.getDefaultInstance(props, null);

        store = session.getStore("imaps");
        store.connect(IMAP_HOST, USER, PASSWORD);
        
        IMAPFolder folder = (IMAPFolder) store.getFolder("inbox");
        if(!folder.isOpen())
      	  folder.open(Folder.READ_WRITE);
        return folder;
    }
    
    private static String deleteSentEmail() throws MessagingException, IOException{
    	Store store = null;
        IMAPFolder folder = null;
        try 
        {
          folder = openFolder(store);	

          if(!folder.isOpen())
        	  folder.open(Folder.READ_WRITE);
          Message[] messages = folder.getMessages();
          messages[messages.length - 1].setFlag(Flags.Flag.DELETED, true);
          return messages[messages.length - 1].getContent().toString();
        }
        finally 
        {
          if (folder != null && folder.isOpen()) { folder.close(true); }
          if (store != null) { store.close(); }
        }
    }

}
