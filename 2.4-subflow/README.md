# Using a subflow to share a common set of transformers

Subflows are useful when you want to share a common message processor chain which impacts many flows. As expected, messages going through these flows will be transformed by the set of transformers as if they were directly configured in each of them.

This example was created thanks to the resources taken from [Mule in action](http://www.manning.com/dossot/) by David Dossot and John D'Emic. 

### Assumptions ###

This document describes the details of the example within the context of **Anypoint™ Studio**, Mule ESB’s graphical user interface (GUI). Where appropriate, the XML configuration is provided.

This document assumes that you are familiar with Mule ESB and the [Anypoint Studio interface](http://www.mulesoft.org/documentation/display/current/Anypoint+Studio+Essentials). To increase your familiarity with Mule Studio, consider completing one or more [Anypoint Studio Tutorials](http://www.mulesoft.org/documentation/display/current/Basic+Studio+Tutorial). Further, this example assumes that you have a basic understanding of [Mule flows](http://www.mulesoft.org/documentation/display/current/Mule+Application+Architecture) and [Mule Global Elements](http://www.mulesoft.org/documentation/display/current/Global+Elements).


### Set Up and Run the Example ###

Complete the following procedure to create, then run this example in your own instance of Anypoint Studio. Skip ahead to the next section if you prefer to simply examine this example.

Firstly, run the example using HTTP endpoint. 

1. [Create](http://www.mulesoft.org/documentation/display/current/Mule+Examples#MuleExamples-CreateandRunExampleApplications) the example application in Anypoint Studio. 

6. Make a POST request, e.g. using Postman Chrome extension, to [http://localhost:8080](http://localhost:8080) with a request body:

		<java.lang.String>my message</java.lang.String>

7. Check that the response is equal to:	

		more-processing: my message

The second approach to demonstrate subflow component functionality is to use ActiveMQ messaging system:  

1. Make sure you have a running instance of ActiveMQ. Please consult this [resource](http://activemq.apache.org/getting-started.html) if you need assistance. 
2. Open [ActiveMQ Admin Console](http://localhost:8161/admin/queues.jsp). Click **Send** tab and fill in the fields as follows:

		Destination					v1.email
		Queue or Topic				Queue
		Message body				<java.lang.String>my message</java.lang.String>
		
2. Check the log for the following line:

		INFO  2014-11-12 10:36:14,536 [[2.4-subflow].mainFlow2.stage1.02] org.mule.api.processor.LoggerMessageProcessor: object: my message


### How it Works ###

The HTTP endpoint accepts POST requests that are processed by *legacyAdapterSubFlow* subflow that transforms XML to an object. The String representation of the object is used in the custom Java component. The same functionality is obtained in the second flow *mainFlow2* but the HTTP endpoint is replaced by the JMS inbound endpoint that listens to the *v1.email* queue. 

### Documentation ###

Studio includes a feature that enables you to easily export all the documentation you have recorded for your project. Whenever you want to share your project with others outside the Studio environment, you can export the project's documentation to print, email or share online. Studio's auto-generated documentation includes:

- A visual diagram of the flows in your application
- The XML configuration which corresponds to each flow in your application
- The text you entered in the Notes tab of any building block in your flow

Follow [the procedure](http://www.mulesoft.org/documentation/display/current/Importing+and+Exporting+in+Studio#ImportingandExportinginStudio-ExportingStudioDocumentation) to export auto-generated Studio documentation.