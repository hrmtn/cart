package com.github.hrmtn.cart.domain;

import lombok.Data;
import org.springframework.data.relational.core.mapping.Table;

import java.math.BigDecimal;
import java.util.UUID;

@Data
@Table("orders_products")
public class OrderProduct {
    private Long userId;
    private UUID orderId;
    private UUID productId;
    private Long quantity;
    private BigDecimal price;
}
