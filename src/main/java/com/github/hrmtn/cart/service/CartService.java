package com.github.hrmtn.cart.service;

import com.github.hrmtn.cart.domain.CartItem;
import com.github.hrmtn.cart.domain.User;
import com.github.hrmtn.cart.repository.CartItemRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Service
@AllArgsConstructor
public class CartService {

    private final CartItemRepository cartItemRepository;
    private final ProductService productService;

    public Mono<Void> addToCart(CartItem cartItem) {


        return productService.findById(UUID.fromString(cartItem.getProductId()))
                .map(product -> {
                    if (product.getQuantity() < cartItem.getQuantity())
                        throw new RuntimeException("Product out of stock");
                    return product;
                })
                .flatMap(product -> cartItemRepository.findCartItemByProductIdAndUserId(cartItem.getProductId(), cartItem.getUserId()))
                .flatMap(ci -> {
                    ci.setQuantity(ci.getQuantity() + cartItem.getQuantity());
                    return cartItemRepository.updateQuantity(User.USER_ID, ci.getProductId(), ci.getQuantity())
                            .then(Mono.fromCallable(() -> cartItem));
                })
                .switchIfEmpty(cartItemRepository.save(cartItem))
                .flatMap(ci -> {
                    // update product quantity
                    return productService.findById(UUID.fromString(ci.getProductId()))
                            .flatMap(product -> {
                                product.setQuantity(product.getQuantity() - ci.getQuantity());
                                return productService.save(product);
                            });
                })
                .then();
    }

    public Flux<CartItem> getCartProducts() {
        return cartItemRepository.findAllByUserId(User.USER_ID);
    }

    public Mono<Void> removeFromCart(String productId) {
        return cartItemRepository.findCartItemByProductIdAndUserId(productId, User.USER_ID)
                .flatMap(cartItem -> {
                    return cartItemRepository.deleteAllByProductIdAndUserId(cartItem.getProductId(), cartItem.getUserId())
                            .then(Mono.fromCallable(() -> cartItem));
                })
                .flatMap(cartItem -> {
                    return productService.findById(UUID.fromString(cartItem.getProductId()))
                            .map(product -> {
                                product.setQuantity(product.getQuantity() + cartItem.getQuantity());
                                return product;
                            });
                })
                .flatMap(productService::save)
                .then();
    }
}
