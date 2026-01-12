package com.zave.kafkaplay.service;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.TopicPartition;
import org.springframework.stereotype.Service;

import com.zave.kafkaplay.config.ConsumerProperties;
import com.zave.kafkaplay.model.RecordInfo;
import com.zave.kafkaplay.model.consumergroup.ConsumeRequest;
import com.zave.kafkaplay.util.KafkaUtil;

@Service
public class KafkaConsumerService {

    private static final Logger LOGGER = Logger.getLogger(KafkaConsumerService.class.getName());

    private final ConsumerProperties consumerProperties;

    public KafkaConsumerService(ConsumerProperties consumerProperties) {
        this.consumerProperties = consumerProperties;
    }

    public List<RecordInfo> consumeRecords(ConsumeRequest request) {

        List<RecordInfo> recordInfos = new ArrayList<>();

        consumerProperties.setGroupId(request.getGroupId());
        try (KafkaConsumer<String, String> consumer = new KafkaConsumer<>(consumerProperties.toMap())) {
            TopicPartition tp = new TopicPartition(request.getTopic(), request.getPartition());
            consumer.assign(List.of(tp));
            consumer.seek(tp, request.getStartOffset());

            while (true) {
                ConsumerRecords<String, String> consumerRecords = consumer.poll(Duration.ofMillis(100));

                for (ConsumerRecord<String, String> consumerRecord : consumerRecords) {
                    recordInfos.add(KafkaUtil.getRecordInfo(consumerRecord));
                    if (consumerRecord.offset() == request.getEndOffset())
                    {
                        if(request.isCommitNeeded()) {
                            consumer.commitSync();
                            LOGGER.log(Level.INFO, "Committed offset for group: {0}", request.getGroupId());
                        }
                        return recordInfos;
                    }
                }
            }
        }
    }
}