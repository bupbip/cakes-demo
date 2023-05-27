package ru.kustikov.cakes.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.kustikov.cakes.entity.Cake;
import ru.kustikov.cakes.entity.Order;

import java.util.List;
import java.util.Optional;

@Repository
public interface CakeRepository extends JpaRepository<Cake, Long> {
    Optional<Cake> findCakeById(Long id);
    List<Cake> findAllByOrder(Order order);
}
