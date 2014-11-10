1. start active mq
2. start app
3. http://localhost:8161/admin/send.jsp

	send jms message to main-flow.request-response queue

	send jms message to main-flow.one-way queue

	with body equal to 1 of these values:

	- spf
	- 	apf
	- 	soapf

4. watch console output to see different response to these message combinations