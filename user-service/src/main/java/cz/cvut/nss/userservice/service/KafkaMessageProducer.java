package cz.cvut.nss.userservice.service;

import cz.cvut.nss.userservice.model.KafkaMessage;
import lombok.AllArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class KafkaMessageProducer {

    private KafkaTemplate<String, KafkaMessage> kafkaTemplate;

    public void sendMessage(String topic, KafkaMessage message) {
        kafkaTemplate.send(topic, message);
    }

}

