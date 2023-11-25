package com.github.hrmtn.cart.dto.mapper;


import com.github.hrmtn.cart.domain.Product;
import com.github.hrmtn.cart.dto.ProductDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ProductMapper {

    ProductDto toDTO(Product entity);

}
