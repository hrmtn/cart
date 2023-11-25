package com.github.hrmtn.cart.repository;

import com.github.hrmtn.cart.domain.Order;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Repository
public interface OrderRepository extends ReactiveCrudRepository<Order, UUID> {

    Flux<Order> findAllBy(Long userId);

    @Query("UPDATE orders SET order_status = :orderStatus WHERE id = :id")
    Mono<Void> updateStatus(UUID id, String orderStatus);
}
