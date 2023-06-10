package ru.kustikov.cakes.facade;

import org.springframework.stereotype.Component;
import ru.kustikov.cakes.dto.CakeDTO;
import ru.kustikov.cakes.entity.Cake;
import ru.kustikov.cakes.entity.enums.CakeSize;

@Component
public class CakeFacade {
    public CakeDTO cakeToCakeDTO(Cake cake) {
        CakeDTO cakeDTO = new CakeDTO();
        cakeDTO.setId(cake.getId());
        cakeDTO.setOrderId(cake.getOrder().getId());
        cakeDTO.setCakeSize(String.valueOf(cake.getCakeSize()));
        cakeDTO.setCakePrice(cake.getCakePrice());
        cakeDTO.setDesignPrice(cake.getDesignPrice());
        cakeDTO.setDesignRating(cake.getDesignRating());
        return cakeDTO;
    }

    public static Cake cakeDTOToCake(CakeDTO cakeDTO) {
        Cake cake = new Cake();
        cake.setCakeSize(CakeSize.valueOf(cakeDTO.getCakeSize()));
        cake.setDesignRating(cakeDTO.getDesignRating());
        cake.setCakePrice(cakeDTO.getCakePrice());
        cake.setDesignPrice(cakeDTO.getDesignPrice());
        return cake;
    }

}
