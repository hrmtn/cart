package com.github.hrmtn.cart.controller;

import com.github.hrmtn.cart.dto.OrderStatusDTO;
import com.github.hrmtn.cart.service.OrderService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
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

    @GetMapping("/{orderId}/status")
    public Mono<OrderStatusDTO> getStatus(@PathVariable UUID orderId) {
        return orderService.getStatus(orderId).map(OrderStatusDTO::new);
    }

}
