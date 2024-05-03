package dev.lvpq.sell_book.controller;

import dev.lvpq.sell_book.dto.request.TransactionRequest;
import dev.lvpq.sell_book.dto.response.TransactionResponse;
import dev.lvpq.sell_book.service.PostService;
import dev.lvpq.sell_book.service.TransactionService;
import dev.lvpq.sell_book.service.UserService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
@RequestMapping("/transaction")
@Controller
public class TransactionController {
    TransactionService transactionService;
    UserService userService;
    PostService postService;
    @PostMapping("/create")
    String postCreate(@RequestParam(name = "price") Integer price,
                  @RequestParam(name = "postId") String postId) {
        var request = TransactionRequest.builder()
                .price(Double.valueOf(price)).build();
        transactionService.create(request, postId);
        return "index";
    }

    @GetMapping("/create/{postId}")
    String getCreate(@PathVariable String postId, Model model) {

        var transaction = TransactionResponse.builder()
                .transactionDate(LocalDate.now())
                .sender(userService.getCurrentUser().getName())
                .receiver(postService.getById(postId).getUser().getName())
                .build();


        var tmpPostId = postId;

        model.addAttribute("transaction", transaction);
        model.addAttribute("postId", tmpPostId);
        return "add/addTransaction";
    }

    @GetMapping("/renderTransaction/{transactionId}")
    String renderTransaction(Model model, @PathVariable String transactionId)
    {
        var transaction = transactionService.getById(transactionId);
        model.addAttribute("transaction", transaction);
        return "layout/renderTransaction";
    }

    @GetMapping("/accept/{transactionId}")
    String accept(@PathVariable String transactionId) {
        transactionService.accept(transactionId);
        return "redirect:/transaction/user/null?page=0";
    }

    @GetMapping("/cancel/{transactionId}")
    String cancel(@PathVariable String transactionId) {
        transactionService.cancel(transactionId);
        return "redirect:/transaction/user/null?page=0";
    }

    @GetMapping("/{pageNumber}")
    String getTransaction(Model model,
                   @RequestParam(name = "page",
                           required = false, defaultValue = "0") Integer pageNumber
    )
    {
        if (pageNumber != null && pageNumber > 0)
            pageNumber-=1;
        if(pageNumber == null)
            pageNumber =0;

        if (transactionService.getAll().isEmpty())
            return "/layout/transactions";

        var result = transactionService.getAllContactsPage(pageNumber);
        var transactions = result.getContent();
        model.addAttribute("transactions", transactions);
        model.addAttribute("totalPages", result.getTotalPages() - 1);
        if(result.getTotalPages() == 0) {
            return "/layout/transactions";
        }
        if(result.getTotalPages() <= pageNumber)
            return "redirect:/transaction/null?page=0&outPage=true";
        return "/layout/transactions";
    }

    @GetMapping("/user/{pageNumber}")
    String getMyTransaction(Model model,
                      @RequestParam(name = "page",
                              required = false, defaultValue = "0") Integer pageNumber
    )
    {
        if (pageNumber != null && pageNumber > 0)
            pageNumber-=1;
        if(pageNumber == null)
            pageNumber =0;

        if (transactionService.getMyTransactions().isEmpty())
            return "layout/userTransactions";

        var result = transactionService.getAllContactsUserPage(pageNumber);
        var transactions = result.getContent();
        model.addAttribute("transactions", transactions);
        model.addAttribute("totalPages", result.getTotalPages());
        if(result.getTotalPages() == 0) {
            return "/layout/userTransactions";
        }
        if(result.getTotalPages() <= pageNumber)
            return "redirect:/transaction/user/null?page=0&outPage=true";
        return "layout/userTransactions";
    }
}
