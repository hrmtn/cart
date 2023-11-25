package com.github.hrmtn.cart.messaging.orders;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class ValidatedOrdersMessagesProducer {

    private static String VALIDATED_ORDER_TOPIC_NAME = "validated-orders";

    private final ObjectMapper objectMapper;

    private final KafkaTemplate<String, String> kafkaTemplate;

    public void sendMessage(Object obj) {
        try {
            kafkaTemplate.send(VALIDATED_ORDER_TOPIC_NAME, this.objectMapper.writeValueAsString(obj));
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}

