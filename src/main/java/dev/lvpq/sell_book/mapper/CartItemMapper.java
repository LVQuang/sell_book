package dev.lvpq.sell_book.mapper;

import dev.lvpq.sell_book.dto.response.CartItemResponse;
import dev.lvpq.sell_book.entity.CartItem;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface CartItemMapper {
    @Mapping(source = "post.id", target = "postId")
    @Mapping(source = "post.title", target = "postTitle")
    CartItemResponse toCartItemResponse(CartItem cartItem);
}
