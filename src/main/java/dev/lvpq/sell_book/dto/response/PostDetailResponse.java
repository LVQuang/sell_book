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
    String type;
    String address;
    String description;
    Double price;
    LocalDate postDate;
    PostState available;
    UserResponse user;
    Set<TransactionResponse> transactions;
}
