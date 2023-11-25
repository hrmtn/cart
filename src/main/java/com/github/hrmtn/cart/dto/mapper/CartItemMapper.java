package com.github.hrmtn.cart.dto.mapper;

import com.github.hrmtn.cart.domain.CartItem;
import com.github.hrmtn.cart.dto.AddToCartRequest;
import com.github.hrmtn.cart.dto.CartItemDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface CartItemMapper {
    @Mapping(target = "userId", ignore = true)
    CartItem fromAddToCartRequest(AddToCartRequest addToCartRequest);

    CartItemDto toDTO(CartItem item);
}
