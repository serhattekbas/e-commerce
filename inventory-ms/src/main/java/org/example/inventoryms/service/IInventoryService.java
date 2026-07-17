package org.example.inventoryms.service;


import org.example.inventoryms.model.inventory;

import java.util.List;

public interface IInventoryService {
    inventory createInventory(inventory inventory);
    List<inventory> listInventory();

}
