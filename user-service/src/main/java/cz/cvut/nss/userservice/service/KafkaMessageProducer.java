package cz.cvut.nss.userservice.service;

import cz.cvut.nss.userservice.model.KafkaMessage;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
@Slf4j
public class KafkaMessageProducer {

    private KafkaTemplate<String, KafkaMessage> kafkaTemplate;

    public void sendMessage(String topic, KafkaMessage message) {
        kafkaTemplate.send(topic, message);
        log.info("A request to create a user with an ID {} has been sent to {}", message.getId(), topic);
    }

}

