# Posting data with the HTTP transport

The HTTP transport allows you to send and receive data using the HTTP protocol. You can use HTTP’s POST method to send data through an outbound endpoint or the GET method to return data from a request-response inbound endpoint.

This example was created thanks to the resources taken from [Mule in action](http://www.manning.com/dossot/) by David Dossot and John D'Emic. 

### Assumptions ###

This document describes the details of the example within the context of **Anypoint™ Studio**, Mule ESB’s graphical user interface (GUI). Where appropriate, the XML configuration is provided.

This document assumes that you are familiar with Mule ESB and the [Anypoint Studio interface](http://www.mulesoft.org/documentation/display/current/Anypoint+Studio+Essentials). To increase your familiarity with Mule Studio, consider completing one or more [Anypoint Studio Tutorials](http://www.mulesoft.org/documentation/display/current/Basic+Studio+Tutorial). Further, this example assumes that you have a basic understanding of [Mule flows](http://www.mulesoft.org/documentation/display/current/Mule+Application+Architecture).

### Set Up and Run the Example ###

Complete the following procedure to create, then run this example in your own instance of Anypoint Studio. 

1. [Create](http://www.mulesoft.org/documentation/display/current/Mule+Examples#MuleExamples-CreateandRunExampleApplications) the example application in Anypoint Studio.
2. Copy any xls file into **location**/data/expenses/in, where location is the location where you store the project directory.
3. Verify that the console log in Anypoint Studio contains output similar to this:
		 
		********************************************************************************
		* Message Received in service: dummyHttpServer. Content is:                    *
		* org.apache.commons.httpclient.ContentLengthInputStream@7ffd4b0f              *
		********************************************************************************

### How it works

The message source of this flow is the file inbound endpoint, configured to poll the* /data/expenses/in* directory every second for new files. The *filename-regexfilter* instructs the endpoint to only accept files that end in .xls and to ignore the case. Finally, the HTTP outbound endpoint posts the file contents to the given URL. 

### Documentation ###

Studio includes a feature that enables you to easily export all the documentation you have recorded for your project. Whenever you want to share your project with others outside the Studio environment, you can export the project's documentation to print, email or share online. Studio's auto-generated documentation includes:

- A visual diagram of the flows in your application
- The XML configuration which corresponds to each flow in your application
- The text you entered in the Notes tab of any building block in your flow

Follow [the procedure](http://www.mulesoft.org/documentation/display/current/Importing+and+Exporting+in+Studio#ImportingandExportinginStudio-ExportingStudioDocumentation) to export auto-generated Studio documentation.