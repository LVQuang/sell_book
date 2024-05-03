package dev.lvpq.sell_book.dto.request;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Builder
public class UserRequest {
    String id;
    String name;
    String password;
    String email;
    String phone;
    Set<String> roles;
    Set<String> transactions;
    Set<String> posts;
    Set<String> contacts;
}
