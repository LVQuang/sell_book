package dev.lvpq.sell_book.api;

import dev.lvpq.sell_book.dto.request.ContactRequest;
import dev.lvpq.sell_book.dto.response.ApiResponse;
import dev.lvpq.sell_book.dto.response.ContactResponse;
import dev.lvpq.sell_book.service.ContactService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/contacts")
@RequiredArgsConstructor @Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ContactAPI {
    ContactService contactService;

    @GetMapping
    ApiResponse<List<ContactResponse>> getAll() {
        return ApiResponse.<List<ContactResponse>>builder()
                .result(contactService.getAll())
                .build();
    }

    @PostMapping("/{receiveId}")
    ApiResponse<ContactResponse> create(@RequestBody ContactRequest request, @PathVariable String receiveId) {
        return ApiResponse.<ContactResponse>builder()
                .result(contactService.create(request, receiveId))
                .build();
    }

    @DeleteMapping("/{permission}")
    ApiResponse<Void> delete(@PathVariable String permission) {
        contactService.delete(permission);
        return ApiResponse.<Void>builder()
                .code(2045)
                .message("Item Deleted")
                .build();
    }
}
