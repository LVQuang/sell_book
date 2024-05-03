package dev.lvpq.sell_book.mapper;

import dev.lvpq.sell_book.dto.request.ForgotPasswordRequest;
import dev.lvpq.sell_book.dto.request.ResetPasswordRequest;
import dev.lvpq.sell_book.dto.response.ForgotPasswordResponse;
import dev.lvpq.sell_book.entity.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ForgotPasswordMapper {
//    User convertEntity(ForgotPasswordRequest request);
    ForgotPasswordResponse toResponse(User user);

    ForgotPasswordRequest toForgotPasswordRequest(ForgotPasswordRequest request);

    ForgotPasswordRequest toResetPasswordRequest(ResetPasswordRequest request);
}
