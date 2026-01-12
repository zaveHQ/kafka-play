package com.zave.kafkaplay.model.consumergroup;

import java.util.List;

public class ConsumerGroupInfo {
    private String groupId;
    private String state;
    private List<MemberInfo> members;

    public ConsumerGroupInfo() {
    }

    public ConsumerGroupInfo(String groupId, String state, List<MemberInfo> members) {
        this.groupId = groupId;
        this.state = state;
        this.members = members;
    }

    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public List<MemberInfo> getMembers() {
        return members;
    }

    public void setMembers(List<MemberInfo> members) {
        this.members = members;
    }

    public static class MemberInfo {
        private String memberId;
        private String clientId;
        private String host;

        public MemberInfo() {
        }

        public MemberInfo(String memberId, String clientId, String host) {
            this.memberId = memberId;
            this.clientId = clientId;
            this.host = host;
        }

        public String getMemberId() {
            return memberId;
        }

        public void setMemberId(String memberId) {
            this.memberId = memberId;
        }

        public String getClientId() {
            return clientId;
        }

        public void setClientId(String clientId) {
            this.clientId = clientId;
        }

        public String getHost() {
            return host;
        }

        public void setHost(String host) {
            this.host = host;
        }
    }
}
