package com.github.hrmtn.cart.controller;

import com.github.hrmtn.cart.domain.Product;
import com.github.hrmtn.cart.dto.ProductDto;
import com.github.hrmtn.cart.dto.mapper.ProductMapper;
import com.github.hrmtn.cart.service.ProductService;
import lombok.AllArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

@RestController
@RequestMapping("/api/v1/products")
@AllArgsConstructor
public class ProductController {

    private final ProductService productService;
    private final ProductMapper productMapper;

    @GetMapping
    @Cacheable(Product.AVAILABLE_PRODUCTS_CACHE_NAME)
    public Flux<ProductDto> availableProducts() {
        return productService.findAvailable().map(productMapper::toDTO);
    }

}
