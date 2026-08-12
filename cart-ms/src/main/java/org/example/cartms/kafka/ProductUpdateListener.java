package org.example.cartms.kafka;

import org.example.cartms.event.ProductUpdateEvent;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class ProductUpdateListener {

    @KafkaListener(topics = "product-update-topic", groupId = "cart-ms")
    public void handleProductUpdateEvent(ProductUpdateEvent event) {
        log.info("Kafka'dan urun guncelleme olayi alindi: ProductId = {}", event.getProductId());
    }
}
