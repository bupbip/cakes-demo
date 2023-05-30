package ru.kustikov.cakes.dto;

import lombok.Data;

import javax.validation.constraints.NotEmpty;
import java.math.BigDecimal;

@Data
public class OrderDTO {
    private Long id;
    @NotEmpty
    private String userInst;
    @NotEmpty
    private String date;
    @NotEmpty
    private BigDecimal resultPrice;
}
