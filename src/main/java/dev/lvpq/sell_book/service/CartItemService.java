package dev.lvpq.sell_book.service;

import dev.lvpq.sell_book.dto.response.CartItemResponse;
import dev.lvpq.sell_book.entity.CartItem;
import dev.lvpq.sell_book.mapper.CartItemMapper;
import dev.lvpq.sell_book.repository.CartItemRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
@Slf4j
@Service
public class CartItemService {

    CartItemRepository cartItemRepository;
    CartItemMapper cartItemMapper;

    public List<CartItemResponse> getAllCartItemResponse() {
        var cartItems = getAll();
        return cartItems.stream().map(cartItemMapper::toCartItemResponse).toList();
    }

    public List<CartItem> getAll() {
        return cartItemRepository.findAll();
    }
}
