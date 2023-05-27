package ru.kustikov.cakes.controller;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.kustikov.cakes.service.UserService;

@RestController
@RequestMapping("api/user")
@CrossOrigin
public class CustomerController {
    private final UserService userService;


    public CustomerController(UserService userService) {
        this.userService = userService;
    }


}
