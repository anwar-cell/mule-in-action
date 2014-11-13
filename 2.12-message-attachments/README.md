# Extracting message attachments with an expression transformer

As briefly presented in the introduction, message attachments allow for supporting extra chunks of data that aren’t meta information, semantically speaking, but that are more like alternative or complementary chunks of payload.
An attachment is defined by a name, a source of data (an instance of javax.activation.DataHandler), and a scope. Attachments’ scopes are limited to inbound and outbound, with the same flow-bound boundaries as the properties’ scopes. Attachments are mainly used to support the multiple parts of inbound email messages (created with the POP or IMAP transport) and outbound email messages (sent with the SMTP transport). Inbound attachments are created by the email endpoint directly, while outbound endpoints require attachments to be created programmatically.

This example was created thanks to the resources taken from [Mule in action](http://www.manning.com/dossot/) by David Dossot and John D'Emic. 

### Assumptions ###

This document describes the details of the example within the context of **Anypoint™ Studio**, Mule ESB’s graphical user interface (GUI). Where appropriate, the XML configuration is provided.

This document assumes that you are familiar with Mule ESB and the [Anypoint Studio interface](http://www.mulesoft.org/documentation/display/current/Anypoint+Studio+Essentials). To increase your familiarity with Mule Studio, consider completing one or more [Anypoint Studio Tutorials](http://www.mulesoft.org/documentation/display/current/Basic+Studio+Tutorial). Further, this example assumes that you have a basic understanding of [Mule flows](http://www.mulesoft.org/documentation/display/current/Mule+Application+Architecture).

### Set Up and Run the Example ###

Complete the following procedure to create, then run this example in your own instance of Anypoint Studio. 

1. [Create](http://www.mulesoft.org/documentation/display/current/Mule+Examples#MuleExamples-CreateandRunExampleApplications) the example application in Anypoint Studio.
2. Start the application.
3. Open your browser and hit http://localhost:8081.
3. Return to Anypoint Studio and observe two log outputs in the console similar to these:

		INFO  2014-11-13 10:24:58,891 [email-order-processor.stage1.02] org.mule.tck.functional.FunctionalTestComponent: 
		********************************************************************************
		* Message Received in service: email-order-processor. Content is:              *
		* javax.activation.DataHandler@41fed6c8                                        *
		********************************************************************************
		INFO  2014-11-13 10:24:58,894 [email-order-processor.stage1.02] org.mule.tck.functional.FunctionalTestComponent: 
		********************************************************************************
		* Message Received in service: email-order-processor. Content is:              *
		* javax.activation.DataHandler@3d71a8b                                         *
		********************************************************************************

### How it Works ###

Two message attachments are added to the processed message by hitting the HTTP inbound endpoint. An expression transformer is used to extract all the attachments and replace the current message payload with the extracted attachments list that is sent to Collection Splitter and printed to the console log using Test component.

### Documentation ###

Studio includes a feature that enables you to easily export all the documentation you have recorded for your project. Whenever you want to share your project with others outside the Studio environment, you can export the project's documentation to print, email or share online. Studio's auto-generated documentation includes:

- A visual diagram of the flows in your application
- The XML configuration which corresponds to each flow in your application
- The text you entered in the Notes tab of any building block in your flow

Follow [the procedure](http://www.mulesoft.org/documentation/display/current/Importing+and+Exporting+in+Studio#ImportingandExportinginStudio-ExportingStudioDocumentation) to export auto-generated Studio documentation.
