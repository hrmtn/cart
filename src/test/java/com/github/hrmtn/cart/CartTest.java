package com.github.hrmtn.cart;


import com.github.hrmtn.cart.dto.AddToCartRequest;
import com.github.hrmtn.cart.dto.CartItemDto;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.web.reactive.function.BodyInserters;
import org.testcontainers.junit.jupiter.Testcontainers;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@Testcontainers
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class CartTest extends SharedSteps {

    @Test
    @Order(1)
    void addProductToCart() {
        webTestClient.post()
                .uri("/api/v1/cart/add")
                .body(BodyInserters.fromValue(new AddToCartRequest(h8led.getId(), 1L)))
                .exchange()
                .expectStatus().isOk();
    }

    @Test
    @Order(2)
    void listCart() {
        webTestClient.get()
                .uri("/api/v1/cart")
                .exchange()
                .expectStatus().isOk()
                .expectBodyList(CartItemDto.class)
                .hasSize(1);
    }

    @Test
    @Order(3)
    void removeProductFromCart() {
        webTestClient.delete()
                .uri("/api/v1/cart/" + h8led.getId())
                .exchange()
                .expectStatus().isOk();
    }

    @Test
    @Order(4)
    void listCartAfterRemove() {
        webTestClient.get()
                .uri("/api/v1/cart")
                .exchange()
                .expectStatus().isOk()
                .expectBodyList(CartItemDto.class)
                .hasSize(0);
    }

}
