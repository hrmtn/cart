package com.github.hrmtn.cart;

import com.github.hrmtn.cart.domain.Product;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.testcontainers.junit.jupiter.Testcontainers;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@Testcontainers
@TestMethodOrder(OrderAnnotation.class)
class ProductTest extends SharedSteps {

	@Test
	@Order(1)
	void searchForProducts() {
		webTestClient.get()
				.uri("/api/v1/products")
				.exchange()
				.expectStatus().isOk()
				.expectBodyList(Product.class)
				.hasSize(3);
	}

}