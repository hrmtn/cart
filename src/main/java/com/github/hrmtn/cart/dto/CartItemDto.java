package com.github.hrmtn.cart.dto;

import lombok.Data;

@Data
public class CartItemDto {
    private String productId;
    private int quantity;
}
