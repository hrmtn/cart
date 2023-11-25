package com.github.hrmtn.cart.domain;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.domain.Persistable;
import org.springframework.data.relational.core.mapping.Table;

import java.time.Instant;
import java.util.UUID;

@Data
@Table("orders")
public class Order implements Persistable<UUID> {

    @Id
    private UUID id;
    private String orderStatus = OrderStatus.CREATED.getStatus();
    private Instant createdAt;

    public Order(UUID id) {
        this.id = id;
    }

    @Override
    public boolean isNew() {
        return true;
    }
}
