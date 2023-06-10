package ru.kustikov.cakes.dto;

import lombok.Data;
import lombok.ToString;
import ru.kustikov.cakes.entity.User;

import javax.validation.constraints.NotEmpty;
import java.math.BigDecimal;
import java.util.List;

@Data
public class OrderDTO {
    private Long id;
    private UserDTO customer;
//    private String customerInst;
//    private String customerPhone;
//    private String customerName;
    @NotEmpty
    private String orderDate;
    private List<CakeDTO> cakesData;
    private BigDecimal resultPrice;
}
