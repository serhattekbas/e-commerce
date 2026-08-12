package org.example.inventoryms.kafka;

import org.example.inventoryms.event.ProductUpdateEvent;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.CompletableFuture;

@Service
@RequiredArgsConstructor // @Data yerine @RequiredArgsConstructor kullanılması önerilir
@Slf4j
public class ProductUpdateProducer {

    private final KafkaTemplate<String, ProductUpdateEvent> kafkaTemplate;

    public void sendProductUpdate(ProductUpdateEvent event) {
        log.info("{} numarali {} urunu guncellendi, Kafka'ya gonderiliyor...", event.getProductId(),
                event.getProductName());

        CompletableFuture<SendResult<String, ProductUpdateEvent>> future = kafkaTemplate.send("product-update-topic",
                event);

        future.whenComplete((result, ex) -> {
            if (ex == null) {
                log.info("Mesaj basariyla Kafka'ya gonderildi. Offset: {}", result.getRecordMetadata().offset());
            } else {
                log.error("Mesaj Kafka'ya gonderilemedi! Hata: {}", ex.getMessage());
            }
        });
    }
}