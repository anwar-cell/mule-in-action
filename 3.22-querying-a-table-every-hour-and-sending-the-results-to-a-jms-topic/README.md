3.22_querying_a_table_every_hour_and_sending_the_results_to_a_jms_topic

1. run orders.sql in mysql workbench. it will create a db table for mysql database. the table needs to be in company DB, be sure that it exists. otherwise jdbc.url needs to be modified.
2. edit common.properties as appropriate.
2. run active mq
3. run mule app
4. app polls for table rows that are sent to jms. go to mq admin gui to check that orders.status.stuck topic got new messages.