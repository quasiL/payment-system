package cz.cvut.nss.riskmanagementservice.service;

import cz.cvut.nss.riskmanagementservice.model.KafkaMessage;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@AllArgsConstructor
@Slf4j
public class KafkaMessageConsumer {

    private final RestTemplate restTemplate;

    @KafkaListener(topics = "risk-topic")
    public void receiveNewTransactionMessage(KafkaMessage message) {

        //Validate new transaction
        //String otherMicroserviceUrl = "http://localhost:9999";
        //ResponseEntity<String> response = restTemplate.postForEntity(otherMicroserviceUrl, message, String.class);
        log.info("Transaction with ID {} is valid", message.getId());
    }
}