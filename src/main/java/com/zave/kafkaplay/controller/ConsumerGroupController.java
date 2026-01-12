package com.zave.kafkaplay.controller;

import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.kafka.clients.admin.ConsumerGroupDescription;
import org.apache.kafka.clients.consumer.OffsetAndMetadata;
import org.apache.kafka.common.TopicPartition;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.zave.kafkaplay.kafka.KafkaAdminService;
import com.zave.kafkaplay.model.consumergroup.ConsumerGroupInfo;
import com.zave.kafkaplay.model.consumergroup.ConsumerGroupOffsetsInfo;
import com.zave.kafkaplay.util.KafkaUtil;

@RestController
@RequestMapping("/api/consumerGroup")
public class ConsumerGroupController {

    private final KafkaAdminService kafkaAdminService;

    public ConsumerGroupController(KafkaAdminService kafkaAdminService) {
        this.kafkaAdminService = kafkaAdminService;
    }

    @GetMapping("/list")
    public ResponseEntity<?> listConsumerGroups() {
        try {
            Set<String> groups = kafkaAdminService.listConsumerGroups();
            return ResponseEntity.ok(groups);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of(
                "error", "Failed to list consumer groups: " + e.getMessage()
            ));
        }
    }

    @GetMapping("/describe")
    public ResponseEntity<?> describeConsumerGroup(@RequestParam String groupId) {
        try {
            ConsumerGroupDescription description = kafkaAdminService.describeConsumerGroup(groupId);
            
            ConsumerGroupInfo groupInfo = new ConsumerGroupInfo(
                description.groupId(),
                description.groupState().toString(),
                description.members().stream()
                    .map(m -> new ConsumerGroupInfo.MemberInfo(
                        m.consumerId(),
                        m.clientId(),
                        m.host()
                    ))
                    .toList()
            );

            return ResponseEntity.ok(groupInfo);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of(
                "error", "Failed to describe consumer group: " + e.getMessage()
            ));
        }
    }

    @GetMapping("/offsets")
    public ResponseEntity<?> getConsumerGroupOffsets(@RequestParam String groupId) {
        try {
            Map<TopicPartition, OffsetAndMetadata> offsets = kafkaAdminService.listConsumerGroupOffsets(groupId);
            
            List<ConsumerGroupOffsetsInfo> result = offsets.entrySet().stream()
                .map(entry -> new ConsumerGroupOffsetsInfo(
                    entry.getKey().topic(),
                    entry.getKey().partition(),
                    entry.getValue().offset(),
                    entry.getValue().metadata() != null ? entry.getValue().metadata() : ""
                ))
                .toList();

            return ResponseEntity.ok(result);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of(
                "error", "Failed to get consumer group offsets: " + e.getMessage()
            ));
        }
    }

    @PutMapping("/offsets/alter")
    public ResponseEntity<?> alterConsumerGroupOffsets(@RequestParam String groupId, @RequestBody List<ConsumerGroupOffsetsInfo> request) {
        try {
            kafkaAdminService.alterConsumerGroupOffsets(groupId, KafkaUtil.getTpVsOffsetMeta(request));
            return ResponseEntity.ok(Map.of(
                "message", "Consumer group offsets altered successfully."
            ));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of(
                "error", "Failed to alter consumer group offsets: " + e.getMessage()
            ));
        }
    }
}
