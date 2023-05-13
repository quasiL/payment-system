package cz.cvut.nss.paymentprocessingservice.config;

import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.map.IMap;
import cz.cvut.nss.paymentprocessingservice.model.Payment;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

@AllArgsConstructor
@Configuration
public class PaymentCacheConfig {

    private HazelcastInstance hazelcastInstance;

    @Bean
    public IMap<String, List<Payment>> paymentCache() {
        IMap<String, List<Payment>> cache = hazelcastInstance.getMap("paymentCache");
        ScheduledExecutorService executorService = Executors.newSingleThreadScheduledExecutor();
        executorService.scheduleAtFixedRate(cache::clear, 0, 60, TimeUnit.SECONDS);
        return cache;
    }
}
