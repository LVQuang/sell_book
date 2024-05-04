package dev.lvpq.sell_book.dto.response;

import dev.lvpq.sell_book.entity.Post;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CartItemResponse {
    String id;
    Integer quantity;
    Double totalPrice;
    String postTitle;
}
