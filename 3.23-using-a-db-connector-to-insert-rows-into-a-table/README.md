3.23_querying_a_table_every_hour_and_sending_the_results_to_a_jms_topic

1. run scr/main/resources/orders.sql in workbench. it will create a db table for mysql database. the table needs to be in company DB. otherwise jdbc.url needs to be modified.
2. edit common.properties as appropriate.
3. run mule app
4. app polls for files in /data. copy src/test/resources/orders.csv to /data
5. make a select from orders table to see the new records using mysql workbench
6. go to http://demo.wftpserver.com/main.html to see an uploaded file orders.csv