package dev.lvpq.sell_book.dto.request;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Builder
public class ResetPasswordRequest {
    @NonNull
    String email;
    @NonNull
    String password;
    @NonNull
    String re_password;
}
