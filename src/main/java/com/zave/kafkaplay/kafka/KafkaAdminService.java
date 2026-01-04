package com.zave.kafkaplay.kafka;

import java.util.Set;

import org.apache.kafka.clients.admin.TopicDescription;
import com.zave.kafkaplay.model.TopicRequest;

public interface KafkaAdminService {

    Set<String> listTopics() throws Exception;

    void createTopic(TopicRequest topicRequest) throws Exception;

    TopicDescription describeTopic(String topic) throws Exception;
    
    void deleteTopic(String topic) throws Exception;
}
