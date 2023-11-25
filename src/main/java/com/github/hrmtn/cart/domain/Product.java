package com.github.hrmtn.cart.domain;

import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.domain.Persistable;
import org.springframework.data.relational.core.mapping.Table;
import org.springframework.util.StringUtils;

import java.math.BigDecimal;
import java.util.UUID;

@Data
@Builder
@Table("products")
public class Product implements Persistable<UUID> {

    public static final String AVAILABLE_PRODUCTS_CACHE_NAME = "available_products";

    @Id
    private UUID id;
    private String name;
    private BigDecimal price;
    private Long quantity;

    @Override
    @Transient
    public boolean isNew() {
        return id == null || !StringUtils.hasText(id.toString());
    }

}
