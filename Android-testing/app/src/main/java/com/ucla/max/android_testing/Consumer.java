package com.ucla.max.android_testing;

import android.util.Log;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.io.Resources;
import org.HdrHistogram.Histogram;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;

import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.Properties;
import java.util.Random;

/**
 * Created by Max on 5/7/16.
 */
public class Consumer {

//    public static void startConsumer1() throws IOException {
//        // set up house-keeping
//        ObjectMapper mapper = new ObjectMapper();
//        Histogram stats = new Histogram(1, 10000000, 2);
//        Histogram global = new Histogram(1, 10000000, 2);
//
//        // and the consumer
//        KafkaConsumer<String, String> consumer;
//        try (InputStream props = Resources.getResource("consumer.props").openStream()) {
//            Properties properties = new Properties();
//            properties.load(props);
//            if (properties.getProperty("group.id") == null) {
//                properties.setProperty("group.id", "group-" + new Random().nextInt(100000));
//            }
//            consumer = new KafkaConsumer<>(properties);
//        }
//        consumer.subscribe(Arrays.asList("fast-messages", "summary-markers"));
//        int timeouts = 0;
//        //noinspection InfiniteLoopStatement
//        while (true) {
//            // read records with a short timeout. If we time out, we don't really care.
//            ConsumerRecords<String, String> records = consumer.poll(200);
//            if (records.count() == 0) {
//                timeouts++;
//            } else {
//                // System.out.printf("Got %d records after %d timeouts\n", records.count(), timeouts);
//                Log.d("sunnyDay", "Got .. records after .. timeouts.");
//                timeouts = 0;
//            }
//            for (ConsumerRecord<String, String> record : records) {
//                switch (record.topic()) {
//                    case "fast-messages":
//                        // the send time is encoded inside the message
//                        JsonNode msg = mapper.readTree(record.value());
//                        switch (msg.get("type").asText()) {
//                            case "test":
//                                long latency = (long) ((System.nanoTime() * 1e-9 - msg.get("t").asDouble()) * 1000);
//                                stats.recordValue(latency);
//                                global.recordValue(latency);
//                                break;
//                            case "marker":
//                                // whenever we get a marker message, we should dump out the stats
//                                // note that the number of fast messages won't necessarily be quite constant
//                                /*
//                                System.out.printf("%d messages received in period, latency(min, max, avg, 99%%) = %d, %d, %.1f, %d (ms)\n",
//                                        stats.getTotalCount(),
//                                        stats.getValueAtPercentile(0), stats.getValueAtPercentile(100),
//                                        stats.getMean(), stats.getValueAtPercentile(99));
//                                System.out.printf("%d messages received overall, latency(min, max, avg, 99%%) = %d, %d, %.1f, %d (ms)\n",
//                                        global.getTotalCount(),
//                                        global.getValueAtPercentile(0), global.getValueAtPercentile(100),
//                                        global.getMean(), global.getValueAtPercentile(99));
//                                */
//                                Log.d("sunnyDay", "marker message received.");
//
//                                stats.reset();
//                                break;
//                            default:
//                                throw new IllegalArgumentException("Illegal message type: " + msg.get("type"));
//                        }
//                        break;
//                    case "summary-markers":
//                        break;
//                    default:
//                        throw new IllegalStateException("Shouldn't be possible to get message on topic " + record.topic());
//                }
//            }
//        }
//    }

    public static void startConsumer2() {
        Properties props = new Properties();
        props.put("bootstrap.servers", "localhost:2181"); // 9092 on Apache website
        props.put("group.id", "testing"); // ID of the consumer group
        props.put("enable.auto.commit", "true");
        props.put("auto.commit.interval.ms", "1000");
        props.put("session.timeout.ms", "30000");
        props.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        props.put("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");

        KafkaConsumer<String, String> consumer = new KafkaConsumer<>(props);
        consumer.subscribe(Arrays.asList("testing")); // subscribe to topic "testing"

        while (true) {
            ConsumerRecords<String, String> records = consumer.poll(100);
            for (ConsumerRecord<String, String> record : records) {
                long offset = record.offset();
                String key = record.key();
                String value = record.value();

                String output = "Data retrieved: offset = " + offset + ", key = " + key + ", value = " + value;
                Log.d("sunnyDay",output);
                //System.out.printf("offset = %d, key = %s, value = %s", record.offset(), record.key(), record.value());
            }
        }
    }
}
