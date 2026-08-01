package com.example.order_ms.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CreateOrderRequest {
    private Long userId;
    private String shippingAddress;
    
    // Ödeme simülasyonu için basit kart bilgileri
    private String cardNumber;
    private String cardHolderName;
    private String expirationDate;
    private String cvv;
}
