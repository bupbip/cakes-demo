package ru.kustikov.cakes.facade;

import org.springframework.stereotype.Component;
import ru.kustikov.cakes.dto.OrderDTO;
import ru.kustikov.cakes.dto.UserDTO;
import ru.kustikov.cakes.entity.Order;
import ru.kustikov.cakes.entity.User;

@Component
public class OrderFacade {
    public OrderDTO orderToOrderDTO(Order order){
        OrderDTO orderDTO = new OrderDTO();
        orderDTO.setId(order.getId());
        orderDTO.setUserInst(order.getUser().getInstagram());
        orderDTO.setDate(String.valueOf(order.getDate()));
        orderDTO.setResultPrice(order.getResultPrice());
        return orderDTO;
    }
}
