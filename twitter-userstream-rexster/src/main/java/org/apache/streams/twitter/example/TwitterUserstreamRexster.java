package org.apache.streams.twitter.example;

import com.fasterxml.jackson.databind.node.ObjectNode;
import com.typesafe.config.Config;
import org.apache.streams.blueprints.BlueprintsConfigurator;
import org.apache.streams.blueprints.BlueprintsPersistWriter;
import org.apache.streams.blueprints.BlueprintsWriterConfiguration;
import org.apache.streams.config.StreamsConfigurator;
import org.apache.streams.core.StreamBuilder;
import org.apache.streams.local.builders.LocalStreamBuilder;
import org.apache.streams.pojo.json.Activity;
import org.apache.streams.twitter.TwitterStreamConfiguration;
import org.apache.streams.twitter.processor.TwitterTypeConverter;
import org.apache.streams.twitter.provider.TwitterConfigurator;
import org.apache.streams.twitter.provider.TwitterStreamProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by sblackmon on 12/10/13.
 */
public class TwitterUserstreamRexster {

    private final static Logger LOGGER = LoggerFactory.getLogger(TwitterUserstreamRexster.class);

    public static void main(String[] args)
    {
        LOGGER.info(StreamsConfigurator.config.toString());

        Config twitter = StreamsConfigurator.config.getConfig("twitter");
        Config blueprints = StreamsConfigurator.config.getConfig("blueprints");

        TwitterStreamConfiguration twitterStreamConfiguration = TwitterConfigurator.detectTwitterStreamConfiguration(twitter);
        BlueprintsWriterConfiguration blueprintsWriterConfiguration = BlueprintsConfigurator.detectWriterConfiguration(blueprints);

        StreamBuilder builder = new LocalStreamBuilder(100);

        TwitterStreamProvider stream = new TwitterStreamProvider(twitterStreamConfiguration);
        TwitterTypeConverter converter = new TwitterTypeConverter(ObjectNode.class, Activity.class);
        BlueprintsPersistWriter writer = new BlueprintsPersistWriter(blueprintsWriterConfiguration);

        builder.newPerpetualStream(TwitterStreamProvider.STREAMS_ID, stream);
        builder.addStreamsProcessor("converter", converter, 1, TwitterStreamProvider.STREAMS_ID);
        //builder.addStreamsPersistWriter("console", new ConsolePersistWriter(), 1, "converter");
        builder.addStreamsPersistWriter(BlueprintsPersistWriter.STREAMS_ID, writer, 1, "converter");
        builder.start();

    }
}