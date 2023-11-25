package com.github.hrmtn.cart.dto;

import com.github.hrmtn.cart.domain.OrderProduct;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Collection;

@Data
public class OrderDetails {
    private String orderId;
    private BigDecimal price;
    private Long userId;
    private String status;
    private String createdAt;
    private Collection<ProductDetail> products;

    @Data
    public static class ProductDetail {
        private String productId;
        private Long quantity;
        private BigDecimal price;

        public static ProductDetail fromOrderProduct(OrderProduct orderProduct) {
            ProductDetail productDetail = new ProductDetail();
            productDetail.setProductId(orderProduct.getProductId());
            productDetail.setQuantity(orderProduct.getQuantity());
            productDetail.setPrice(orderProduct.getPrice());
            return productDetail;
        }
    }

}
