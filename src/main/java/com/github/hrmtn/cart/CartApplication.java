package com.github.hrmtn.cart;

import com.github.hrmtn.cart.domain.Product;
import com.github.hrmtn.cart.repository.ProductRepository;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import reactor.core.publisher.Flux;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@SpringBootApplication
public class CartApplication {

	public static void main(String[] args) {
		SpringApplication.run(CartApplication.class, args);
	}

	@Bean
	public ApplicationRunner runner(ProductRepository repository) {
		return args -> {

			Flux.fromIterable(
					List.of(
							Product.builder().id(UUID.randomUUID()).name("D1S").price(BigDecimal.valueOf(44)).quantity(13L).build(),
							Product.builder().id(UUID.randomUUID()).name("H3").price(BigDecimal.valueOf(17.0)).quantity(19L).build(),
							Product.builder().id(UUID.randomUUID()).name("H3").price(BigDecimal.valueOf(22.0)).quantity(8L).build())

			)
					.flatMap(repository::save)
					.thenMany(repository.findAll())
					.subscribe(System.out::println);

		};
	}

}
