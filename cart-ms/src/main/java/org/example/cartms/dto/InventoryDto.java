package org.example.cartms.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class InventoryDto {
    private Long productId;
    private String productName;
    private Long quantity;
    private Double price;
}
