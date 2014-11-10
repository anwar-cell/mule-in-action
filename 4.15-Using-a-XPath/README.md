1. start mule app
2. make a post request to localhost:8081 with:
	
		<products>
		<product>
			<id>1234</id>
	    	<type>Imported Beer</type>
			<name>Mordor's Pale Lager</name>
			<price>10.90</price>
		</product>
		</products>
 to get a product id extracted from xml
3. make a post request to localhost:8082 with:
	
		<products>
		<product>
			<id>1234</id>
	    	<type>Imported Beer</type>
			<name>Mordor's Pale Lager</name>
			<price>10.90</price>
		</product>
		</products>

to see that the node list was extracted from input xml