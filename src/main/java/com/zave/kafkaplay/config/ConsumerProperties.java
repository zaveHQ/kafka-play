package com.zave.kafkaplay.config;

import java.util.HashMap;
import java.util.Map;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "kafka.consumer")
public class ConsumerProperties {

    private final KafkaCommonProperties kafkaCommonProperties;

    private String groupId;
    private String keyDeserializer;
    private String valueDeserializer;
    private String autoOffsetReset;
    private String maxPollRecords;
    private String enableAutoCommit;

    public ConsumerProperties(KafkaCommonProperties kafkaCommonProperties) {
        this.kafkaCommonProperties = kafkaCommonProperties;
    }

    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    public String getKeyDeserializer() {
        return keyDeserializer;
    }

    public void setKeyDeserializer(String keyDeserializer) {
        this.keyDeserializer = keyDeserializer;
    }

    public String getValueDeserializer() {
        return valueDeserializer;
    }

    public void setValueDeserializer(String valueDeserializer) {
        this.valueDeserializer = valueDeserializer;
    }

    public String getAutoOffsetReset() {
        return autoOffsetReset;
    }

    public void setAutoOffsetReset(String autoOffsetReset) {
        this.autoOffsetReset = autoOffsetReset;
    }

    public String getMaxPollRecords() {
        return maxPollRecords;
    }

    public void setMaxPollRecords(String maxPollRecords) {
        this.maxPollRecords = maxPollRecords;
    }

    public String getEnableAutoCommit() {
        return enableAutoCommit;
    }

    public void setEnableAutoCommit(String enableAutoCommit) {
        this.enableAutoCommit = enableAutoCommit;
    }

    /**
     * Returns all consumer properties as a Map (useful for Spring Kafka factories)
     */
    public Map<String, Object> toMap() {
        Map<String, Object> consumerProperties = new HashMap<>();

        if(kafkaCommonProperties != null) {
            consumerProperties.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, kafkaCommonProperties.getBootstrapServers());
        }
        if (groupId != null) {
            consumerProperties.put(ConsumerConfig.GROUP_ID_CONFIG, groupId);
        }
        if (keyDeserializer != null) {
            consumerProperties.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, keyDeserializer);
        }
        if (valueDeserializer != null) {
            consumerProperties.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, valueDeserializer);
        }
        if (autoOffsetReset != null) {
            consumerProperties.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, autoOffsetReset);
        }
        if (maxPollRecords != null) {
            consumerProperties.put(ConsumerConfig.MAX_POLL_RECORDS_CONFIG, maxPollRecords);
        }
        if (enableAutoCommit != null) {
            consumerProperties.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, enableAutoCommit);
        }
        return consumerProperties;
    }
}
