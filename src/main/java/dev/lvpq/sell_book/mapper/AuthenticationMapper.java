package dev.lvpq.sell_book.mapper;

import dev.lvpq.sell_book.dto.request.AuthenticationRequest;
import dev.lvpq.sell_book.dto.request.UserLoginRequest;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface AuthenticationMapper {
    AuthenticationRequest toAuthenticationRequest(UserLoginRequest request);
}
