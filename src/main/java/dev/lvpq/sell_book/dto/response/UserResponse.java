package dev.lvpq.sell_book.dto.response;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.Set;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Builder
public class UserResponse {
    String id;
    String name;
    String password;
    String email;
    String phone;
    String gender;
    Set<RoleResponse> roles;
    Set<TransactionResponse> transactions;
    Set<ContactResponse> contacts;
}
