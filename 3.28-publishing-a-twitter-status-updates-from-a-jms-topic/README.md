3.28_publishing_a_twitter_status_updates_from_a_jms_topic

1. edit common.properties to set your twitter access data
2. start active mq
3. go to send tab and send a message to brews.new topic with body:

{ "name" : "blablal", "type" : "kakaka"}

you should get a new tweet status at your account. to be honest i didnt test this because i couldnt modify my app permissions to read and write. this is done at https://apps.twitter.com/ but it requires mobile phone verification. i couldnt find a way to add my phone number there. this doc didnt help me

[https://support.twitter.com/articles/110250-adding-your-mobile-number-to-your-account-via-web](https://support.twitter.com/articles/110250-adding-your-mobile-number-to-your-account-via-web)

could you try this one?