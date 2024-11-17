package com.enigma.scream_api.constant;

import lombok.Getter;

@Getter
public enum PaymentStatus {
    SETTLEMENT("settlement"),
    PENDING("pending"),
    DENY("deny"),
    CANCEL("cancel"),
    EXPIRE("expire");

    private final String description;

    PaymentStatus(String description) {
        this.description = description;
    }

    public static PaymentStatus findByDescription(String name) {
        for (PaymentStatus value : values()) {
            if (value.description.equalsIgnoreCase(name)) {
                return value;
            }
        }
        return null;
    }
}
