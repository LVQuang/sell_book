package dev.lvpq.sell_book.api;

import com.nimbusds.jose.JOSEException;
import dev.lvpq.sell_book.dto.request.AuthenticationRequest;
import dev.lvpq.sell_book.dto.request.LogoutRequest;
import dev.lvpq.sell_book.dto.request.RegisterRequest;
import dev.lvpq.sell_book.dto.response.ApiResponse;
import dev.lvpq.sell_book.dto.response.AuthenticationResponse;
import dev.lvpq.sell_book.dto.response.RegisterResponse;
import dev.lvpq.sell_book.exception.AppException;
import dev.lvpq.sell_book.service.AuthenticationService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;


@RestController
@RequestMapping("/api/auth")
@Slf4j
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AuthenticationAPI {
    AuthenticationService authenticationService;

    @PostMapping("/login")
    ApiResponse<AuthenticationResponse> authenticate(@RequestBody AuthenticationRequest request) {
        var result = authenticationService.authenticate(request, false);
        return ApiResponse.<AuthenticationResponse>builder()
                .result(result)
                .build();
    }

    @PostMapping("/register")
    ApiResponse<RegisterResponse> register(@RequestBody RegisterRequest request) {
        var result = authenticationService.register(request);
        return ApiResponse.<RegisterResponse>builder()
                .result(result)
                .build();
    }

    @PostMapping("/logout")
    ApiResponse<Void> logout(@RequestBody LogoutRequest request)
            throws ParseException, JOSEException {

        if (!authenticationService.logout(request))
            throw new AppException();

        return ApiResponse.<Void>builder()
                .build();
    }

}
