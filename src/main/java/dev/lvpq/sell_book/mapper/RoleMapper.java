package dev.lvpq.sell_book.mapper;

import dev.lvpq.sell_book.dto.request.RoleRequest;
import dev.lvpq.sell_book.dto.response.RoleResponse;
import dev.lvpq.sell_book.entity.Role;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface RoleMapper {
    @Mapping(target = "permissions", ignore = true)
    Role convertEntity(RoleRequest request);
    RoleResponse toResponse(Role role);
}
