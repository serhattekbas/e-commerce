package org.example.cartms.service;

import org.example.cartms.dto.InventoryDto;
import org.example.cartms.model.shoppingCart;

import java.util.List;

public interface ICartService {
    List<InventoryDto> listInventory();

    shoppingCart addToCart(InventoryDto inventoryDto, Long userId);

    void deleteItem(Long productId);

    List<shoppingCart> getCartItems(Long userId);

    void clearCart(Long userId);
}
