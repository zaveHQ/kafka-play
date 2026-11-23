package com.zave.kafkaplay.model.consumergroup;

public class ConsumerGroupOffsetsInfo {

    private String topic;
    private int partition;
    private long offset;
    private String metadata;

    public ConsumerGroupOffsetsInfo() {
    }

    public ConsumerGroupOffsetsInfo(String topic, int partition, long offset, String metadata) {
        this.topic = topic;
        this.partition = partition;
        this.offset = offset;
        this.metadata = metadata;
    }

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public int getPartition() {
        return partition;
    }

    public void setPartition(int partition) {
        this.partition = partition;
    }

    public long getOffset() {
        return offset;
    }

    public void setOffset(long offset) {
        this.offset = offset;
    }

    public String getMetadata() {
        return metadata;
    }

    public void setMetadata(String metadata) {
        this.metadata = metadata;
    }
}