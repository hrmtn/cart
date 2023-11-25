package com.github.hrmtn.cart.controller;

import com.github.hrmtn.cart.service.OrderService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/orders/")
@AllArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @PostMapping("/validate")
    Mono<UUID> validate() {
        return orderService.validate();
    }

}
