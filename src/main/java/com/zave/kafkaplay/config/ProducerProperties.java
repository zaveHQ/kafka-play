package com.zave.kafkaplay.config;

import java.util.HashMap;
import java.util.Map;

import org.apache.kafka.clients.producer.ProducerConfig;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "kafka.producer")
public class ProducerProperties {

    private final KafkaCommonProperties kafkaCommonProperties;

    private String keySerializer;
    private String valueSerializer;
    private String acks;
    private String retries;
    private String batchSize;
    private String lingerMs;
    private String bufferMemory;

    public ProducerProperties(KafkaCommonProperties kafkaCommonProperties) {
        this.kafkaCommonProperties = kafkaCommonProperties;
    }

    public String getKeySerializer() {
        return keySerializer;
    }

    public void setKeySerializer(String keySerializer) {
        this.keySerializer = keySerializer;
    }

    public String getValueSerializer() {
        return valueSerializer;
    }

    public void setValueSerializer(String valueSerializer) {
        this.valueSerializer = valueSerializer;
    }

    public String getAcks() {
        return acks;
    }

    public void setAcks(String acks) {
        this.acks = acks;
    }

    public String getRetries() {
        return retries;
    }

    public void setRetries(String retries) {
        this.retries = retries;
    }

    public String getBatchSize() {
        return batchSize;
    }

    public void setBatchSize(String batchSize) {
        this.batchSize = batchSize;
    }

    public String getLingerMs() {
        return lingerMs;
    }

    public void setLingerMs(String lingerMs) {
        this.lingerMs = lingerMs;
    }

    public String getBufferMemory() {
        return bufferMemory;
    }

    public void setBufferMemory(String bufferMemory) {
        this.bufferMemory = bufferMemory;
    }

    /**
     * Returns all producer properties as a Map (useful for Spring Kafka factories)
     */
    public Map<String, Object> toMap() {
        Map<String, Object> producerProperties = new HashMap<>();

        if(kafkaCommonProperties != null) {
            producerProperties.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, kafkaCommonProperties.getBootstrapServers());
        }

        if (keySerializer != null) {
            producerProperties.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, keySerializer);
        }
        if (valueSerializer != null) {
            producerProperties.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, valueSerializer);
        }
        if (acks != null) {
            producerProperties.put(ProducerConfig.ACKS_CONFIG, acks);
        }
        if (retries != null) {
            producerProperties.put(ProducerConfig.RETRIES_CONFIG, retries);
        }
        if (batchSize != null) {
            producerProperties.put(ProducerConfig.BATCH_SIZE_CONFIG, batchSize);
        }
        if (lingerMs != null) {
            producerProperties.put(ProducerConfig.LINGER_MS_CONFIG, lingerMs);
        }
        if (bufferMemory != null) {
            producerProperties.put(ProducerConfig.BUFFER_MEMORY_CONFIG, bufferMemory);
        }

        return producerProperties;
    }
}
