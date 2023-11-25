package com.github.hrmtn.cart.domain;

public enum OrderStatus {
    CREATED("CREATED"),
    PROCESSING("PROCESSING"),
    CANCELED("CANCELED"),
    DELIVERED("DELIVERED");

    private String status;

    OrderStatus(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }
}
