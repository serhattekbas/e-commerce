package org.example.inventoryms.controller;

import org.example.inventoryms.model.inventory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

public interface IInevntoryController {
    ResponseEntity<inventory> createInventory(@RequestBody inventory inventory);

}
