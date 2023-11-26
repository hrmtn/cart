package com.github.hrmtn.cart.domain;

import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.domain.Persistable;
import org.springframework.data.relational.core.mapping.Table;
import org.springframework.util.StringUtils;

import java.math.BigDecimal;

@Data
@Builder
@Table("products")
public class Product implements Persistable<String> {

    public static final String AVAILABLE_PRODUCTS_CACHE_NAME = "available_products";

    @Id
    private String id;
    private String name;
    private BigDecimal price;
    private Long quantity;

    @Override
    public String getId() {
        return this.id;
    }

    @Override
    @Transient
    public boolean isNew() {
        return !StringUtils.hasText(id);
    }

}
