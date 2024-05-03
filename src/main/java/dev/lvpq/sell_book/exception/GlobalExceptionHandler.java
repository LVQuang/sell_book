package dev.lvpq.sell_book.exception;

import dev.lvpq.sell_book.enums.ErrorCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(value = Exception.class)
    public ModelAndView handlingOtherException(RuntimeException exception) {
        log.info("Exception: " + exception);
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("error/otherException");
        modelAndView.addObject("message", exception.getMessage());
        return modelAndView;
    }

    @ExceptionHandler(value = AppException.class)
    public ModelAndView handlingAppException(AppException exception) {
        ErrorCode errorCode = exception.getErrorCode();
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("error/exception");
        modelAndView.addObject("errorCode", errorCode);
        return modelAndView;
    }

    @ExceptionHandler(value = WebException.class)
    public ModelAndView handlingWebException(WebException exception) {
        ErrorCode errorCode = exception.getErrorCode();
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("error/exception");
        modelAndView.addObject("errorCode", errorCode);
        return modelAndView;
    }
}
