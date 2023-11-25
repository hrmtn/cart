package com.github.hrmtn.cart.domain;

import lombok.Data;
import org.springframework.data.relational.core.mapping.Table;

@Data
@Table("cart_items")
public class CartItem {
    private Long userId = User.USER_ID;
    private String productId;
    private Long quantity;
}
