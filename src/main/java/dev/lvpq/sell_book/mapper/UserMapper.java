package dev.lvpq.sell_book.mapper;

import dev.lvpq.sell_book.dto.response.UserResponse;
import dev.lvpq.sell_book.entity.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {
    UserResponse toResponse(User user);
}
