package ru.kustikov.cakes.entity;

import jakarta.persistence.*;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import ru.kustikov.cakes.entity.enums.Role;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Data
@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String name;

    @Column(unique = true)
    private String instagram;

    @Column(unique = true)
    private String phone;

    @Column(length = 3000)
    private String password;

    @ElementCollection(targetClass = Role.class)
    @CollectionTable(name = "user_role",
    joinColumns = @JoinColumn(referencedColumnName = "id"))
    private Set<Role> role = new HashSet<>();

    /**
     * Поле для комментария к пользователю
     */
    @Column(columnDefinition = "text")
    private String info;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "user", orphanRemoval = true)
    private List<Order> orders;

    @Transient
    private Collection<? extends GrantedAuthority> authorities;
}
