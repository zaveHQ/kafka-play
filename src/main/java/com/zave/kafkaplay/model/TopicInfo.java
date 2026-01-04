package com.zave.kafkaplay.model;

import java.util.List;

import org.apache.kafka.clients.admin.TopicDescription;
import org.apache.kafka.common.TopicPartitionInfo;

public class TopicInfo {

    private String topicName;
    private int partitionCount;
    private int replicationFactor;
    private List<PartitionInfo> partitionDetails;

    public TopicInfo() {
    }

    public TopicInfo(TopicDescription topicDescription) {
        this.topicName = topicDescription.name();
        this.partitionCount = topicDescription.partitions().size();
        this.replicationFactor = topicDescription.partitions().isEmpty() ? 0 : topicDescription.partitions().get(0).replicas().size();
        this.partitionDetails = topicDescription.partitions().stream()
                .map(PartitionInfo::new)
                .toList();
    }

    public TopicInfo(String name, int partitions, int replicationFactor) {
        this.topicName = name;
        this.partitionCount = partitions;
        this.replicationFactor = replicationFactor;
    }

    public String getTopicName() {
        return topicName;
    }

    public void setTopicName(String topicName) {
        this.topicName = topicName;
    }

    public int getPartitionCount() {
        return partitionCount;
    }

    public void setPartitionCount(int partitionCount) {
        this.partitionCount = partitionCount;
    }

    public int getReplicationFactor() {
        return replicationFactor;
    }

    public void setReplicationFactor(int replicationFactor) {
        this.replicationFactor = replicationFactor;
    }

    public List<PartitionInfo> getPartitionDetails() {
        return partitionDetails;
    }

    public void setPartitionDetails(List<PartitionInfo> partitionDetails) {
        this.partitionDetails = partitionDetails;
    }

    public static class PartitionInfo {
        private int partition;
        private int leader;
        private List<Integer> replicas;
        private List<Integer> isr;

        public PartitionInfo() {
        }

        public PartitionInfo(TopicPartitionInfo topicPartitionInfo){
            this.partition = topicPartitionInfo.partition();
            this.leader = topicPartitionInfo.leader() != null ? topicPartitionInfo.leader().id() : -1;
            this.replicas = topicPartitionInfo.replicas().stream().map(n -> n.id()).toList();
            this.isr = topicPartitionInfo.isr().stream().map(n -> n.id()).toList();
        }

        public PartitionInfo(int partition, int leader, List<Integer> replicas, List<Integer> isr) {
            this.partition = partition;
            this.leader = leader;
            this.replicas = replicas;
            this.isr = isr;
        }

        public int getPartition() {
            return partition;
        }

        public void setPartition(int partition) {
            this.partition = partition;
        }

        public int getLeader() {
            return leader;
        }

        public void setLeader(int leader) {
            this.leader = leader;
        }

        public List<Integer> getReplicas() {
            return replicas;
        }

        public void setReplicas(List<Integer> replicas) {
            this.replicas = replicas;
        }

        public List<Integer> getIsr() {
            return isr;
        }

        public void setIsr(List<Integer> isr) {
            this.isr = isr;
        }
    }
}
