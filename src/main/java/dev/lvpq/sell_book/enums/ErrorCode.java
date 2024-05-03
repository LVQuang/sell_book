package dev.lvpq.sell_book.enums;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;

@Getter @AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public enum ErrorCode {
    ITEM_EXISTS(1001, "Item exists", HttpStatus.BAD_REQUEST),
    ITEM_DONT_EXISTS(1002, "Item don't exist", HttpStatus.NOT_FOUND),
    UNAUTHENTICATED(1007, "Unauthenticated", HttpStatus.UNAUTHORIZED),
    UNAUTHORIZED(1006 , "Access Denied", HttpStatus.FORBIDDEN),
    DUPLICATE_PASSWORD(1013,"Duplicate Password", HttpStatus.BAD_REQUEST),
    INCORRECTPASSWORD(1007 , "Wrong Password", HttpStatus.BAD_REQUEST),
    INVALIDATEDTOKEN(1008 , "Token is invalid", HttpStatus.BAD_REQUEST),
    OTHER_EXCEPTION(9999, "Other Exception", HttpStatus.INTERNAL_SERVER_ERROR);

    int code;
    String message;
    HttpStatusCode statusCode;
}

