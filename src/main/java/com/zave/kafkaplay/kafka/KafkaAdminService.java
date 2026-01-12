package com.zave.kafkaplay.kafka;

import java.util.Map;
import java.util.Set;

import org.apache.kafka.clients.admin.ConsumerGroupDescription;
import org.apache.kafka.clients.admin.ListOffsetsResult.ListOffsetsResultInfo;
import org.apache.kafka.clients.admin.OffsetSpec;
import org.apache.kafka.clients.admin.TopicDescription;
import org.apache.kafka.clients.consumer.OffsetAndMetadata;
import org.apache.kafka.common.TopicPartition;

import com.zave.kafkaplay.model.TopicRequest;

public interface KafkaAdminService {

    Set<String> listTopics() throws Exception;

    void createTopic(TopicRequest topicRequest) throws Exception;

    TopicDescription describeTopic(String topic) throws Exception;
    
    void deleteTopic(String topic) throws Exception;

    Set<String> listConsumerGroups() throws Exception;

    ConsumerGroupDescription describeConsumerGroup(String consumerGroup) throws Exception;

    Map<TopicPartition, OffsetAndMetadata> listConsumerGroupOffsets(String consumerGroup) throws Exception;

    void alterConsumerGroupOffsets(String consumerGroup, Map<TopicPartition, OffsetAndMetadata> tpVsOffsetMeta) throws Exception;

    String getTopicConfig(String topic, String config) throws Exception;

    void alterTopicConfig(String topic, String config, String value) throws Exception;

    Map<TopicPartition, ListOffsetsResultInfo> getOffsetInfo(Set<TopicPartition> topicPartitions, OffsetSpec offsetSpec) throws Exception;


}
