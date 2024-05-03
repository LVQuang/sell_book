package dev.lvpq.sell_book.service;

import dev.lvpq.sell_book.dto.response.UserResponse;
import dev.lvpq.sell_book.entity.User;
import dev.lvpq.sell_book.enums.ErrorCode;
import dev.lvpq.sell_book.exception.AppException;
import dev.lvpq.sell_book.mapper.UserMapper;
import dev.lvpq.sell_book.repository.UserRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
@Slf4j
@Service
public class UserService {
    UserRepository userRepository;
    UserMapper userMapper;


    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public UserResponse getById(String id) {
        var user = userRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.ITEM_DONT_EXISTS));
        return userMapper.toResponse(user);
    }

    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public User getCurrentUser() {
        var name = SecurityContextHolder.getContext().getAuthentication().getName();
        return userRepository.findByName(name)
                .orElseThrow(() -> new AppException(ErrorCode.ITEM_DONT_EXISTS));
    }
}
