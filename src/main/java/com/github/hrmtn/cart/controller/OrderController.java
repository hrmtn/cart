package com.github.hrmtn.cart.controller;

import com.github.hrmtn.cart.dto.OrderStatusDTO;
import com.github.hrmtn.cart.service.OrderService;
import lombok.AllArgsConstructor;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.UUID;

import static org.springframework.hateoas.server.reactive.WebFluxLinkBuilder.linkTo;
import static org.springframework.hateoas.server.reactive.WebFluxLinkBuilder.methodOn;

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
    public Mono<EntityModel<OrderStatusDTO>> getStatus(@PathVariable UUID id) {
        return orderService.getStatus(id).map(OrderStatusDTO::new)
                .flatMap(dto -> {
                    List<Mono<Link>> links = List.of(
                            linkTo(methodOn(OrderController.class).complete(id)).withRel("complete").toMono(),
                            linkTo(methodOn(OrderController.class).cancel(id)).withRel("cancel").toMono());
                    return Flux.concat(links)
                            .collectList()
                            .map(link -> EntityModel.of(dto, link));
                });
    }

    @PostMapping("/{id}/cancel")
    public Mono<Void> cancel(@PathVariable UUID id) {
        return orderService.cancel(id);
    }

    @PostMapping("/{id}/complete")
    public Mono<Void> complete(@PathVariable UUID id) {
        return orderService.complete(id);
    }

}
