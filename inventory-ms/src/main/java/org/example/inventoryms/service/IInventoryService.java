package org.example.inventoryms.service;


import org.example.inventoryms.model.inventory;

import java.util.List;

public interface IInventoryService {
    List<inventory> createInventory(List<inventory> inventories);
    List<inventory> listInventory();
}
