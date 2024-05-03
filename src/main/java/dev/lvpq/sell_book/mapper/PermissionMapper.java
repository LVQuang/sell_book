package dev.lvpq.sell_book.mapper;

import dev.lvpq.sell_book.dto.request.PermissionRequest;
import dev.lvpq.sell_book.dto.response.PermissionResponse;
import dev.lvpq.sell_book.entity.Permission;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PermissionMapper {
    Permission convertEntity(PermissionRequest request);
    PermissionResponse toResponse(Permission permission);
}
