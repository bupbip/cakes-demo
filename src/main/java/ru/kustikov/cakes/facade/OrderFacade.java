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

        User customer = order.getCustomer();
        if (customer != null) {
            UserDTO customerDTO = new UserDTO();
            customerDTO.setId(customer.getId());
            customerDTO.setInstagram(customer.getInstagram());
            customerDTO.setName(customer.getName());
            customerDTO.setPhone(customer.getPhone());

            orderDTO.setCustomer(customerDTO);
        }

        orderDTO.setOrderDate(String.valueOf(order.getDate()));
        orderDTO.setResultPrice(order.getResultPrice());
        return orderDTO;
    }
}
