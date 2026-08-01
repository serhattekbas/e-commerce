package org.example.inventoryms.service.impl;

import lombok.AllArgsConstructor;
import org.example.inventoryms.model.inventory;
import org.example.inventoryms.repository.inventoryRepository;
import org.example.inventoryms.service.IInventoryService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;

import java.util.List;

@Service
@AllArgsConstructor

public class inventoryServiceImpl implements IInventoryService {

    private final inventoryRepository inventoryRepository;

    @Override
    public List<inventory> createInventory(List<inventory> inventories) {
        return inventoryRepository.saveAll(inventories);
    }

    @Override
    public List<inventory> listInventory() {
        return inventoryRepository.findAll();
    }

    @Override
    @Transactional
    public void decreaseStock(Long productId, Integer quantity) {

        inventory inventory = inventoryRepository.findByProductId(productId).orElseThrow(() -> new RuntimeException("Ürün bulunamadı: " + productId));
        if (inventory.getQuantity() < quantity) {
            throw new RuntimeException("Stok yetersiz");
        }
        inventory.setQuantity(inventory.getQuantity() - quantity);
        inventoryRepository.save(inventory);

    }

    @Override
    @Transactional
    public void increaseStock(Long productId, Integer quantity) {
        inventory inventory = inventoryRepository.findByProductId(productId).orElseThrow(() -> new RuntimeException("Ürün bulunamadı: " + productId));
        inventory.setQuantity(inventory.getQuantity() + quantity);
        inventoryRepository.save(inventory);
    }

}

