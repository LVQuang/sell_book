package dev.lvpq.sell_book.controller;

import dev.lvpq.sell_book.service.UserService;
import dev.lvpq.sell_book.dto.request.ContactRequest;
import dev.lvpq.sell_book.dto.response.ContactResponse;
import dev.lvpq.sell_book.service.ContactService;
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
@RequestMapping("/contact")
@Controller
public class ContactController {
    ContactService contactService;
    UserService userService;

    @GetMapping("/create/{contactId}")
    String createContact(Model model, @PathVariable String contactId)
    {
        var contact = ContactResponse.builder()
                .contactDate(LocalDate.now())
                .sender(userService.getCurrentUser().getName())
                .receiver(userService.getById(contactId).getName())
                .build();

        model.addAttribute("request",contact);
        return "add/addContact";
    }

    @PostMapping("/create")
    String postCreate(@RequestParam(name = "message") String mess,
                      @RequestParam(name = "contactId") String contactId) {
        var request = ContactRequest.builder()
                .message(mess)
                .build();
        contactService.create(request, contactId);
        return "index";
    }

    @GetMapping("/{pageNumber}")
    String getContact(Model model,
                   @RequestParam(name = "page",
                           required = false, defaultValue = "0") Integer pageNumber
    )
    {

        if (pageNumber != null && pageNumber > 0)
            pageNumber-=1;
        if(pageNumber == null)
            pageNumber =0;

        if (contactService.getAll().isEmpty())
            return "/layout/contacts";

        var result = contactService.getAllContactsPage(pageNumber);
        var contacts = result.getContent();
        model.addAttribute("contacts", contacts);
        model.addAttribute("totalPages", result.getTotalPages());
        if(result.getTotalPages() == 0) {
            return "/layout/contacts";
        }
        if(result.getTotalPages() <= pageNumber)
            return "redirect:/contact/null?page=0&outPage=true";
        return "/layout/contacts";
    }

    @GetMapping("/user/{pageNumber}")
    String getMyContact(Model model,
                      @RequestParam(name = "page",
                              required = false, defaultValue = "0") Integer pageNumber
    )
    {

        if (pageNumber != null && pageNumber > 0)
            pageNumber-=1;
        if(pageNumber == null)
            pageNumber =0;

        if (contactService.getAllMyContacts().isEmpty())
            return "layout/userContacts";

        var result = contactService.getAllContactsUserPage(pageNumber);
        var contacts = result.getContent();
        model.addAttribute("contacts", contacts);
        model.addAttribute("totalPages", result.getTotalPages());
        if(result.getTotalPages() == 0) {
            return "/layout/userContacts";
        }
        if(result.getTotalPages() <= pageNumber)
            return "redirect:/contact/user/null?page=0&outPage=true";
        return "layout/userContacts";
    }

}
