package ru.kustikov.cakes.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ru.kustikov.cakes.entity.User;
import ru.kustikov.cakes.repository.UserRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    @Autowired
    public CustomUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }


    @Override
    public UserDetails loadUserByUsername(String username) {
        User user = userRepository.findUserByInstagram(username)
                .orElseThrow(() -> new UsernameNotFoundException("User with inst " + username + " not found."));
        return build(user);
    }

    public User loadUserById(Long id) {
        User user = userRepository.findUserById(id)
                .orElseThrow(() -> new UsernameNotFoundException("User not found."));
        return build(user);
    }

    public static User build(User user) {
        List<GrantedAuthority> authorities = user.getRoles().stream()
                .map(role -> new SimpleGrantedAuthority(role.name()))
                .collect(Collectors.toList());
        return new User(
                user.getId(),
                user.getInstagram(),
                user.getPassword(),
                authorities
        );
    }
}
