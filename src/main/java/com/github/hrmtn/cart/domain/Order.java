package com.github.hrmtn.cart.domain;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.domain.Persistable;
import org.springframework.data.relational.core.mapping.Table;

import java.time.Instant;

@Data
@Table("orders")
public class Order implements Persistable<String> {

    @Id
    private String id;
    private Long userId;
    private String orderStatus = OrderStatus.CREATED.getStatus();
    private Instant createdAt;

    public Order(String id) {
        this.id = id;
    }

    @Override
    public String getId() {
        return this.id;
    }

    @Override
    public boolean isNew() {
        return true;
    }
}
