package dev.lvpq.sell_book.dto.request;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Builder
public class AddPostRequest {
    @NonNull
    String id;
    @NonNull
    String title;
    @NonNull
    String author;
    @NonNull
    String country;
    @NonNull
    String category;
    @NonNull
    Double price;
    @NonNull
    Integer pageSize;
}
