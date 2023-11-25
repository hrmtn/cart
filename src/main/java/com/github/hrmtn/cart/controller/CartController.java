package com.github.hrmtn.cart.controller;

import com.github.hrmtn.cart.domain.CartItem;
import com.github.hrmtn.cart.dto.AddToCartRequest;
import com.github.hrmtn.cart.dto.CartItemDto;
import com.github.hrmtn.cart.dto.mapper.CartItemMapper;
import com.github.hrmtn.cart.service.CartService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/v1/cart")
@AllArgsConstructor
public class CartController {

    private final CartService service;
    private final CartItemMapper mapper;

    @PostMapping("/add")
    public Mono<Void> addToCart(@RequestBody AddToCartRequest addToCartRequest) {
        CartItem cartItem = mapper.fromAddToCartRequest(addToCartRequest);
        return service.addToCart(cartItem);
    }

    @GetMapping()
    public Flux<CartItemDto> listCartProducts() {
        return service.getCartProducts().map(mapper::toDTO);
    }

    @DeleteMapping("/{id}")
    public Mono<Void> removeFromCart(@PathVariable String id) {
        return service.removeFromCart(id);
    }


}

