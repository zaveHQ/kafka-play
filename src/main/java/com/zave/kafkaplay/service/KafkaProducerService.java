package com.zave.kafkaplay.service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;

import com.zave.kafkaplay.model.RecordInfo;
import com.zave.kafkaplay.util.KafkaUtil;

@Service
public class KafkaProducerService {

    private final KafkaTemplate<String, String> kafkaTemplate;

    public KafkaProducerService(KafkaTemplate<String, String> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void produce(List<RecordInfo> recordInfos) throws Exception{

        List<CompletableFuture<SendResult<String, String>>> futures = new ArrayList<>();

        recordInfos.forEach(recordInfo ->{
            CompletableFuture<SendResult<String, String>> future = 
                kafkaTemplate.send(KafkaUtil.getProducerRecord(recordInfo));
            futures.add(future);
        });

        kafkaTemplate.flush();

        CompletableFuture.allOf(futures.toArray(new CompletableFuture[0])).join();
    }
}
