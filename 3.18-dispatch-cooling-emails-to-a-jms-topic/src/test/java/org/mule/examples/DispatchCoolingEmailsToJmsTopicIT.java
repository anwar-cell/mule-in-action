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
import static org.junit.Assert.assertNotNull;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

import javax.mail.Flags;
import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Store;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.mule.api.MuleMessage;
import org.mule.tck.junit4.FunctionalTestCase;
import org.mule.util.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.sun.mail.imap.IMAPFolder;


public class DispatchCoolingEmailsToJmsTopicIT extends FunctionalTestCase
{
	private static final String PATH_TO_TEST_PROPERTIES = "./src/test/resources/mule.test.properties";
	private static final Logger log = LoggerFactory.getLogger(DispatchCoolingEmailsToJmsTopicIT.class); 
	
	private static String EMAIL_TEXT = "This is an alert from the cooling systems";
	private static String USER;
	private static String SMTP_HOST;
	private static String SMTP_PORT;
	private static String PASSWORD;
	
	private final static String TEST_DIR_PATH = "./data/cooling/reports"; 
	
	@Override
    protected String getConfigResources()
    {
        return "dispatch_cooling_emails_to_a_jms_topic.xml";
    }

    @BeforeClass
    public static void prepareTest() throws Exception {
    	final Properties props = new Properties();
    	try {
    	props.load(new FileInputStream(PATH_TO_TEST_PROPERTIES));
    	} catch (Exception e) {
    		log.error("Error occured while reading mule.test.properties", e);
    	} 

    	PASSWORD = props.getProperty("imap.password");
    	SMTP_HOST = props.getProperty("smtp.host");
    	SMTP_PORT = props.getProperty("smtp.port");
    	USER = props.getProperty("imap.user");
      	System.setProperty("imap.host", props.getProperty("imap.host"));
       	System.setProperty("imap.password", props.getProperty("imap.password"));
       	System.setProperty("imap.user", props.getProperty("imap.user_enc"));       	
      	System.setProperty("imap.port", props.getProperty("imap.port"));

       	new File(TEST_DIR_PATH).mkdirs();
       	
    	Properties properties = System.getProperties();
        properties.put("mail.smtp.auth", "true");
        properties.setProperty("mail.smtp.host", SMTP_HOST);
        properties.setProperty("mail.smtp.port", SMTP_PORT);
        properties.put("mail.smtp.starttls.enable", "true");
        Session session = Session.getInstance(properties,
                new javax.mail.Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(USER, PASSWORD);
                    }
                });


        try{
           MimeMessage message = new MimeMessage(session);
           message.setFrom(new InternetAddress(USER));
           message.addRecipient(Message.RecipientType.TO,
                                    new InternetAddress(USER));
           message.setSubject("This is the Subject Line!");
           message.setText(EMAIL_TEXT);
           Transport.send(message);
           System.out.println("Sent message successfully....");
        }catch (MessagingException mex) {
           mex.printStackTrace();
        }
    }
    
    @Test
    public void testCanReceiveEmail() throws Exception {
        MuleMessage coolingAlert = muleContext.getClient().request("jms://cooling.alerts", 1500);
        assertNotNull(coolingAlert);
        assertNotNull(coolingAlert.getPayload());
        assertEquals(EMAIL_TEXT, coolingAlert.getPayloadAsString().trim());
        assertEquals(1,FileUtils.listFiles(new File("./data/cooling/reports"), new String[]{"dat"}, false).size());
    }
   
    @AfterClass
    public static void tearDown(){
    	File testDir = new File(TEST_DIR_PATH);
    	
    	for (File file : testDir.listFiles()){
    		file.delete();
    	}
    	try {
			getSentEmail();
		} catch (MessagingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
    }
    
    private static String getSentEmail() throws MessagingException, IOException{
    	IMAPFolder folder = null;
        Store store = null;
        try 
        {
          Properties props = System.getProperties();
          props.setProperty("mail.store.protocol", "imaps");

          Session session = Session.getDefaultInstance(props, null);

          store = session.getStore("imaps");
          store.connect(SMTP_HOST, USER, PASSWORD);

          folder = (IMAPFolder) store.getFolder("inbox");


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
