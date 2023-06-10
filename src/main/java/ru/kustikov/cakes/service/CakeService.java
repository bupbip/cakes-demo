package ru.kustikov.cakes.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ru.kustikov.cakes.dto.CakeDTO;
import ru.kustikov.cakes.dto.OrderDTO;
import ru.kustikov.cakes.entity.Cake;
import ru.kustikov.cakes.entity.Order;
import ru.kustikov.cakes.entity.User;
import ru.kustikov.cakes.entity.enums.CakeSize;
import ru.kustikov.cakes.exception.OrderNotFoundException;
import ru.kustikov.cakes.repository.CakeRepository;
import ru.kustikov.cakes.repository.OrderRepository;
import ru.kustikov.cakes.repository.UserRepository;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.security.Principal;
import java.time.LocalDate;
import java.util.List;
import java.util.zip.DataFormatException;
import java.util.zip.Deflater;
import java.util.zip.Inflater;

@Service
public class CakeService {
    public static final Logger LOG = LoggerFactory.getLogger(CakeService.class);

    private final UserRepository userRepository;
    private final OrderRepository orderRepository;
    private final CakeRepository cakeRepository;

    @Autowired
    public CakeService(UserRepository userRepository, OrderRepository orderRepository, CakeRepository cakeRepository) {
        this.userRepository = userRepository;
        this.orderRepository = orderRepository;
        this.cakeRepository = cakeRepository;
    }

    public Cake saveCake(Long orderId, CakeDTO cakeDTO, Principal principal) {
        User user = getUserByPrincipal(principal);
        Order order = orderRepository.findOrderById(orderId).orElseThrow(() -> new OrderNotFoundException("Order not found."));
        Cake cake = new Cake();
        cake.setOrder(order);
        cake.setCakeSize(CakeSize.valueOf(cakeDTO.getCakeSize()));
        cake.setCakePrice(cakeDTO.getCakePrice());
        cake.setDesignPrice(cakeDTO.getDesignPrice());
        cake.setDesignRating(cakeDTO.getDesignRating());
        LOG.info("Saving order for user: {}", user.getInstagram());
        return cakeRepository.save(cake);
    }

    public List<Cake> getAllCakesForOrder(Long orderId) {
        Order order = orderRepository.findOrderById(orderId).orElseThrow(() -> new OrderNotFoundException("Order not found."));
        return cakeRepository.findAllByOrder(order);
    }

    private byte[] compressBytes(byte[] data) {
        Deflater deflater = new Deflater();
        deflater.setInput(data);
        deflater.finish();
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream(data.length);
        byte[] buffer = new byte[1024];
        while (!deflater.finished()) {
            int cnt = deflater.deflate(buffer);
            outputStream.write(buffer, 0, cnt);
        }
        try {
            outputStream.close();
        } catch (IOException e) {
            LOG.error("Cannot compress bytes");
        }
        System.out.println("Compressed image byte size - " + outputStream.toByteArray().length);
        return outputStream.toByteArray();
    }

    private static byte[] decompressBytes(byte[] data) {
        Inflater inflater = new Inflater();
        inflater.setInput(data);
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream(data.length);
        byte[] buffer = new byte[1024];
        try {
            while (!inflater.finished()) {
                int cnt = inflater.inflate(buffer);
                outputStream.write(buffer, 0, cnt);
            }
            outputStream.close();
        } catch (IOException | DataFormatException e) {
            LOG.error("Cannot decompress bytes");
        }
        return outputStream.toByteArray();
    }

    private User getUserByPrincipal(Principal principal) {
        String inst = principal.getName();
        return userRepository.findUserByInstagram(inst)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with inst " + inst));
    }
}
