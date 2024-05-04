package dev.lvpq.sell_book.service;

import dev.lvpq.sell_book.dto.response.CartItemResponse;
import dev.lvpq.sell_book.entity.CartItem;
import dev.lvpq.sell_book.entity.Post;
import dev.lvpq.sell_book.entity.User;
import dev.lvpq.sell_book.enums.ErrorCode;
import dev.lvpq.sell_book.exception.AppException;
import dev.lvpq.sell_book.mapper.CartItemMapper;
import dev.lvpq.sell_book.repository.CartItemRepository;
import dev.lvpq.sell_book.repository.PostRepository;
import dev.lvpq.sell_book.repository.UserRepository;
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
    PostRepository postRepository;
    UserRepository userRepository;
    CartItemMapper cartItemMapper;
    UserService userService;
    public List<CartItemResponse> getAllCartItemResponse() {
        var user = userService.getCurrentUser();
        var cartItems = cartItemRepository.findByUser(user);
        return cartItems.stream().map(cartItemMapper::toCartItemResponse).toList();
    }

    public void createCartItem(String postId, Integer quantity) {
        var user = userService.getCurrentUser();
        var post = postRepository.findById(postId)
                .orElseThrow(() -> new AppException(ErrorCode.ITEM_DONT_EXISTS));

        var exist = checkCartItemExists(user, postId);

        if (exist != null) {
            exist.setQuantity(exist.getQuantity() + quantity);
            cartItemRepository.save(exist);
            return;
        }

        var cartItem = CartItem.builder()
                .quantity(quantity)
                .totalPrice(quantity * post.getPrice())
                .post(post)
                .user(user)
                .build();

        saveToRepo(cartItem, user, post);
    }

    public void deleteCartItem(String cartId) {
        var cartItem = cartItemRepository.findById(cartId)
                .orElseThrow(() -> new AppException(ErrorCode.ITEM_DONT_EXISTS));

        var user = userService.getCurrentUser();
        var post = cartItem.getPost();

        post.setCartItem(null);
        postRepository.save(post);

        user.getCartItems().add(cartItem);
        userRepository.save(user);

        cartItem.setPost(post);
        cartItem.setUser(user);
    }

    private CartItem checkCartItemExists(User user, String postId) {
        return user.getCartItems().stream()
                .filter(cartItem -> cartItem.getPost().getId().equals(postId))
                .findFirst()
                .orElse(null);
    }

    private void saveToRepo(CartItem cartItem, User user, Post post) {
        cartItemRepository.save(cartItem);

        post.setCartItem(cartItem);
        postRepository.save(post);

        user.getCartItems().add(cartItem);
        userRepository.save(user);
    }
}
