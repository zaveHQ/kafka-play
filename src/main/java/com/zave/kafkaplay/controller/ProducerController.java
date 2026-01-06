package com.zave.kafkaplay.controller;

import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.zave.kafkaplay.model.RecordInfo;
import com.zave.kafkaplay.service.KafkaProducerService;

@RestController
@RequestMapping("/api/topic/produce")
public class ProducerController {

    private final KafkaProducerService kafkaProducerService;

    public ProducerController(KafkaProducerService kafkaProducerService) {
        this.kafkaProducerService = kafkaProducerService;
    }

    @PostMapping("/single")
    public ResponseEntity<?> produceSingle(@RequestBody RecordInfo recordInfo) {

        try {
            kafkaProducerService.produce(Collections.singletonList(recordInfo));

            return ResponseEntity.ok(Map.of(
                "message", "Record sent successfully"
            ));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of(
                "error", "Failed to send record: " + e.getMessage()
            ));
        }
    }

    @PostMapping("/multiple")
    public ResponseEntity<?> produceMultiple(@RequestBody List<RecordInfo> recordInfos) {
        try {
            kafkaProducerService.produce(recordInfos);

            return ResponseEntity.ok(Map.of(
                "message", "Records sent successfully"
            ));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of(
                "error", "Failed to send records: " + e.getMessage()
            ));
        }
    }
}
