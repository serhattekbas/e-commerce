package org.example.cartms.service.impl;


import lombok.AllArgsConstructor;
import org.example.cartms.dto.InventoryDto;
import org.example.cartms.feign.IInventoryClient;
import org.example.cartms.model.shoppingCart;
import org.example.cartms.repository.cartRepository;
import org.example.cartms.service.ICartService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor

public class cartServiceImpl implements ICartService {
    private final cartRepository cartRepository;
    private final IInventoryClient inventoryClient;


    @Override
    public List<InventoryDto> listInventory() {
        return inventoryClient.listInventory();
    }

    @Override
    public shoppingCart addToCart(InventoryDto inventoryDto) {
        // Find the product in the inventory list
        InventoryDto product = inventoryClient.listInventory().stream()
                .filter(p -> p.getProductId().equals(inventoryDto.getProductId()))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Product not found"));

        // Create a new shoppingCart item and save
        shoppingCart cartItem = new shoppingCart();
        cartItem.setProductId(product.getProductId());
        cartItem.setProductName(product.getProductName());
        cartItem.setPrice(product.getPrice());
        cartItem.setQuantity(inventoryDto.getQuantity().intValue());
        cartItem.setTotalPrice(product.getPrice() * inventoryDto.getQuantity());

        return cartRepository.save(cartItem);
    }

    @Override
    public void deleteItem(Long id) {
        cartRepository.deleteById(id);
    }


}


