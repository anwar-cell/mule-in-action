copy any xls file to the following dir:

./data/expenses/in

. means the project folder. the dir gets created by mule app.

you should see sth like this in your log:

	Message Received in service: dummyHttpServer. Content is:                    *
	* org.apache.commons.httpclient.ContentLengthInputStream@26f1e633
	
this means the file from file connector got to http endpoint via VM connectors. 