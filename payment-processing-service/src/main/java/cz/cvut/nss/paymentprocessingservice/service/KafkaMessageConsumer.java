package cz.cvut.nss.paymentprocessingservice.service;

import cz.cvut.nss.paymentprocessingservice.dto.KafkaMessage;
import cz.cvut.nss.paymentprocessingservice.model.UserAccount;
import cz.cvut.nss.paymentprocessingservice.repository.UserAccountRepository;
import lombok.AllArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.security.SecureRandom;
import java.util.Random;

@Service
@AllArgsConstructor
public class KafkaMessageConsumer {

    private final RestTemplate restTemplate;
    private final UserAccountRepository userAccountRepository;

    @KafkaListener(topics = "user-topic")
    public void receiveNewAccountMessage(KafkaMessage message) {

        System.out.println("MESSAGE RECEIVED!");
        String accountNumber = generateAccountNumber();

        userAccountRepository.save(UserAccount.builder()
                .userId(message.getId())
                .account(accountNumber)
                .balance(BigDecimal.valueOf(0))
                .build()
        );
        System.out.println(accountNumber);

        // Call the endpoint in the other microservice
        //String otherMicroserviceUrl = "http://localhost:";
        //ResponseEntity<String> response = restTemplate.postForEntity(otherMicroserviceUrl, message, String.class);
    }

    private String generateAccountNumber() {
        final Random rand = new SecureRandom();
        while (true) {
            String accountNumber = Integer.toString(rand.nextInt(900000) + 100000);
            if (userAccountRepository.findByAccount(accountNumber).isEmpty()) {
                return accountNumber;
            }
        }

    }

}