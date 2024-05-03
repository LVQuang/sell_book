package dev.lvpq.sell_book.mapper;

import dev.lvpq.sell_book.dto.request.UserRequest;
import dev.lvpq.sell_book.entity.User;
import dev.lvpq.sell_book.dto.response.UserResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface UserMapper {

    UserResponse toResponse(User user);
    @Mapping(target = "roles", ignore = true)
    @Mapping(target = "contacts", ignore = true)
    @Mapping(target = "posts", ignore = true)
    void update(@MappingTarget User user, UserRequest request);

}
