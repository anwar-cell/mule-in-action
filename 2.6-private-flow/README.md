# Using a private flow to define a local exception strategy

Private flows are another type of reusable flows, very similar to subflows, but with a different
behavior in terms of threading and exception handling. The primary reason
for using a private flow instead of a subflow is to define in it a different exception strategy
from the calling flow (something that is impossible with a subflow). Another reason
is that subflows aren’t materialized at runtime and, as such, don’t have specific
statistics or debug properties attached to them and can’t be controlled or monitored
independently. Private flows offer all this.

This example was created thanks to the resources taken from [Mule in action](http://www.manning.com/dossot/) by David Dossot and John D'Emic. 

### Assumptions ###

This document describes the details of the example within the context of **Anypoint™ Studio**, Mule ESB’s graphical user interface (GUI). Where appropriate, the XML configuration is provided.

This document assumes that you are familiar with Mule ESB and the [Anypoint Studio interface](http://www.mulesoft.org/documentation/display/current/Anypoint+Studio+Essentials). To increase your familiarity with Mule Studio, consider completing one or more [Anypoint Studio Tutorials](http://www.mulesoft.org/documentation/display/current/Basic+Studio+Tutorial). Further, this example assumes that you have a basic understanding of [Mule flows](http://www.mulesoft.org/documentation/display/current/Mule+Application+Architecture) and [Mule Global Elements](http://www.mulesoft.org/documentation/display/current/Global+Elements).


### Set Up and Run the Example ###

Complete the following procedure to create, then run this example in your own instance of Anypoint Studio. Skip ahead to the next section if you prefer to simply examine this example.

1. Make sure you have a running instance of ActiveMQ. Please consult this [resource](http://activemq.apache.org/getting-started.html) if you need assistance. 
2. Open Global Elements tab and set the correct broker URL for the *ActiveMQ1* component. If you have it running with the default setup, the URL should be:

		Broker URL					tcp://localhost:61616		

3. [Create](http://www.mulesoft.org/documentation/display/current/Mule+Examples#MuleExamples-CreateandRunExampleApplications) the example application in Anypoint Studio.
3. Open [ActiveMQ Admin Console](http://localhost:8161/admin/queues.jsp). Click **Send** tab and fill in the fields as follows:

		Destination					main-flow.one-way
		Queue or Topic				Queue
		Message body				test

2. Click **Queues** tab and notice the increased number of messages under *Messages Enqueued* column in the *legacyAdapter.failures* queue.  


3. Click **Send** tab and fill in the fields as follows:

		Destination					main-flow.one-way
		Queue or Topic				Queue
		
	You can use *main-flow.request-response* as *Destination* as well. 
	Play around and try on of the following values for *Message body*:
	
	+ spf
	+ apf
	+ soapf

4. Watch the console output in Anypoint Studio to see different response to these message combinations.

### How it Works ###

The composite source containing two JMS endpoints allows accepting messages from two queues: *main-flow.request-response* and *main-flow.one-way*. The message is routed to four flows that are referenced from the main flow based on the payload content. These flows differ in the processing strategy. The *legacyAdapterPrivateFlow* flow defines custom exception strategy: it sends a message to *legacyAdapter.failures* queue to your JMS system.

### Documentation ###

Studio includes a feature that enables you to easily export all the documentation you have recorded for your project. Whenever you want to share your project with others outside the Studio environment, you can export the project's documentation to print, email or share online. Studio's auto-generated documentation includes:

- A visual diagram of the flows in your application
- The XML configuration which corresponds to each flow in your application
- The text you entered in the Notes tab of any building block in your flow

Follow [the procedure](http://www.mulesoft.org/documentation/display/current/Importing+and+Exporting+in+Studio#ImportingandExportinginStudio-ExportingStudioDocumentation) to export auto-generated Studio documentation.