package ru.kustikov.cakes.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.ObjectUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.kustikov.cakes.dto.OrderDTO;
import ru.kustikov.cakes.entity.Order;
import ru.kustikov.cakes.facade.OrderFacade;
import ru.kustikov.cakes.service.OrderService;
import ru.kustikov.cakes.validations.ResponseErrorValidation;

import javax.validation.Valid;
import java.security.Principal;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("api/order")
@CrossOrigin
public class OrderController {
    private final OrderService orderService;
    private final ResponseErrorValidation responseErrorValidation;
    private final OrderFacade orderFacade;

    @Autowired
    public OrderController(OrderService orderService, ResponseErrorValidation responseErrorValidation, OrderFacade orderFacade) {
        this.orderService = orderService;
        this.responseErrorValidation = responseErrorValidation;
        this.orderFacade = orderFacade;
    }

    @PostMapping("/create")
    public ResponseEntity<Object> createOrder(@Valid @RequestBody OrderDTO orderDTO,
                                              BindingResult bindingResult,
                                              Principal principal) {
        ResponseEntity<Object> errors = responseErrorValidation.mapValidationService(bindingResult);
        if (!ObjectUtils.isEmpty(errors)) return errors;

        Order order = orderService.createOrder(orderDTO, principal);
        OrderDTO createdOrder = orderFacade.orderToOrderDTO(order);
        return new ResponseEntity<>(createdOrder, HttpStatus.OK);
      }

    @GetMapping("/all")
    public ResponseEntity<List<OrderDTO>> getAllOrders() {
        List<OrderDTO> ordersList = orderService.getAllOrders()
                .stream()
                .map(orderFacade::orderToOrderDTO)
                .collect(Collectors.toList());
        return new ResponseEntity<>(ordersList, HttpStatus.OK);
    }
}
