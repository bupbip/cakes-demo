package ru.kustikov.cakes.dto;

import lombok.Data;
import ru.kustikov.cakes.entity.enums.CakeSize;

import java.math.BigDecimal;

@Data
public class CakeDTO {
    private Long id;
    private Long orderId;
    private String cakeSize;
    private int designRating;
    private BigDecimal cakePrice;
    private BigDecimal designPrice;
}
