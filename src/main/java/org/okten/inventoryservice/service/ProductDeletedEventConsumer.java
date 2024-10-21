package org.okten.inventoryservice.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.okten.springdemo.dto.ProductDeletedEvent;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.listener.MessageListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class ProductDeletedEventConsumer implements MessageListener<Long, ProductDeletedEvent> {

    private final InventoryService inventoryService;

    @KafkaListener(topics = "${spring.kafka.consumer.topic}", groupId = "inventory-service-listener")
    @Override
    public void onMessage(ConsumerRecord<Long, ProductDeletedEvent> data) {
        ProductDeletedEvent event = data.value();
        log.info("Received product deleted event: {}", event);
        inventoryService.deleteInventoryOfProduct(event.productId());
    }
}
