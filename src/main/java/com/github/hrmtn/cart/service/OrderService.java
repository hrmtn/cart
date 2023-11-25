package com.github.hrmtn.cart.service;

import com.github.hrmtn.cart.domain.*;
import com.github.hrmtn.cart.repository.CartItemRepository;
import com.github.hrmtn.cart.repository.OrderRepository;
import com.github.hrmtn.cart.repository.OrdersProductsRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.time.Instant;
import java.util.UUID;

@Service
@AllArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final OrdersProductsRepository ordersProductsRepository;
    private final ProductService productService;
    private final CartItemRepository cartItemRepository;

    public Mono<String> getStatus(UUID id) {
        return orderRepository.findById(id).map(Order::getOrderStatus);
    }

    public Mono<Void> cancel(UUID id) {
        return orderRepository.findById(id)
                .filter(order -> order.getCreatedAt().isAfter(Instant.now().minusSeconds(3600)))
                .map(order -> {
                    order.setOrderStatus(OrderStatus.CANCELED.getStatus());
                    return order;
                })
                .flatMap(order -> orderRepository.updateStatus(order.getId(), order.getOrderStatus()).thenReturn(order))
                .flatMapMany(order -> ordersProductsRepository.findAllByOrderId(order.getId()))
                .flatMap(orderProduct -> productService.findById(orderProduct.getProductId())
                        .map(product -> {
                            product.setQuantity(product.getQuantity() + orderProduct.getQuantity());
                            return product;
                        })
                        .flatMap(productService::save)
                )
                .then();
    }

    public Mono<Void> complete(UUID id) {
        return orderRepository.updateStatus(id, OrderStatus.DELIVERED.getStatus());
    }

    private record ProductAndCartItem(Product product, CartItem cartItem ) {}

    public Mono<UUID> validate() {

        var order = new Order(UUID.randomUUID());
        order.setCreatedAt(Instant.now());

        return orderRepository.save(order)
                .flatMapMany(savedOrder -> this.cartItemRepository.findAllByUserId(User.USER_ID))
                .flatMap(cartItem -> productService.findById(UUID.fromString(cartItem.getProductId()))
                        .map(product -> new OrderService.ProductAndCartItem(product, cartItem))
                )
                .flatMap(productAndCartItem -> {
                    OrderProduct orderProduct = new OrderProduct();
                    orderProduct.setUserId(User.USER_ID);
                    orderProduct.setOrderId(order.getId());
                    orderProduct.setProductId(UUID.fromString(productAndCartItem.cartItem().getProductId()));
                    orderProduct.setQuantity(productAndCartItem.cartItem().getQuantity());
                    orderProduct.setPrice(productAndCartItem.product().getPrice());
                    return ordersProductsRepository.save(orderProduct);
                })
                .flatMap(orderProduct -> cartItemRepository.deleteAllByProductIdAndUserId(String.valueOf(orderProduct.getProductId()), User.USER_ID)
                        .thenReturn(orderProduct.getOrderId()))
                .last();

    }

}
