package dev.lvpq.sell_book.mapper;

import dev.lvpq.sell_book.dto.request.RegisterRequest;
import dev.lvpq.sell_book.dto.request.UserRegisterRequest;
import dev.lvpq.sell_book.entity.User;
import dev.lvpq.sell_book.dto.response.RegisterResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface RegisterMapper {
    @Mapping(target = "gender", ignore = true)
    User convertEntity(RegisterRequest request);
    RegisterResponse toResponse(User user);
    RegisterRequest toRegisterRequest(UserRegisterRequest request);
}
