package ru.kustikov.cakes.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.kustikov.cakes.entity.User;
import ru.kustikov.cakes.entity.Order;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
    List<Order> findAllByCustomer(User user);
    Optional<Order> findOrderById(Long id);
    List<Order> findAllByDate(LocalDate date);
}
