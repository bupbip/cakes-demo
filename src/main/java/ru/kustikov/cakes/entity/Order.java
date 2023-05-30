package ru.kustikov.cakes.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@Table(name = "orders")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne(fetch = FetchType.LAZY)
    private User user;
    @JsonFormat(pattern = "dd-MM-yyyy")
    private LocalDate date;
    @Column(name = "result_price")
    private BigDecimal resultPrice = BigDecimal.ZERO;
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "order", orphanRemoval = true)
    private List<Cake> cakes = new ArrayList<>();

    @PrePersist
    protected void onCreate() {
        this.date = LocalDate.now();
    }

    @PostPersist
    protected void calculateResultPrice() {
        if(cakes.isEmpty()) return;
        for(Cake cake : cakes) {
            resultPrice = resultPrice.add(cake.getCakePrice()).add(cake.getDesignPrice());
        }
    }
}
