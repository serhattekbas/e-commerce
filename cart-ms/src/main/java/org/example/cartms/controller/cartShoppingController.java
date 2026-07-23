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

    @PostMapping("/add")
    public ResponseEntity<shoppingCart> addToCart(@RequestBody InventoryDto inventoryDto) {
        shoppingCart savedCart = cartService.addToCart(inventoryDto);
        return ResponseEntity.ok(savedCart);
    }

    @DeleteMapping("/delete/{id}")
    public String deleteProduct(@PathVariable("id") Long productId) {
         cartService.deleteItem(productId);
         return "Deleted product with successfully ";
    }

}
