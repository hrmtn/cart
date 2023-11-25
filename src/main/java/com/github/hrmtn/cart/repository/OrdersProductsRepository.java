package com.github.hrmtn.cart.repository;

import com.github.hrmtn.cart.domain.OrderProduct;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Flux;

import java.util.UUID;

public interface OrdersProductsRepository extends ReactiveCrudRepository<OrderProduct, UUID> {

    Flux<OrderProduct> findAllByUserId(Long userId);
    Flux<OrderProduct> findAllByOrderId(String orderId);

}
