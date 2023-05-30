package ru.kustikov.cakes.service;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import ru.kustikov.cakes.entity.User;
import ru.kustikov.cakes.entity.enums.Role;
import ru.kustikov.cakes.exception.UserExistException;
import ru.kustikov.cakes.payload.request.SignupRequest;
import ru.kustikov.cakes.repository.UserRepository;

import java.security.Principal;

@Service
public class UserService {
    public static final Logger LOG = LoggerFactory.getLogger(UserService.class);

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    @Autowired
    public UserService(UserRepository userRepository, BCryptPasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public User createUser(SignupRequest userIn) {
        User user = new User();
        user.setInstagram(userIn.getInst());
        user.setName(userIn.getName());
        user.setPassword(passwordEncoder.encode(userIn.getPassword()));
        user.getRoles().add(Role.ROLE_CUSTOMER);

        try {
            LOG.info("Saving user {}", user.getInstagram());
            return userRepository.save(user);
        } catch (Exception e) {
            LOG.error("Error during registration. {}", e.getMessage());
            throw new UserExistException("The user " + user.getInstagram() + " already exists");
        }
    }

    public User getCurrentUser(Principal principal) {
        return getUserByPrincipal(principal);
    }

    private User getUserByPrincipal(Principal principal) {
        String inst = principal.getName();
        return userRepository.findUserByInstagram(inst)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with inst " + inst));
    }
}
