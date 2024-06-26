package dev.lvpq.sell_book.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
public class Bill {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    String id;
    String deliveryAddress;
    String billState;
    LocalDate billDate;
    @ManyToOne
    User user;
    @OneToMany(mappedBy = "bill")
    Set<CartItem> cartItems;
}
