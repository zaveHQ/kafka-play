package com.zave.kafkaplay.controller;

import java.util.Map;
import java.util.Set;

import org.apache.kafka.clients.admin.TopicDescription;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.zave.kafkaplay.kafka.KafkaAdminService;
import com.zave.kafkaplay.model.TopicInfo;
import com.zave.kafkaplay.model.TopicRequest;

@RestController
@RequestMapping("/api/topic")
public class TopicController {

    private final KafkaAdminService kafkaAdminService;

    public TopicController(KafkaAdminService kafkaAdminService) {
        this.kafkaAdminService = kafkaAdminService;
    }

    @GetMapping("/list")
    public ResponseEntity<?> listTopics() {
        try {
            Set<String> topics = kafkaAdminService.listTopics();
            return ResponseEntity.ok(topics);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of(
                "error", "Failed to list topics: " + e.getMessage()
            ));
        }
    }

    @PostMapping("/create")
    public ResponseEntity<?> createTopic(@RequestBody TopicRequest topicRequest) {
        try {
            kafkaAdminService.createTopic(topicRequest);
            return ResponseEntity.ok(Map.of(
                "message", "Topic created successfully",
                "topic", topicRequest.getTopicName()
            ));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of(
                "error", "Failed to create topic: " + e.getMessage()
            ));
        }
    }

    @GetMapping("/describe")
    public ResponseEntity<?> describeTopic(@RequestParam String topicName) {
        try {
            TopicDescription topicDescription = kafkaAdminService.describeTopic(topicName);
            
            if (topicDescription == null) {
                return ResponseEntity.notFound().build();
            }
            return ResponseEntity.ok(new TopicInfo(topicDescription));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of(
                "error", "Failed to describe topic: " + e.getMessage()
            ));
        }
    }

    @DeleteMapping("/delete")
    public ResponseEntity<?> deleteTopic(@RequestParam String topicName) {
        try {
            kafkaAdminService.deleteTopic(topicName);
            return ResponseEntity.ok(Map.of(
                "message", "Topic deleted successfully",
                "topic", topicName
            ));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of(
                "error", "Failed to delete topic: " + e.getMessage()
            ));
        }
    }
}
