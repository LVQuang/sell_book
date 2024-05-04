package dev.lvpq.sell_book.entity;

import dev.lvpq.sell_book.enums.UserGender;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.Set;

@Entity
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Builder
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    String id;
    @Column(nullable = false, unique = true)
    String name;
    String password;
    String email;
    String phone;
    UserGender gender;
    @ManyToMany
    Set<Role> roles;
    @OneToMany(mappedBy = "user")
    Set<Bill> bills;
    @OneToMany(mappedBy = "user",  fetch = FetchType.EAGER)
    Set<CartItem> cartItems;
}