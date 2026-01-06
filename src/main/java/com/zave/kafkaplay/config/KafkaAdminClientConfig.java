package com.zave.kafkaplay.config;

import java.util.HashMap;
import java.util.Map;

import org.apache.kafka.clients.admin.AdminClient;
import org.apache.kafka.clients.admin.AdminClientConfig;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class KafkaAdminClientConfig {

    private final KafkaCommonProperties kafkaCommonProperties;

    public KafkaAdminClientConfig(KafkaCommonProperties kafkaCommonProperties) {
        this.kafkaCommonProperties = kafkaCommonProperties;
    }

    @Bean
    public AdminClient adminClient() {
        Map<String, Object> configs = new HashMap<>();
        configs.put(AdminClientConfig.BOOTSTRAP_SERVERS_CONFIG, kafkaCommonProperties.getBootstrapServers());
        return AdminClient.create(configs);
    }
}
