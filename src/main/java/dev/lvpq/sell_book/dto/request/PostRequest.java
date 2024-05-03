package dev.lvpq.sell_book.dto.request;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class PostRequest {
    String title;
    String author;
    String country;
    String category;
    Double price;
    Integer pageSize;
}
