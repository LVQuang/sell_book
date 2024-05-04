package dev.lvpq.sell_book.controller;

import dev.lvpq.sell_book.service.CartItemService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
@RequestMapping("/cart")
@Controller
public class CartItemController {
    CartItemService cartItemService;

    @GetMapping
    String getCart(Model model) {
        var cartItems = cartItemService.getAllCartItemResponse();
        model.addAttribute("cartItems", cartItems);
        return "layout/cart";
    }

    @PostMapping("/{id}")
    String postCart(@PathVariable String id, @RequestParam(name = "quantity", required = false) Integer quantity) {
        cartItemService.createCartItem(id, quantity);
        return "redirect:/cart";
    }

    @GetMapping("/delete/{id}")
    String deletePost(@PathVariable String id) {
        cartItemService.deleteCartItem(id);
        return "redirect:/cart";
    }

    @GetMapping("/createBill")
    String renderBillForm() { return "add/addBill"; }
    @PostMapping("/createBill")
    String createBill(@RequestParam(name = "address") String address) {
        log.info(address);
        cartItemService.transToBill(address);
        return "redirect:/bill";
    }
}
