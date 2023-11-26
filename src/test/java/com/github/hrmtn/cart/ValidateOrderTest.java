package com.github.hrmtn.cart;


import com.github.hrmtn.cart.domain.OrderStatus;
import com.github.hrmtn.cart.domain.Product;
import com.github.hrmtn.cart.dto.AddToCartRequest;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.hateoas.EntityModel;
import org.springframework.kafka.test.context.EmbeddedKafka;
import org.springframework.test.web.reactive.server.FluxExchangeResult;
import org.springframework.web.reactive.function.BodyInserters;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.shaded.com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.LinkedHashMap;
import java.util.UUID;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@Testcontainers
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@EmbeddedKafka(partitions = 1, brokerProperties = { "listeners=PLAINTEXT://localhost:9092", "port=9092" })
public class ValidateOrderTest extends SharedSteps {


    static UUID orderId;

    private static final Long cartItemQuantity = 1L;

    static ObjectMapper objectMapper;

    static {
        objectMapper = new ObjectMapper();
    }

    // here, we will use the D1S product
    final static Product d1s = Product.builder()
            .id("a517dec9-e025-4681-9b7d-584ebd5f990a")
            .name("D1S HID")
            .quantity(44L)
            .price(BigDecimal.valueOf(40))
            .build();

    @Test
    @Order(1)
    void addProductToCart() {
        webTestClient.post()
                .uri("/api/v1/cart/add")
                .body(BodyInserters.fromValue(new AddToCartRequest(d1s.getId(), cartItemQuantity)))
                .exchange()
                .expectStatus().isOk();
    }

    @Test
    @Order(2)
    void createOrder() {
        orderId = webTestClient.post()
                .uri("/api/v1/orders/validate")
                .exchange()
                .expectStatus().isOk()
                .expectBody(UUID.class)
                .returnResult()
                .getResponseBody();
    }

    @Test
    @Order(3)
    void getOrderStatus() throws IOException {
        FluxExchangeResult<String> body = webTestClient.get()
                .uri("/api/v1/orders/" + orderId + "/status")
                .exchange()
                .expectStatus().isOk()
                .returnResult(String.class);

        final EntityModel<LinkedHashMap<String, String>> result = objectMapper.readValue(body.getResponseBody().blockFirst(), EntityModel.class);
        final String orderStatus = result.getContent().get("status");
        assert orderStatus.equals(OrderStatus.CREATED.getStatus());
    }

    @Test
    @Order(4)
    void checkProductQuantityAfterBeingAddedToCart() {
        webTestClient.get()
                .uri("/api/v1/products")
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$[?(@.id == '" + d1s.getId() + "')].quantity")
                .isEqualTo(Math.toIntExact(d1s.getQuantity() - cartItemQuantity));
    }

    @Test
    @Order(5)
    void completeOrder() {
        webTestClient.post()
                .uri("/api/v1/orders/" + orderId + "/complete")
                .exchange()
                .expectStatus().isOk();
    }

    @Test
    @Order(6)
    void checkProductQuantityAfterCompletingTheOrder() {
        webTestClient.get()
                .uri("/api/v1/products")
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$[?(@.id == '" + d1s.getId() + "')].quantity")
                .isEqualTo(Math.toIntExact(d1s.getQuantity() - cartItemQuantity));

    }

    @Test
    @Order(7)
    void getOrderStatusAfterCompletingTheOrder() throws IOException {
        FluxExchangeResult<String> body = webTestClient.get()
                .uri("/api/v1/orders/" + orderId + "/status")
                .exchange()
                .expectStatus().isOk()
                .returnResult(String.class);

        final EntityModel<LinkedHashMap<String, String>> result = objectMapper.readValue(body.getResponseBody().blockFirst(), EntityModel.class);
        final String orderStatus = result.getContent().get("status");
        assert orderStatus.equals(OrderStatus.DELIVERED.getStatus());
    }

}
