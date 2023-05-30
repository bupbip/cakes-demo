package ru.kustikov.cakes.facade;

import org.springframework.stereotype.Component;
import ru.kustikov.cakes.dto.CakeDTO;
import ru.kustikov.cakes.entity.Cake;

@Component
public class CakeFacade {
    private CakeDTO cakeToCakeDTO(Cake cake) {
        CakeDTO cakeDTO = new CakeDTO();
        cakeDTO.setId(cake.getId());
        cakeDTO.setOrderId(cake.getOrder().getId());
        cakeDTO.setCakeSize(cake.getCakeSize());
        cakeDTO.setCakePrice(cake.getCakePrice());
        cakeDTO.setDesignPrice(cake.getDesignPrice());
        cakeDTO.setDesignRating(cake.getDesignRating());
        return cakeDTO;
    }
}
