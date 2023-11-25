package com.github.hrmtn.cart.dto.mapper;


import com.github.hrmtn.cart.domain.Product;
import com.github.hrmtn.cart.dto.ProductDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ProductMapper {

    ProductDTO toDTO(Product entity);

}
