package dev.lvpq.sell_book.dto.request;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Builder
public class UserLoginRequest {

    @NotNull(message = "Email should not be empty")
    String name;
    @NotEmpty(message = "Password should not be empty")
    String pass;
}
