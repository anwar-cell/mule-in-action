
1. run an app
2. to test soap, make a post request  to 

	http:/localhost:8090/soap

with
	<soapenv:Envelope xmlns:soapenv="http://schemas.xmlsoap.org/soap/envelope/" xmlns:ns="http://service.prancingdonkey.com/">
			   <soapenv:Header/>
			   <soapenv:Body>
			      <ns:getBrews>		         
			      </ns:getBrews>
			   </soapenv:Body>
			</soapenv:Envelope>