package dev.lvpq.sell_book.mapper;

import dev.lvpq.sell_book.dto.response.CartItemResponse;
import dev.lvpq.sell_book.entity.CartItem;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface CartItemMapper {
    @Mapping(target = "postTitle", ignore = true)
    CartItemResponse toCartItemResponse(CartItem cartItem);
}
