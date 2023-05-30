package ru.kustikov.cakes.entity;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import ru.kustikov.cakes.entity.enums.CakeSize;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import java.math.BigDecimal;

@Data
@Entity
@Table(name = "cakes")
public class Cake {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne(fetch = FetchType.LAZY)
    private Order order;
    @Enumerated
    @Column(name = "cake_size")
    private CakeSize cakeSize;
    @Column(name = "design_rating")
    @Max(value = 10, message = "Значение не может быть больше 10")
    @Min(value = 1, message = "Значение не может быть меньше 1")
    private int designRating;
    @Lob
    private byte[] photo;
    @Column(name = "cake_price")
    private BigDecimal cakePrice;
    @Column(name = "design_price")
    private BigDecimal designPrice = BigDecimal.ZERO;

    @PostLoad
    public void calculateCakePrice() {
        if (cakeSize != null) {
            cakePrice = cakeSize.getPrice();
        }
    }
}
