package dev.lvpq.sell_book.controller;

import dev.lvpq.sell_book.service.BillService;
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
@RequestMapping("/bill")
@Controller
public class BillController {
    BillService billService;

    @GetMapping
    String getMyBills(Model model) {
        var bills = billService.getAllUserBill();
        model.addAttribute("bills", bills);
        return "layout/bill";
    }

    @GetMapping("/admin")
    String getBills(Model model) {
        var bills = billService.getAllBills();
        model.addAttribute("bills", bills);
        return "layout/bill";
    }

    @GetMapping("/check/{id}")
    String checkBill(@PathVariable String id) {
        billService.checkBill(id);
        return "redirect:/bill/admin";
    }
}
