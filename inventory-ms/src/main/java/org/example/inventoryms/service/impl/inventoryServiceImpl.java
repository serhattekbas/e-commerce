package org.example.inventoryms.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.example.inventoryms.event.ProductUpdateEvent;
import org.example.inventoryms.kafka.ProductUpdateProducer; // Producer eklendi
import org.example.inventoryms.model.inventory;
import org.example.inventoryms.repository.inventoryRepository;
import org.example.inventoryms.service.IInventoryService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import jakarta.transaction.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class inventoryServiceImpl implements IInventoryService {

    private final inventoryRepository inventoryRepository;
    private final ProductUpdateProducer productUpdateProducer; // Doğrudan template yerine producer enjekte edildi

    @Override
    public List<inventory> createInventory(List<inventory> inventories) {
        return inventoryRepository.saveAll(inventories);
    }

    @Override
    public List<inventory> listInventory() {
        return inventoryRepository.findAll();
    }

    @Override
    @Transactional // Güncelleme işlemi transaction içine alındı
    public ResponseEntity<inventory> updateInventory(Long productId, inventory inventory) {
        inventory existingInventory = inventoryRepository.findByProductId(productId)
                .orElseThrow(() -> new RuntimeException("Urun bulunamadi."));

        existingInventory.setProductId(inventory.getProductId());
        existingInventory.setProductName(inventory.getProductName());
        existingInventory.setQuantity(inventory.getQuantity());
        existingInventory.setPrice(inventory.getPrice());

        inventory updatedProduct = inventoryRepository.save(existingInventory);

        ProductUpdateEvent productUpdateEvent = ProductUpdateEvent.builder()
                .productId(updatedProduct.getProductId())
                .productName(updatedProduct.getProductName())
                .quantity(updatedProduct.getQuantity())
                .price(updatedProduct.getPrice())
                .build();

        // Kafka tetiklemesi producer sınıfı üzerinden yapılıyor
        productUpdateProducer.sendProductUpdate(productUpdateEvent);

        return ResponseEntity.ok(updatedProduct);
    }

    @Override
    @Transactional
    public void decreaseStock(Long productId, Integer quantity) {
        inventory inventory = inventoryRepository.findByProductId(productId)
                .orElseThrow(() -> new RuntimeException("Ürün bulunamadı: " + productId));
        if (inventory.getQuantity() < quantity) {
            throw new RuntimeException("Stok yetersiz");
        }
        inventory.setQuantity(inventory.getQuantity() - quantity);
        inventoryRepository.save(inventory);
    }

    @Override
    @Transactional
    public void increaseStock(Long productId, Integer quantity) {
        inventory inventory = inventoryRepository.findByProductId(productId)
                .orElseThrow(() -> new RuntimeException("Ürün bulunamadı: " + productId));
        inventory.setQuantity(inventory.getQuantity() + quantity);
        inventoryRepository.save(inventory);
    }
}