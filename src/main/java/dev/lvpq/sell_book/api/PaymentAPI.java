package dev.lvpq.sell_book.api;

import dev.lvpq.sell_book.dto.request.PaymentRequest;
import dev.lvpq.sell_book.dto.response.ApiResponse;
import dev.lvpq.sell_book.dto.response.PaymentResponse;
import dev.lvpq.sell_book.service.PaymentService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.UnsupportedEncodingException;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class PaymentAPI {

    PaymentService paymentService;

    @GetMapping("/payment")
    ApiResponse<PaymentResponse> pay(@RequestBody PaymentRequest request) throws UnsupportedEncodingException {
        var result = paymentService.payment(request);
        return ApiResponse.<PaymentResponse>builder()
                .result(result)
                .build();
    }
}
