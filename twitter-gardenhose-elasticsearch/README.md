twitter-gardenhose-elasticsearch
==============================

Requirements:
-------------
 - Authorized Twitter API credentials
 - A running ElasticSearch 1.0.0+ instance

Description:
------------
Listens for tweets, converts them to activities, and writes them in activity form to elasticsearch

Specification:
-----------------

[TwitterGardenhoseElasticsearch.dot](src/main/resources/TwitterGardenhoseElasticsearch.dot "TwitterGardenhoseElasticsearch.dot" )

Diagram:
-----------------

![TwitterGardenhoseElasticsearch.png](./TwitterGardenhoseElasticsearch.png?raw=true)

Configuration:
--------------
    twitter {
        endpoint = "sample"
        oauth {
            consumerKey = ""
            consumerSecret = ""
            accessToken = ""
            accessTokenSecret = ""
        }
        track = [
            apache
            hadoop
            pig
            hive
            cassandra
            elasticsearch
            mongo
            data
            apache storm
            apache streams
            big data
            asf
            opensource
            open source
            apachecon
            apache con
        ]
    }
    elasticsearch {
        hosts = [
            localhost
        ]
        port = 9300
        clusterName = elasticsearch
        index = gardenhose_activity
        type = activity
        batchSize = 50
    }

You will need to change the Twitter keys to reflect the contents your personal token

Running:
--------

    java -cp target/twitter-gardenhose-elasticsearch-0.1-SNAPSHOT.jar -Dconfig.file=src/main/resources/application.conf org.apache.streams.twitter.example.TwitterGardenhoseElasticsearch

Verification:
-------------
**NOTE:** It may take some time for enough tweets to come through before the buffers get flushed to ElasticSearch

Once this example has run for long enough, you should see the index you specified filling with data.

