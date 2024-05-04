package dev.lvpq.sell_book.controller;

import dev.lvpq.sell_book.service.CartItemService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
@RequestMapping("/cart")
@Controller
public class CartItemController {
    CartItemService cartItemService;

    @GetMapping
    String getPost(Model model) {
        var cartItems = cartItemService.getAllCartItemResponse();
        model.addAttribute("cartItems", cartItems);
        return "layout/cart";
    }
}
