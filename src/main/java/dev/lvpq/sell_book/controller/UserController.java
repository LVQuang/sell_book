package dev.lvpq.sell_book.controller;

import dev.lvpq.sell_book.service.UserService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
@RequestMapping("/user")
@Controller
public class UserController {
    UserService userService;
    @GetMapping("/{pageNumber}")
    String getUsers(Model model,
                   @RequestParam(name = "page",
                           required = false, defaultValue = "0") Integer pageNumber
    )
    {
        if (pageNumber != null && pageNumber > 0)
            pageNumber-=1;
        if(pageNumber == null)
            pageNumber =0;

        if (userService.getAll().isEmpty())
            return "/layout/users";

        var result = userService.getAllUsersPage(pageNumber);
        var users = result.getContent();


        model.addAttribute("users", users);
        model.addAttribute("totalPages", result.getTotalPages());
        if(result.getTotalPages() == 0) {
            return "/layout/users";
        }
        if(result.getTotalPages() <= pageNumber)
            return "redirect:/user/null?page=0&outPage=true";
        return "/layout/users";
    }

}
