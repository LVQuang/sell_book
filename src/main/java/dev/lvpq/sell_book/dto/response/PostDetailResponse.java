package dev.lvpq.sell_book.dto.response;

import dev.lvpq.sell_book.enums.PostState;
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
public class PostDetailResponse {
    String id;
    String title;
    String author;
    String country;
    String category;
    Double price;
    Integer pageSize;
}
