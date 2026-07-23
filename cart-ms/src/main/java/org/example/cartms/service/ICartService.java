package org.example.cartms.service;

import org.example.cartms.dto.InventoryDto;
import org.example.cartms.model.shoppingCart;

import java.util.List;

public interface ICartService {
    List<InventoryDto> listInventory();
    shoppingCart addToCart(InventoryDto inventoryDto);
    void deleteItem(Long productId);
}
