package com.ucla.max.android_testing;

//import android.content.res.Resources;

import android.util.Log;

import com.google.common.io.Resources;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * Created by Max on 5/7/16.
 */
public class Producer {

    public static void startProducer1() throws IOException {

        // set up the producer
        KafkaProducer<String, String> producer;
        try (InputStream props = Resources.getResource("src/main/res/producer.props").openStream()) {
            Properties properties = new Properties();
            properties.load(props);
            producer = new KafkaProducer<>(properties);
        }

        try {
            for (int i = 0; i < 1000000; i++) {
                // send lots of messages
                producer.send(new ProducerRecord<String, String>(
                        "fast-messages",
                        String.format("{\"type\":\"test\", \"t\":%.3f, \"k\":%d}", System.nanoTime() * 1e-9, i)));

                // every so often send to a different topic
                if (i % 1000 == 0) {
                    producer.send(new ProducerRecord<String, String>(
                            "fast-messages",
                            String.format("{\"type\":\"marker\", \"t\":%.3f, \"k\":%d}", System.nanoTime() * 1e-9, i)));
                    producer.send(new ProducerRecord<String, String>(
                            "summary-markers",
                            String.format("{\"type\":\"other\", \"t\":%.3f, \"k\":%d}", System.nanoTime() * 1e-9, i)));
                    producer.flush();
                    System.out.println("Sent msg number " + i);
                }
            }
        } catch (Throwable throwable) {
            System.out.printf("%s", throwable.getStackTrace());
        } finally {
            producer.close();
        }

    }

    public static void startProducer2() {
        Log.d("sunnyDay", "starting startProducer2().");

        Properties props = new Properties();
        props.put("bootstrap.servers", "localhost:4242");
        props.put("acks", "all");
        props.put("retries", 0);
        props.put("batch.size", 16384);
        props.put("linger.ms", 1);
        props.put("buffer.memory", 33554432);
        props.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        props.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");

        org.apache.kafka.clients.producer.KafkaProducer<String, String> producer = new KafkaProducer<>(props);
        Log.d("sunnyDay", "producer initialization succeed.");
        for(int i = 0; i < 100; i++) {
            producer.send(new ProducerRecord<String, String>("my-topic", Integer.toString(i), Integer.toString(i)));
            Log.d("sunnyDay", "Entering for-loop for sending messages.");
        }

        producer.close();
    }

}