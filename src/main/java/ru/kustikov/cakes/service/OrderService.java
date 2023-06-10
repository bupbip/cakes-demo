package ru.kustikov.cakes.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ru.kustikov.cakes.dto.OrderDTO;
import ru.kustikov.cakes.dto.UserDTO;
import ru.kustikov.cakes.entity.Cake;
import ru.kustikov.cakes.entity.Order;
import ru.kustikov.cakes.entity.User;
import ru.kustikov.cakes.facade.CakeFacade;
import ru.kustikov.cakes.facade.OrderFacade;
import ru.kustikov.cakes.repository.CakeRepository;
import ru.kustikov.cakes.repository.OrderRepository;
import ru.kustikov.cakes.repository.UserRepository;

import java.security.Principal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrderService {
    public static final Logger LOG = LoggerFactory.getLogger(OrderService.class);

    private final OrderRepository orderRepository;
    private final UserRepository userRepository;
    private final CakeRepository cakeRepository;

    @Autowired
    public OrderService(OrderRepository orderRepository, UserRepository userRepository, CakeRepository cakeRepository) {
        this.orderRepository = orderRepository;
        this.userRepository = userRepository;
        this.cakeRepository = cakeRepository;
    }

    public Order createOrder(OrderDTO orderDTO, Principal principal) {
//        User user = getUserByPrincipal(principal);
        UserDTO orderCustomer = orderDTO.getCustomer();
        User user = new User();
        user.setInstagram(orderCustomer.getInstagram());
        user.setName(orderCustomer.getName());
        user.setPhone(orderCustomer.getPhone());
        user = userRepository.save(user);

        Order order = new Order();
        order.setCustomer(user);
        order.setResultPrice(orderDTO.getResultPrice());
        order.setDate(LocalDate.now());
        List<Cake> cakes = orderDTO.getCakesData().stream().map(CakeFacade::cakeDTOToCake).collect(Collectors.toList());
        cakes.forEach(cake -> cake.setOrder(order));
        order.setCakes(cakes);
        order.calculateResultPrice();
        LOG.info("Saving order for user: {}", user.getInstagram());
        return orderRepository.save(order);
    }

    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }

    public List<Order> getAllOrdersOnDate(LocalDate date) {
        return orderRepository.findAllByDate(date);
    }

    public void addCakesToOrder(Order order, List<Cake> cakes) {
        order.setCakes(cakes);
        orderRepository.save(order);
    }

    private User getUserByPrincipal(Principal principal) {
        String instagram = principal.getName();
        return userRepository.findUserByInstagram(instagram).orElseThrow(() -> new UsernameNotFoundException("Username " + instagram + " not found"));
    }
}
