package com.github.hrmtn.cart.service;

import com.github.hrmtn.cart.domain.Product;
import com.github.hrmtn.cart.repository.ProductRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.UUID;


@Service
@AllArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;


    public Flux<Product> findAvailable() {
        return productRepository.findAvailable()
                .switchIfEmpty(Mono.error(new RuntimeException("Product not found")));
    }

    public Mono<Product> findById(UUID id) {
        return productRepository.findById(id.toString())
                .switchIfEmpty(Mono.error(new RuntimeException("Product not found")));
    }

    public Mono<Product> save(Product product) {
        return productRepository.save(product);
    }

}
