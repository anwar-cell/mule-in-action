start active mq

1. to test messageRouter and idempotentRouter:
	1. send a message to orders topic with body:
		
	<order>
	<id>4d3196a9-aecc-4b46-be21-2b7be47a8b19</id>
	<priority>HIGH</priority>
	<customerId>d69d3d89-9955-45c7-8167-24bf2733b347</customerId>
	<total>100.23</total>
	<shippingCost>21.23</shippingCost>
	<lineItems>
	<lineItem>
	<brew>11</brew>
	<quantity>2</quantity>
	</lineItem>
	<lineItem>
	<brew>3</brew>
	<quantity>1</quantity>
	</lineItem>
	</lineItems>
	</order> 

2. to test schema validation send post request to http://localhost:8081/orders using body

		<?xml version="1.0" encoding="utf-8"?>
		<order>
		  <id>str1234</id>
		  <priority>str1234</priority>
		  <items>
		    <lineItem>
		      <priority>HIGH</priority>
		    </lineItem>
		  </items>
		</order>

	go to http://localhost:8161/admin/topics.jsp to see the inc. number of messages at cooling.alerts
3. send post request to http://localhost:8081 with body

blavblal SEVERE ablavbal

it checks SEVERE substring there.

go to http://localhost:8161/admin/topics.jsp to see the inc. number of messages at cooling.alerts


