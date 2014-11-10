1. download redis from
2. run redis-server.exe from your redis unzipped dir
3. run app
4. make a post request using:

		<order>
            <id>1234</id> 
		    <name>John</name>
		    <address>123 Foo Street</address>
		    <city>Brooklyn</city>
		    <country>USA</country>
		</order>

5. run redis-client.exe from your redis dir
6. type 
	
	get 1234

this should be the output
		"<order>\n            <id>1234</id> \n\t    <name>John</name>\n\t    <address>12
		3 Foo Street</address>\n\t    <city>Brooklyn</city>\n\t    <country>USA</country
		>\n\t</order>"

