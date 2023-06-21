package cz.cvut.nss.paymentprocessingservice.service;

import cz.cvut.nss.paymentprocessingservice.dto.KafkaMessage;
import cz.cvut.nss.paymentprocessingservice.model.UserAccount;
import cz.cvut.nss.paymentprocessingservice.repository.UserAccountRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.security.SecureRandom;
import java.util.Random;

@Service
@AllArgsConstructor
@Slf4j
public class KafkaMessageConsumer {

    private final UserAccountRepository userAccountRepository;

    @KafkaListener(topics = "user-topic")
    public void receiveNewAccountMessage(KafkaMessage message) {
        String accountNumber = generateAccountNumber();
        userAccountRepository.save(UserAccount.builder()
                .userId(message.getId())
                .account(accountNumber)
                .balance(BigDecimal.valueOf(0))
                .build()
        );
        log.info("User account {} for user with ID {} has been created", accountNumber, message.getId());
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