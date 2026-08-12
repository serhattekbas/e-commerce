package org.example.cartms.event;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Data
@Slf4j
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProductUpdateEvent {

    private Long productId;
    private String productName;
    private Long quantity;
    private Double price;
}
