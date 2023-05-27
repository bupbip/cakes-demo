package ru.kustikov.cakes.entity.enums;

import java.math.BigDecimal;

public enum CakeSize {
    XS(BigDecimal.valueOf(1400)),
    S(BigDecimal.valueOf(2400)),
    M(BigDecimal.valueOf(3300)),
    L(BigDecimal.valueOf(5500));

    private final BigDecimal price;

    CakeSize(BigDecimal price) {
        this.price = price;
    }

    public BigDecimal getPrice() {
        return price;
    }
}
