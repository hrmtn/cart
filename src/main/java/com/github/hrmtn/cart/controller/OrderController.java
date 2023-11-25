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

    @GetMapping("/{id}/status")
    public Mono<OrderStatusDTO> getStatus(@PathVariable UUID id) {
        return orderService.getStatus(id).map(OrderStatusDTO::new);
    }

    @PostMapping("/{id}/cancel")
    public Mono<Void> cancel(@PathVariable UUID id) {
        return orderService.cancel(id);
    }
}
