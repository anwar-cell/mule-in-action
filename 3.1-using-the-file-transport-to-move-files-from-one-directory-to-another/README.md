# Using the file transport to move files from one directory to another

Reading and writing file data is often the easiest way get data into and out of applications. In this example, you’ll see how to use Mule’s file transport to read, write, move, and delete files.

This example was created thanks to the resources taken from [Mule in action](http://www.manning.com/dossot/) by David Dossot and John D'Emic.

### Assumptions ###

This document describes the details of the example within the context of **Anypoint™ Studio**, Mule ESB’s graphical user interface (GUI). Where appropriate, the XML configuration is provided.

This document assumes that you are familiar with Mule ESB and the [Anypoint Studio interface](http://www.mulesoft.org/documentation/display/current/Anypoint+Studio+Essentials). To increase your familiarity with Mule Studio, consider completing one or more [Anypoint Studio Tutorials](http://www.mulesoft.org/documentation/display/current/Basic+Studio+Tutorial). Further, this example assumes that you have a basic understanding of [Mule flows](http://www.mulesoft.org/documentation/display/current/Mule+Application+Architecture).

### Set Up and Run the Example ###

Complete the following procedure to create, then run this example in your own instance of Anypoint Studio. 

1. [Create](http://www.mulesoft.org/documentation/display/current/Mule+Examples#MuleExamples-CreateandRunExampleApplications) the example application in Anypoint Studio.
2. Copy any xls file into **location**/data/expenses/1/in, where location is the location where you store the project directory.
3. Verify that the file was copied to */data/expenses/out* in your project directory.

### How it works

The message source of this flow is the file inbound endpoint, configured to poll the* /data/expenses/1/in* directory every second for new files. The *filename-regexfilter* instructs the endpoint to only accept files that end in .xls and to ignore the case. Finally, the outbound endpoint uses the Mule Expression Language to normalize the filename with the timestamp and the file’s original name. To obtain the latter, you reference the *originalFilename* header populated by the file inbound endpoint. 

### Documentation ###

Studio includes a feature that enables you to easily export all the documentation you have recorded for your project. Whenever you want to share your project with others outside the Studio environment, you can export the project's documentation to print, email or share online. Studio's auto-generated documentation includes:

- A visual diagram of the flows in your application
- The XML configuration which corresponds to each flow in your application
- The text you entered in the Notes tab of any building block in your flow

Follow [the procedure](http://www.mulesoft.org/documentation/display/current/Importing+and+Exporting+in+Studio#ImportingandExportinginStudio-ExportingStudioDocumentation) to export auto-generated Studio documentation.
