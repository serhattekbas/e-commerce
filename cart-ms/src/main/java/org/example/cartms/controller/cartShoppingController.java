package org.example.cartms.controller;

import lombok.RequiredArgsConstructor;
import org.example.cartms.dto.InventoryDto;
import org.example.cartms.model.shoppingCart;
import org.example.cartms.service.ICartService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/shoppingcart")
@RequiredArgsConstructor

public class cartShoppingController {
    private final ICartService cartService;

    @GetMapping("/products")
    public ResponseEntity<List<InventoryDto>> listProducts() {
        List<InventoryDto> products = cartService.listInventory();
        return ResponseEntity.ok(products);
    }

    @PostMapping("/add/{userId}")
    public ResponseEntity<shoppingCart> addToCart(@RequestBody InventoryDto inventoryDto,
            @PathVariable("userId") Long userId) {
        shoppingCart savedCart = cartService.addToCart(inventoryDto, userId);
        return ResponseEntity.ok(savedCart);
    }

    @DeleteMapping("/delete/{id}")
    public String deleteProduct(@PathVariable("id") Long productId) {
        cartService.deleteItem(productId);
        return "Deleted product with successfully ";
    }

    @GetMapping("/items/{userId}")
    public ResponseEntity<List<shoppingCart>> getCartItems(@PathVariable("userId") Long userId) {
        List<shoppingCart> cartItems = cartService.getCartItems(userId);
        return ResponseEntity.ok(cartItems);
    }

    @DeleteMapping("/clear/{userId}")
    public ResponseEntity<Void> clearCart(@PathVariable("userId") Long userId) {
        cartService.clearCart(userId);
        return ResponseEntity.ok().build();
    }
}
