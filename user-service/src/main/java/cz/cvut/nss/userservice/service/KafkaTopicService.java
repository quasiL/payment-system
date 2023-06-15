package cz.cvut.nss.userservice.service;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.admin.AdminClient;
import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.kafka.core.KafkaAdmin;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.concurrent.ExecutionException;

@Service
@RequiredArgsConstructor
@Slf4j
public class KafkaTopicService {

    private final AdminClient adminClient;
    private final KafkaAdmin kafkaAdmin;

    public void createTopic(String topicName, int partitions, short replicationFactor)
            throws ExecutionException, InterruptedException {
        NewTopic newTopic = new NewTopic(topicName, partitions, replicationFactor);
        adminClient.createTopics(Collections.singleton(newTopic)).all().get();
    }

    @PostConstruct
    public void createUserTopic() throws ExecutionException, InterruptedException {
        String topicName = "user-topic";

        try (final AdminClient admin = AdminClient.create(kafkaAdmin.getConfigurationProperties())) {
            boolean topicExists = admin.listTopics().names().get().contains(topicName);

            if (!topicExists) {
                this.createTopic(topicName, 1, (short) 1);
                log.info("Topic created: {}", topicName);
            } else {
                log.info("Topic already exists: {}", topicName);
            }
        }
    }
}

