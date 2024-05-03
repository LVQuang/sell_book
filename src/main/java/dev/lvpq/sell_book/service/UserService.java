package dev.lvpq.sell_book.service;

import dev.lvpq.sell_book.dto.request.UserRequest;
import dev.lvpq.sell_book.dto.response.UserResponse;
import dev.lvpq.sell_book.entity.User;
import dev.lvpq.sell_book.enums.ErrorCode;
import dev.lvpq.sell_book.exception.AppException;
import dev.lvpq.sell_book.mapper.UserMapper;
import dev.lvpq.sell_book.repository.*;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;

@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
@Slf4j
@Service
public class UserService {
    UserRepository userRepository;
    RoleRepository roleRepository;
    ContactRepository contactRepository;
    TransactionRepository transactionRepository;
    PostRepository postRepository;
    PasswordEncoder passwordEncoder;
    UserMapper userMapper;

    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public List<UserResponse> getAll() {
        var users = userRepository.findAll();
        return users.stream()
                .map(userMapper::toResponse)
                .toList();
    }

    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public UserResponse getById(String id) {
        var user = userRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.ITEM_DONT_EXISTS));
        return userMapper.toResponse(user);
    }

    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public UserResponse getMyInfo() {
        var user = getCurrentUser();
        return userMapper.toResponse(user);
    }

    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public UserResponse update(UserRequest request) {
        var user = getCurrentUser();
        userMapper.update(user, request);

        var roles = roleRepository.findAllById(request.getRoles());
        user.setRoles(new HashSet<>(roles));

        var contacts = contactRepository.findAllById(request.getContacts());
        user.setContacts(new HashSet<>(contacts));

        var transactions = transactionRepository.findAllById(request.getTransactions());
        user.setTransactions(new HashSet<>(transactions));

        var posts = postRepository.findAllById(request.getPosts());
        user.setPosts(new HashSet<>(posts));

        user.setPassword(passwordEncoder.encode(request.getPassword()));
        return userMapper.toResponse(userRepository.save(user));
    }

    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public User getCurrentUser() {
        var name = SecurityContextHolder.getContext().getAuthentication().getName();
        return userRepository.findByName(name)
                .orElseThrow(() -> new AppException(ErrorCode.ITEM_DONT_EXISTS));
    }

    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public Page<User> getAllUsersPage(int page) {
        var result = userRepository.findAll();
        return getAllUsersPageImpl(page , result);
    }

    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    private Page<User> getAllUsersPageImpl(int page, List<User> result) {
        int pageSize = 10;

        if(result.size() < pageSize)
            pageSize = result.size() ;

        Pageable pageable = PageRequest.of(page, pageSize);

        int start =(int) pageable.getOffset();
        int end = Math.min( (start + pageable.getPageSize()) , result.size());

        var content = result.subList(start, end);
        return new PageImpl<>(content, pageable, result.size());
    }
}
