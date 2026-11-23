package com.zave.kafkaplay.kafka;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ExecutionException;
import java.util.function.Function;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import org.apache.kafka.clients.admin.AdminClient;
import org.apache.kafka.clients.admin.AlterConfigOp;
import org.apache.kafka.clients.admin.AlterConfigOp.OpType;
import org.apache.kafka.clients.admin.ConfigEntry;
import org.apache.kafka.clients.admin.ConsumerGroupDescription;
import org.apache.kafka.clients.admin.DescribeConsumerGroupsResult;
import org.apache.kafka.clients.admin.GroupListing;
import org.apache.kafka.clients.admin.ListOffsetsResult.ListOffsetsResultInfo;
import org.apache.kafka.clients.admin.NewTopic;
import org.apache.kafka.clients.admin.OffsetSpec;
import org.apache.kafka.clients.admin.TopicDescription;
import org.apache.kafka.clients.consumer.OffsetAndMetadata;
import org.apache.kafka.common.KafkaFuture;
import org.apache.kafka.common.TopicPartition;
import org.apache.kafka.common.config.ConfigResource;
import org.apache.kafka.common.config.ConfigResource.Type;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.config.TopicBuilder;
import org.springframework.stereotype.Service;

import com.zave.kafkaplay.model.TopicRequest;

@Service
public class KafkaAdminServiceImpl implements KafkaAdminService {

    private static final Logger LOGGER = Logger.getLogger(KafkaAdminServiceImpl.class.getName());

    private final AdminClient adminClient;

    @Value("${kafka.bootstrap-servers}")
    private String bootstrapServers;

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

	@Override
	public Set<String> listConsumerGroups() throws Exception
	{
		return adminClient.listGroups().all().get().stream()
				.map(GroupListing::groupId)
				.collect(Collectors.toSet());
	}

	@Override
	public ConsumerGroupDescription describeConsumerGroup(String consumerGroup) throws Exception
	{
		DescribeConsumerGroupsResult describeConsumerGroupsResult = adminClient.describeConsumerGroups(Collections.singletonList(consumerGroup));
		Map<String, KafkaFuture<ConsumerGroupDescription>> futures = describeConsumerGroupsResult.describedGroups();
		return futures.get(consumerGroup).get();
	}

	@Override
	public Map<TopicPartition, OffsetAndMetadata> listConsumerGroupOffsets(String consumerGroup) throws InterruptedException, ExecutionException
	{
		Map<TopicPartition, OffsetAndMetadata> consumerGroupOffsetsMap = adminClient.listConsumerGroupOffsets(consumerGroup).partitionsToOffsetAndMetadata().get();
		consumerGroupOffsetsMap.replaceAll((topicPartition, offsetAndMetadata) -> new OffsetAndMetadata(offsetAndMetadata.offset(), offsetAndMetadata.metadata()));
		return consumerGroupOffsetsMap;
	}

	@Override
	public void alterConsumerGroupOffsets(String consumerGroup, Map<TopicPartition, OffsetAndMetadata> tpVsOffsetMeta) throws Exception
	{
		adminClient.alterConsumerGroupOffsets(consumerGroup, tpVsOffsetMeta).all().get();
		LOGGER.log(Level.INFO, "Consumer group offsets altered for group {0}", consumerGroup);
	}

	@Override
	public String getTopicConfig(String topic, String config) throws Exception
	{
		return adminClient
			.describeConfigs(Collections.singleton(new ConfigResource(Type.TOPIC, topic)))
			.all().get()
			.get(new ConfigResource(Type.TOPIC, topic))
			.get(config)
			.value();
	}

	@Override
	public void alterTopicConfig(String topic, String config, String value) throws Exception
	{
		Map<ConfigResource, Collection<AlterConfigOp>> op = new HashMap<>();
		op.put(new ConfigResource(Type.TOPIC, topic), Collections.singleton(new AlterConfigOp(new ConfigEntry(config, value), OpType.SET)));
		adminClient.incrementalAlterConfigs(op).all().get();
		LOGGER.log(Level.INFO, "Topic config alter successful for topic {0}", topic);
	}

	@Override
	public Map<TopicPartition, ListOffsetsResultInfo> getOffsetInfo(Set<TopicPartition> topicPartitions, OffsetSpec offsetSpec) throws Exception
	{
		Map<TopicPartition, OffsetSpec> topicPartitionVsOffsetSpec = topicPartitions.stream().collect(Collectors.toMap(Function.identity(), e -> offsetSpec));
		return adminClient.listOffsets(topicPartitionVsOffsetSpec).all().get();
	}
}
