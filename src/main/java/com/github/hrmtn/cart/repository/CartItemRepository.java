package com.github.hrmtn.cart.repository;

import com.github.hrmtn.cart.domain.CartItem;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface CartItemRepository extends ReactiveCrudRepository<CartItem, Long> {
    Mono<CartItem> findCartItemByProductIdAndUserId(String productId, Long userId);

    Flux<CartItem> findAllByUserId(Long userId);

    @Query("UPDATE cart_items SET quantity = :quantity WHERE user_id = :userId AND product_id = :productId")
    Mono<Void> updateQuantity(Long userId, String productId, Long quantity);

    Flux<Object> deleteAllByProductIdAndUserId(String productId, Long userId);
}
