package io.conduktor.demos.kafka;

import org.apache.kafka.clients.producer.*;
import org.apache.kafka.common.serialization.StringSerializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Properties;

public class ProducerDemoWithCallback {

    private static final Logger log = LoggerFactory.getLogger(ProducerDemoWithCallback.class.getSimpleName());


    public static void main(String[] args) {

        Properties properties = new Properties();

        properties.setProperty(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9094");
        properties.setProperty(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        properties.setProperty(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());

        KafkaProducer<String, String> producer = new KafkaProducer<>(properties);

        for (int i = 0; i < 10; i++) {

            ProducerRecord<String, String> producerRecord = new ProducerRecord<>("demo_java", "hello world " + i);
            producer.send(producerRecord, (metadata, exception) -> {
                if (exception == null) {
                    log.info("Received metadata. Topic: " + metadata.topic() + " Partition:" + metadata.partition() + " Offset:" + metadata.offset());
                }
            });
        }

        producer.flush();
        producer.close();
    }
}
