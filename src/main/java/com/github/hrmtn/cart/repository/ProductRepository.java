package com.github.hrmtn.cart.repository;

import com.github.hrmtn.cart.domain.Product;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

import java.util.UUID;

@Repository
public interface ProductRepository extends ReactiveCrudRepository<Product, UUID> {
    @Query("SELECT * FROM products WHERE quantity > 0")
    Flux<Product> findAvailable();
}
