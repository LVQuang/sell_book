package dev.lvpq.sell_book.dto.request;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Builder
public class UserRegisterRequest {
    @NonNull
    String name;
    @NonNull
    String email;
    @NonNull
    String phone;
    String gender;
    @NonNull
    String password;
    @NonNull
    String re_password;
}
