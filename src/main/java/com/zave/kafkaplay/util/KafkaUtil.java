package com.zave.kafkaplay.util;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.OffsetAndMetadata;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.TopicPartition;

import com.zave.kafkaplay.model.RecordInfo;
import com.zave.kafkaplay.model.consumergroup.ConsumerGroupOffsetsInfo;

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

    public static Map<TopicPartition, OffsetAndMetadata> getTpVsOffsetMeta(List<ConsumerGroupOffsetsInfo> consumerGroupOffsetsInfo) {
        Map<TopicPartition, OffsetAndMetadata> tpVsOffsetMeta = new HashMap<>();

        for (ConsumerGroupOffsetsInfo info : consumerGroupOffsetsInfo) {
            TopicPartition tp = new TopicPartition(info.getTopic(), info.getPartition());
            OffsetAndMetadata offsetAndMetadata = new OffsetAndMetadata(info.getOffset(), info.getMetadata());
            tpVsOffsetMeta.put(tp, offsetAndMetadata);
        }
        return tpVsOffsetMeta;
    }
}
