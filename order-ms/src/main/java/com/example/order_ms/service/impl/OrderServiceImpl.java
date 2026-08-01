package com.example.order_ms.service.impl;

import com.example.order_ms.client.CartClient;
import com.example.order_ms.client.InventoryClient;
import com.example.order_ms.client.PaymentClient;
import com.example.order_ms.dto.request.CreateOrderRequest;
import com.example.order_ms.dto.response.OrderItemResponse;
import com.example.order_ms.dto.response.OrderResponse;
import com.example.order_ms.enums.OrderStatus;
import com.example.order_ms.model.Order;
import com.example.order_ms.model.OrderItem;
import com.example.order_ms.repository.OrderRepository;
import com.example.order_ms.service.OrderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final CartClient cartClient;
    private final InventoryClient inventoryClient;
    private final PaymentClient paymentClient;

    @Override
    @Transactional
    public OrderResponse createOrder(CreateOrderRequest request) {
        // 1. Sepet verilerini al
        List<CartClient.CartItemResponse> cartItems = cartClient.getCartItems();
        if (cartItems == null || cartItems.isEmpty()) {
            throw new IllegalStateException("Sepetinizde ürün bulunmamaktadır.");
        }

        // 2. Toplam tutarı hesapla
        double totalAmount = cartItems.stream()
                .mapToDouble(item -> item.getTotalPrice() != null ? item.getTotalPrice()
                        : (item.getPrice() * item.getQuantity()))
                .sum();

        // 3. Sipariş taslağını oluştur
        Order order = Order.builder()
                .userId(request.getUserId())
                .shippingAddress(request.getShippingAddress())
                .totalPrice(BigDecimal.valueOf(totalAmount))
                .status(OrderStatus.PENDING)
                .items(new ArrayList<>())
                .build();

        // 4. Stok düşme ve Sipariş kalemlerini hazırlama
        List<CartClient.CartItemResponse> processedItems = new ArrayList<>();
        try {
            for (CartClient.CartItemResponse cartItem : cartItems) {
                inventoryClient.decreaseStock(cartItem.getProductId(), cartItem.getQuantity());
                processedItems.add(cartItem);

                OrderItem orderItem = OrderItem.builder()
                        .productId(cartItem.getProductId())
                        .quantity(cartItem.getQuantity())
                        .price(BigDecimal.valueOf(cartItem.getPrice()))
                        .build();

                order.addItem(orderItem);
            }
        } catch (Exception e) {
            log.error("Stok düşme hatası, işlem geri alınıyor: {}", e.getMessage());
            // Stok iadesi (Saga rollback)
            for (CartClient.CartItemResponse item : processedItems) {
                try {
                    inventoryClient.increaseStock(item.getProductId(), item.getQuantity());
                } catch (Exception ex) {
                    log.error("Rollback sırasında stok artırma hatası: {}", ex.getMessage());
                }
            }
            throw new RuntimeException("Sipariş oluşturulamadı, stok hatası: " + e.getMessage());
        }

        Order savedOrder = orderRepository.save(order);

        // 5. Ödeme işlemini gerçekleştir
        try {
            PaymentClient.PaymentRequest paymentRequest = PaymentClient.PaymentRequest.builder()
                    .orderId(savedOrder.getId())
                    .userId(request.getUserId())
                    .amount(totalAmount)
                    .cardNumber(request.getCardNumber())
                    .cardHolderName(request.getCardHolderName())
                    .expirationDate(request.getExpirationDate())
                    .cvv(request.getCvv())
                    .build();

            PaymentClient.PaymentResponse paymentResponse = paymentClient.processPayment(paymentRequest);

            if (paymentResponse != null && "SUCCESS".equalsIgnoreCase(paymentResponse.getStatus())) {
                savedOrder.setStatus(OrderStatus.APPROVED);
                orderRepository.save(savedOrder);
                // Sepeti temizle
                try {
                    cartClient.clearCart();
                } catch (Exception e) {
                    log.warn("Sipariş tamamlandı fakat sepet temizlenemedi: {}", e.getMessage());
                }
            } else {
                rollbackOrder(savedOrder, processedItems, "Ödeme başarısız.");
            }
        } catch (Exception e) {
            log.error("Ödeme servisi hatası: {}", e.getMessage());
            rollbackOrder(savedOrder, processedItems, "Ödeme servisi hatası: " + e.getMessage());
            throw new RuntimeException("Ödeme işlemi tamamlanamadı: " + e.getMessage());
        }

        return mapToResponse(savedOrder);
    }

    private void rollbackOrder(Order order, List<CartClient.CartItemResponse> items, String reason) {
        log.warn("Sipariş iptal ediliyor (Rollback). Neden: {}", reason);
        order.setStatus(OrderStatus.FAILED);
        orderRepository.save(order);

        for (CartClient.CartItemResponse item : items) {
            try {
                inventoryClient.increaseStock(item.getProductId(), item.getQuantity());
            } catch (Exception ex) {
                log.error("Rollback stok iadesi hatası: {}", ex.getMessage());
            }
        }
    }

    @Override
    public OrderResponse getOrderById(Long orderId) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Sipariş bulunamadı: " + orderId));
        return mapToResponse(order);
    }

    @Override
    public List<OrderResponse> getOrdersByUserId(Long userId) {
        return orderRepository.findByUserId(userId).stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public OrderResponse cancelOrder(Long orderId) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Sipariş bulunamadı: " + orderId));

        if (order.getStatus() == OrderStatus.SHIPPED || order.getStatus() == OrderStatus.DELIVERED) {
            throw new IllegalStateException("Kargoya verilen veya teslim edilen siparişler iptal edilemez.");
        }

        order.setStatus(OrderStatus.CANCELLED);
        orderRepository.save(order);

        // Stokları geri iade et
        for (OrderItem item : order.getItems()) {
            try {
                inventoryClient.increaseStock(item.getProductId(), item.getQuantity());
            } catch (Exception e) {
                log.error("İptal sonrası stok artırma hatası: {}", e.getMessage());
            }
        }

        return mapToResponse(order);
    }

    @Override
    public OrderResponse updateOrderStatus(Long orderId, OrderStatus status) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Sipariş bulunamadı: " + orderId));
        order.setStatus(status);
        Order updatedOrder = orderRepository.save(order);
        return mapToResponse(updatedOrder);
    }

    private OrderResponse mapToResponse(Order order) {
        List<OrderItemResponse> itemResponses = order.getItems().stream()
                .map(item -> OrderItemResponse.builder()
                        .id(item.getId())
                        .productId(item.getProductId())
                        .quantity(item.getQuantity())
                        .price(item.getPrice())
                        .build())
                .collect(Collectors.toList());

        return OrderResponse.builder()
                .id(order.getId())
                .userId(order.getUserId())
                .totalPrice(order.getTotalPrice())
                .status(order.getStatus())
                .shippingAddress(order.getShippingAddress())
                .createdAt(order.getCreatedAt())
                .updatedAt(order.getUpdatedAt())
                .items(itemResponses)
                .build();
    }
}
