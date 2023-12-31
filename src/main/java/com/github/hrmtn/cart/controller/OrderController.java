package com.github.hrmtn.cart.controller;

import com.github.hrmtn.cart.dto.OrderDetails;
import com.github.hrmtn.cart.dto.OrderStatusDTO;
import com.github.hrmtn.cart.service.OrderService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
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

    @Operation(summary = "Validate order")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Order validated"),
            @ApiResponse(responseCode = "500", description = "Something went wrong") })
    @PostMapping("/validate")
    Mono<UUID> validate() {
        return orderService.validate();
    }

    @Operation(summary = "Get order status")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "The order status"),
            @ApiResponse(responseCode = "500", description = "Something went wrong") })
    @GetMapping("/{id}/status")
    public Mono<EntityModel<OrderStatusDTO>> getStatus(@PathVariable UUID id) {
        return orderService.getStatus(id).map(OrderStatusDTO::new)
                .flatMap(dto -> addLinksToOrderDetails(dto, id.toString()));
    }

    @PostMapping("/{id}/cancel")
    public Mono<Void> cancel(@PathVariable UUID id) {
        return orderService.cancel(id);
    }

    @PostMapping("/{id}/complete")
    public Mono<Void> complete(@PathVariable UUID id) {
        return orderService.complete(id);
    }

    @GetMapping("/{id}")
    public Mono<EntityModel<OrderDetails>> getOrder(@PathVariable UUID id) {
        return orderService.getOrderDetails(id)
                .flatMap(dto -> addLinksToOrderDetails(dto, id.toString()));
    }

    private <T> Mono<EntityModel<T>> addLinksToOrderDetails(T model, String orderId) {
        List<Mono<Link>> links = List.of(
                linkTo(methodOn(OrderController.class).complete(UUID.fromString(orderId))).withRel("complete").toMono(),
                linkTo(methodOn(OrderController.class).cancel(UUID.fromString(orderId))).withRel("cancel").toMono());
        return Flux.concat(links)
                .collectList()
                .map(link -> EntityModel.of(model, link));
    }

}
