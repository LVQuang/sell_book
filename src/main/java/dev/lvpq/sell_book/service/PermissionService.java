package dev.lvpq.sell_book.service;

import dev.lvpq.sell_book.exception.AppException;
import dev.lvpq.sell_book.mapper.PermissionMapper;
import dev.lvpq.sell_book.repository.PermissionRepository;
import dev.lvpq.sell_book.dto.request.PermissionRequest;
import dev.lvpq.sell_book.dto.response.PermissionResponse;
import dev.lvpq.sell_book.enums.ErrorCode;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;


@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor  @Slf4j
@Service
public class PermissionService {
    PermissionRepository permissionRepository;
    PermissionMapper permissionMapper;

    public PermissionResponse create(PermissionRequest request) {
        if(permissionRepository.existsById(request.getName())) {
            throw new AppException(ErrorCode.ITEM_EXISTS);
        }
        var permission = permissionMapper.convertEntity(request);
        permission = permissionRepository.save(permission);
        return permissionMapper.toResponse(permission);
    }

    public List<PermissionResponse> getAll() {
        var permissions = permissionRepository.findAll();
        return permissions
                .stream()
                .map(permissionMapper::toResponse)
                .toList();
    }

    public void delete(String permission) {
        if(!permissionRepository.existsById(permission))
            throw new AppException(ErrorCode.ITEM_DONT_EXISTS);
        permissionRepository.deleteById(permission);
    }
}
