Step 1: import the project
Step 2: edit common.properties for imap account
Step 3: run the app
Step 4: send an email to the imap account
Step 5: verify the created file. get a location from the log:

	INFO  2014-07-16 11:09:56,386 [[3.18_dispatch_cooling_emails_to_a_jms_topic].connector.file.mule.default.dispatcher.01] org.mule.transport.file.FileConnector: Writing file to: C:\studio-examples-repo\3.18_dispatch_cooling_emails_to_a_jms_topic\data\cooling\reports\f48cfaf0-0cc8-11e4-9f5b-5c514f927c90.dat

		go to http://localhost:8161/admin/topics.jsp to see the message was delivered to the topic