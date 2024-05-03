package dev.lvpq.sell_book.entity;

import dev.lvpq.sell_book.enums.PostState;
import dev.lvpq.sell_book.enums.TypePost;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;

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
    TypePost type;
    String address;
    String description;
    Double price;
    LocalDate postDate;
    PostState available;
    @ManyToOne
    User user;
}