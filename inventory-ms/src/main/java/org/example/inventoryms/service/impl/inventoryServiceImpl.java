package org.example.inventoryms.service.impl;

import lombok.AllArgsConstructor;
import org.example.inventoryms.model.inventory;
import org.example.inventoryms.repository.inventoryRepository;
import org.example.inventoryms.service.IInventoryService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
@AllArgsConstructor

public class inventoryServiceImpl implements IInventoryService {

    private final inventoryRepository inventoryRepository;

    public inventory createInventory(inventory inventory) {
        return inventoryRepository.save(inventory);
    }

    public List<inventory> listInventory() {
        return inventoryRepository.findAll();
    }


}
