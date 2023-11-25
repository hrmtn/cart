package com.github.hrmtn.cart.dto;

import lombok.Data;

@Data
public class AddToCartRequest {
    String productId;
    Long quantity;
}
