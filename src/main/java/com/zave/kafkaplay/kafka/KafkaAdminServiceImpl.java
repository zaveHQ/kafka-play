package com.zave.kafkaplay.kafka;

import java.util.Collections;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.kafka.clients.admin.AdminClient;
import org.apache.kafka.clients.admin.NewTopic;
import org.apache.kafka.clients.admin.TopicDescription;
import org.springframework.kafka.config.TopicBuilder;
import org.springframework.stereotype.Service;

import com.zave.kafkaplay.model.TopicRequest;

@Service
public class KafkaAdminServiceImpl implements KafkaAdminService {

    private static final Logger LOGGER = Logger.getLogger(KafkaAdminServiceImpl.class.getName());

    private final AdminClient adminClient;

	public KafkaAdminServiceImpl(AdminClient adminClient) {
		this.adminClient = adminClient;
	}

	@Override
	public Set<String> listTopics() throws Exception
	{
		return adminClient.listTopics().names().get();
	}

	@Override
	public void createTopic(TopicRequest topicRequest) throws Exception
	{
        NewTopic newTopic = TopicBuilder.name(topicRequest.getTopicName())
                .partitions(topicRequest.getPartitionCount())
                .replicas(topicRequest.getReplicationFactor())
                .build();

		adminClient.createTopics(Collections.singleton(newTopic)).all().get();

		LOGGER.log(Level.INFO, "Topic {0} with {1} partitions created.", new Object[] { topicRequest.getTopicName(), topicRequest.getPartitionCount() });
	}

	@Override
	public TopicDescription describeTopic(String topic) throws Exception
	{
		return adminClient.describeTopics(Collections.singleton(topic)).allTopicNames().get().get(topic);
	}

	@Override
	public void deleteTopic(String topic) throws Exception
	{
		adminClient.deleteTopics(Collections.singleton(topic)).all().get();
		LOGGER.log(Level.INFO, "Topic {0} deleted.", topic);
	}
}
