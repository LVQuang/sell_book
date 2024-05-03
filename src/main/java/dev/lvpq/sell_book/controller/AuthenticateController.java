package dev.lvpq.sell_book.controller;

import com.nimbusds.jose.JOSEException;
import dev.lvpq.sell_book.dto.request.*;
import dev.lvpq.sell_book.mapper.AuthenticationMapper;
import dev.lvpq.sell_book.mapper.ForgotPasswordMapper;
import dev.lvpq.sell_book.mapper.RegisterMapper;
import dev.lvpq.sell_book.service.AuthenticationService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.text.ParseException;

@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
@RequestMapping("/auth")
@Controller
public class AuthenticateController {
    AuthenticationService authenticationService;
    AuthenticationMapper authenticationMapper;
    ForgotPasswordMapper forgotPasswordMapper;
    RegisterMapper registerMapper;

    @GetMapping("/login")
    String getLogin(Model model, HttpServletRequest request)
            throws ParseException, JOSEException {
        HttpSession session = request.getSession();
        String token =(String) session.getAttribute("myToken");

        if(token != null) {
            authenticationService
                    .logout(LogoutRequest.builder()
                            .token(token)
                            .build());
            session.removeAttribute("myToken");
        }

        log.info(token);

        UserLoginRequest user = new UserLoginRequest();
        model.addAttribute("user", user);
        return "user/login";
    }

    @PostMapping("/login")
    String postLogin(@Valid @ModelAttribute("user") UserLoginRequest user
            , HttpServletRequest httpRequest)
    {
        var authRequest = authenticationMapper.toAuthenticationRequest(user);
        var authentication = authenticationService.authenticate(authRequest, true);


        if(!authentication.isAuthenticated())
            return "redirect:/auth/login?incorrect";
        else
        {
            httpRequest.getSession().setAttribute("myToken", authentication.getToken());
            return "redirect:/post/null?page=0";
        }
    }

    @GetMapping("/logout")
    String getLogout(HttpServletRequest request) throws ParseException, JOSEException {
        HttpSession session = request.getSession();
        String token =(String) session.getAttribute("myToken");

        var refresh = authenticationService
                .refreshToken(RefreshRequest.builder()
                        .token(token)
                        .build());

        session.setAttribute("myToken", refresh.getToken());

        return "redirect:/auth/login";
    }

    @GetMapping("/register")
    String getRegister(Model model) {
        UserRegisterRequest user = new UserRegisterRequest();
        model.addAttribute("user", user);
        return "user/register";
    }

    @PostMapping("/register")
    String postRegister(@Valid @ModelAttribute("user") UserRegisterRequest user)
    {
        var request = registerMapper.toRegisterRequest(user);
        boolean checkName = authenticationService.checkNameExist(user);
        if(checkName)
            return "redirect:/auth/register?user_exists_name";
        authenticationService.register(request);
        return "redirect:/auth/login?register_success";
    }

    @GetMapping("/ForgotPassword")
    String getForgot(Model model){
        ForgotPasswordRequest user = new ForgotPasswordRequest();
        model.addAttribute("user", user);
        return "password/forgotPassword";
    }

    @GetMapping("/checkEmail")
    String waitEmail() {
        return "password/wait";
    }

    @PostMapping("/ForgotPassword")
    String postForgot(@Valid @ModelAttribute("user") ForgotPasswordRequest user){
        var authRequest = forgotPasswordMapper.toForgotPasswordRequest(user);
        boolean checkEmail = authenticationService.checkEmailExist(user);
        if (!checkEmail)
            return "redirect:/auth/ForgotPassword?email_not_exists";
        authenticationService.forgotPassword(authRequest);
        return "redirect:/auth/checkEmail?check_email";
    }

    @GetMapping("/ResetPassword")
    String getReset(Model model){
        ResetPasswordRequest user = new ResetPasswordRequest();
        model.addAttribute("user", user);
        return "password/resetPassword";
    }

    @PostMapping("/ResetPassword")
    String postReset(@Valid @ModelAttribute("user") ResetPasswordRequest user){
        var authRequest = forgotPasswordMapper.toResetPasswordRequest(user);
        var checkEmail = authenticationService.checkEmailExistForReset(user);
        if (!checkEmail)
            return "redirect:/auth/ResetPassword?email_not_exist";
        authenticationService.resetPassword(authRequest);
        return "redirect:/auth/login?resetPass_success";
    }
}
