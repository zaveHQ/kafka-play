package com.zave.kafkaplay.model.consumergroup;

public class ConsumeRequest {
    private String topic;
    private int partition;
    private String groupId;
    private Long startOffset;
    private Long endOffset;
    private boolean commitNeeded;

    public ConsumeRequest() {
    }

    public ConsumeRequest(String topic, int partition, String groupId, Long startOffset, Long endOffset, boolean commitNeeded) {
        this.topic = topic;
        this.partition = partition;
        this.groupId = groupId;
        this.startOffset = startOffset;
        this.endOffset = endOffset;
        this.commitNeeded = commitNeeded;
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

    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    public Long getStartOffset() {
        return startOffset;
    }

    public void setStartOffset(Long startOffset) {
        this.startOffset = startOffset;
    }

    public Long getEndOffset() {
        return endOffset;
    }

    public void setEndOffset(Long endOffset) {
        this.endOffset = endOffset;
    }

    public boolean isCommitNeeded() {
        return commitNeeded;
    }

    public void setCommitNeeded(boolean commitNeeded) {
        this.commitNeeded = commitNeeded;
    }
}
