package com.zave.kafkaplay.controller;

import java.util.List;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.zave.kafkaplay.model.RecordInfo;
import com.zave.kafkaplay.model.consumergroup.ConsumeRequest;
import com.zave.kafkaplay.service.KafkaConsumerService;

@RestController
@RequestMapping("/api/consumer")
public class ConsumerController {

    private final KafkaConsumerService kafkaConsumerService;

    public ConsumerController(KafkaConsumerService kafkaConsumerService) {
        this.kafkaConsumerService = kafkaConsumerService;
    }

    @GetMapping("/consume/single")
    public ResponseEntity<Map<String, Object>> consumeSingle(@RequestBody ConsumeRequest request) {

        try {
            request.setEndOffset(request.getStartOffset());
            List<RecordInfo> recordInfos = kafkaConsumerService.consumeRecords(request);
            return ResponseEntity.ok(Map.of(
                "record", recordInfos.getFirst(),
                "count", recordInfos.size()
            ));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of(
                "error", "Failed to consume messages: " + e.getMessage()
            ));
        }
    }

    @GetMapping("/consume/range")
    public ResponseEntity<Map<String, Object>> consumeRange(@RequestBody ConsumeRequest request) {

        try {
            List<RecordInfo> recordInfos = kafkaConsumerService.consumeRecords(request);
            return ResponseEntity.ok(Map.of(
                "records", recordInfos,
                "count", recordInfos.size()
            ));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of(
                "error", "Failed to consume messages: " + e.getMessage()
            ));
        }
    }
}
