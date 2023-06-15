package cz.cvut.nss.userservice;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(value = "us")
public record ApplicationProperties(
        String secretKey,
        String basicAvatarUrl) {
}
