# Bux-bot Quick Start

The expected bot flow looks as follow:
1. Connect and register for price event
2. Wait for the expected price and then buy the product 
3. Sell the product when price is less or greater than given boundaries
4. Stop the bot

### Tech stack

1. Taking into account that all the logic is based on event processing, I decided to use actors. The most known one I heard but
never use was Akka.
2. All the messages that come from/to actros should be immutable. Google's Autovalue allows us to create such object with 
less code. 
3. Jackson for serialization/deserialization - pretty obvious choice.
4. Retrofit fot http calls
5. async-http-client for websockets

### How to run

The bot accepts several params all of which are required for successful start.

* -i,--id <arg>      The product id
* -p,--price <arg>   The buy price
* -l,--lower <arg>   The lower limit sell price this the price you want are willing to close a position at and make a loss.
* -u,--upper <arg>   The upper limit sell price this is the price you are willing to close a position and make a profit.

`java -jar bux-bot-1.0-jar-with-dependencies.jar -i sb26493 -p 13222 -l 13220 -u 13225`


### Things would be nice to improve

* Error handling.
* Test coverage.
* Don't stop trading after the first sell.




