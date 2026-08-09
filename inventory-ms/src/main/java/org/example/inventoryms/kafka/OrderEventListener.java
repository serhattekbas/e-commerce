package org.example.inventoryms.kafka;

import org.example.inventoryms.event.OrderCreatedEvent;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class OrderEventListener {

    @KafkaListener(topics = "order-created-topic", groupId = "inventory-ms")
    public void handleOrderCreatedEvent(OrderCreatedEvent event) {

        log.info("Kafka'dan yeni siparis olayi alindi: OrderId = {}", event.getOrderId());
    }

}
