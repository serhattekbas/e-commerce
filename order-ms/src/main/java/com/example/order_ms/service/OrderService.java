package com.example.order_ms.service;

import com.example.order_ms.dto.request.CreateOrderRequest;
import com.example.order_ms.dto.response.OrderResponse;
import com.example.order_ms.enums.OrderStatus;

import java.util.List;

public interface OrderService {
    OrderResponse createOrder(CreateOrderRequest request);
    OrderResponse getOrderById(Long orderId);
    List<OrderResponse> getOrdersByUserId(Long userId);
    OrderResponse cancelOrder(Long orderId);
    OrderResponse updateOrderStatus(Long orderId, OrderStatus status);
}
