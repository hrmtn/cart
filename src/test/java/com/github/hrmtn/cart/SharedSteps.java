package com.github.hrmtn.cart;

import com.github.hrmtn.cart.domain.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.testcontainers.containers.PostgreSQLContainer;

import java.math.BigDecimal;
import java.util.UUID;

public abstract class SharedSteps {

    @Autowired
    protected WebTestClient webTestClient;

    protected static final PostgreSQLContainer<?> postgreSQLContainer = new PostgreSQLContainer<>("postgres:16.1-bullseye")
            .withDatabaseName("integration-tests-db").withUsername("username").withPassword("password");


    // This product is created in the database by the Flyway migration
    static Product h8led = Product.builder()
            .id("becd5e12-defd-4238-8739-5ac69fb06b0f")
            .name("H8 LED")
            .quantity(11L)
            .price(BigDecimal.valueOf(30))
            .build();

    static {
        postgreSQLContainer.start();
    }

    @DynamicPropertySource
    static void setProperties(DynamicPropertyRegistry dynamicPropertyRegistry) {
        dynamicPropertyRegistry.add("spring.r2dbc.url", () -> postgreSQLContainer.getJdbcUrl().replace("jdbc", "r2dbc"));
        dynamicPropertyRegistry.add("spring.r2dbc.username", postgreSQLContainer::getUsername);
        dynamicPropertyRegistry.add("spring.r2dbc.password", postgreSQLContainer::getPassword);
        dynamicPropertyRegistry.add("spring.flyway.url", postgreSQLContainer::getJdbcUrl);
        dynamicPropertyRegistry.add("spring.flyway.user", postgreSQLContainer::getUsername);
        dynamicPropertyRegistry.add("spring.flyway.password", postgreSQLContainer::getPassword);
    }

}
