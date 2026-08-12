package org.example.inventoryms.event;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder

public class ProductUpdateEvent {

    private Long productId;
    private String productName;
    private Long quantity;
    private Double price;

}
