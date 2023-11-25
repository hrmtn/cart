package com.github.hrmtn.cart.repository;

import com.github.hrmtn.cart.domain.Order;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

import java.util.UUID;

@Repository
public interface OrderRepository extends ReactiveCrudRepository<Order, UUID> {

    Flux<Order> findAllBy(Long userId);
}
