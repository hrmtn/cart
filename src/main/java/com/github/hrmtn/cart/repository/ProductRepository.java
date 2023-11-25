package com.github.hrmtn.cart.repository;

import com.github.hrmtn.cart.domain.Product;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Repository
public interface ProductRepository extends ReactiveCrudRepository<Product, UUID> {
    @Query("SELECT * FROM products WHERE quantity > 0")
    Flux<Product> findAvailable();

    @Override
    @CacheEvict(value = Product.AVAILABLE_PRODUCTS_CACHE_NAME, allEntries = true)
    <S extends Product> Mono<S> save(S entity);

    @Override
    @CacheEvict(value = Product.AVAILABLE_PRODUCTS_CACHE_NAME, allEntries = true)
    <S extends Product> Flux<S> saveAll(Iterable<S> entities);
}
