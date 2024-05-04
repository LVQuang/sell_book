package dev.lvpq.sell_book.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

@NoArgsConstructor
@AllArgsConstructor
@Getter @Setter
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    String id;
    String title;
    String author;
    String country;
    String category;
    Double price;
    Integer pageSize;
    @OneToOne
    CartItem cartItem;
}