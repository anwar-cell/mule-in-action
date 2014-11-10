1. start app
2. hit http://localhost:8081/foo
3. output is /foo:valid
2. hit http://localhost:8082/foo
3. output is /foo:invalid



4. make post request to http://localhost:8080 with body equal to:

		subflow1

	go to activemq admin gui, queues and subflow1 should be increased