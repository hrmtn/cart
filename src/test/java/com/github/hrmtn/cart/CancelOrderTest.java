package com.github.hrmtn.cart;

import com.github.hrmtn.cart.dto.AddToCartRequest;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.kafka.test.context.EmbeddedKafka;
import org.springframework.web.reactive.function.BodyInserters;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.util.UUID;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@Testcontainers
@TestMethodOrder(OrderAnnotation.class)
@EmbeddedKafka(partitions = 1, brokerProperties = { "listeners=PLAINTEXT://localhost:9092", "port=9092" })
class CancelOrderTest extends SharedSteps {

	static UUID orderId;

	private Long cartItemQuantity = 1L;

	@Test
	@Order(1)
	void addProductToCart() {
		webTestClient.post()
				.uri("/api/v1/cart/add")
				.body(BodyInserters.fromValue(new AddToCartRequest(h8led.getId(), cartItemQuantity)))
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
	void checkProductQuantityAfterBeingAddedToCart() {
		webTestClient.get()
				.uri("/api/v1/products")
				.exchange()
				.expectStatus().isOk()
				.expectBody()
				.jsonPath("$[?(@.id == '" + h8led.getId() + "')].quantity")
				// The query above returns an Integer, and we need to compare with Long.
				.isEqualTo(Math.toIntExact(h8led.getQuantity() - cartItemQuantity));
	}

	@Test
	@Order(4)
	void cancelOrder() {
		webTestClient.post()
				.uri("/api/v1/orders/" + orderId + "/cancel")
				.exchange()
				.expectStatus().isOk();
	}

	@Test
	@Order(5)
	void checkProductQuantityAfterCancerlingTheOrder() {
		webTestClient.get()
				.uri("/api/v1/products")
				.exchange()
				.expectStatus().isOk()
				.expectBody()
				.jsonPath("$[?(@.id == '" + h8led.getId() + "')].quantity")
				// The query above returns an Integer, and we need to compare with Long.
				.isEqualTo(Math.toIntExact(h8led.getQuantity()));
	}

}