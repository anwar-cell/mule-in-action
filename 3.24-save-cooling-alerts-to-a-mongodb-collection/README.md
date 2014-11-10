3.24_save_cooling_alerts_to_a_mongodb_collection

1. start mongo db, by running mongod.exe
2. start active mq
3. send a message to cooling.alerts with a body:

{
	"whatever": "whatever",
	"whatever1": "whatever1"
}

it only needs to be like key/value pairs.
4. run mongo and type:

	use customers
	db.customers.find()

there should be records of your messages