package com.zave.kafkaplay.model;

public class RecordInfo {
    private String topic;
    private Integer partition;
    private Long offset;
    private Long timestamp;
    private String key;
    private String value;

    public RecordInfo() {
    }

    public RecordInfo(String topic, int partition, String key, String value) {
        this(topic, partition, null, null, key, value);
    }

    public RecordInfo(String topic, int partition, Long offset, Long timestamp, String key, String value) {
        this.topic = topic;
        this.partition = partition;
        this.offset = offset;
        this.timestamp = timestamp;
        this.key = key;
        this.value = value;
    }

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public Integer getPartition() {
        return partition;
    }

    public void setPartition(Integer partition) {
        this.partition = partition;
    }

    public Long getOffset() {
        return offset;
    }

    public void setOffset(Long offset) {
        this.offset = offset;
    }

    public Long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Long timestamp) {
        this.timestamp = timestamp;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
