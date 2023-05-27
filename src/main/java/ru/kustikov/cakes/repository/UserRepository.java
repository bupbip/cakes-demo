package ru.kustikov.cakes.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.kustikov.cakes.entity.User;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findUserByInstagram(String inst);
    Optional<User> findUserByPhone(String phone);
    Optional<User> findUserById(Long id);
}
