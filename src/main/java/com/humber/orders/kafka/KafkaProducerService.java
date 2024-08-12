package com.humber.orders.kafka;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Properties;


@Service
public class KafkaProducerService {

    @Value("${spring.kafka.producer.bootstrap-servers}")
    private String bootstrapServers;

    @Value("${spring.kafka.producer.topic.order-placed}")
    private String orderPlacedTopic;

    private final KafkaProducer<String, String> producer;
    private final ObjectMapper objectMapper;

    public KafkaProducerService() {
        this.objectMapper = new ObjectMapper(); // Ensure ObjectMapper is initialized

        Properties properties = new Properties();
        properties.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        properties.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        properties.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        properties.put(ProducerConfig.ACKS_CONFIG, "all"); // Ensure all replicas acknowledge

        this.producer = new KafkaProducer<>(properties);
    }

    public void sendOrderPlacedEvent(OrderPlacedEvent event) {
        try {
            String eventJson = objectMapper.writeValueAsString(event);
            ProducerRecord<String, String> record = new ProducerRecord<>(orderPlacedTopic, event.getOrderId(), eventJson);
            producer.send(record, (metadata, exception) -> {
                if (exception != null) {
                    System.err.printf("Failed to send message: %s%n", exception.getMessage());
                    exception.printStackTrace();
                } else {
                    System.out.printf("Sent message to topic %s with offset %d%n", metadata.topic(), metadata.offset());
                }
            });
        } catch (JsonProcessingException e) {
            System.err.printf("Failed to serialize event: %s%n", e.getMessage());
            e.printStackTrace();
        }
    }

    // Close the producer to release resources
    public void close() {
        producer.close();
    }
}




