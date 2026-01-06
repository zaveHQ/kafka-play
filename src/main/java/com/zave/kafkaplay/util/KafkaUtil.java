package com.zave.kafkaplay.util;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.producer.ProducerRecord;

import com.zave.kafkaplay.model.RecordInfo;

public class KafkaUtil {

    private KafkaUtil() {
        // Utility class - prevent instantiation
    }

    public static ProducerRecord<String, String> getProducerRecord(RecordInfo recordInfo) {
        if (recordInfo.getPartition() != null) {
            return new ProducerRecord<>(
                recordInfo.getTopic(),
                recordInfo.getPartition(),
                recordInfo.getKey(),
                recordInfo.getValue()
            );
        } else {
            return new ProducerRecord<>(
                recordInfo.getTopic(),
                recordInfo.getKey(),
                recordInfo.getValue()
            );
        }
    }

    public static RecordInfo getRecordInfo(ConsumerRecord<String, String> consumerRecord) {
        return new RecordInfo(
            consumerRecord.topic(),
            consumerRecord.partition(),
            consumerRecord.offset(),
            consumerRecord.timestamp(),
            consumerRecord.key(),
            consumerRecord.value()
        );
    }
}
