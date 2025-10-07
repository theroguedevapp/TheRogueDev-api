package br.com.theroguedev.api.config;

import br.com.theroguedev.api.exceptions.CustomBadRequestException;
import br.com.theroguedev.api.exceptions.CustomNotFoundException;
import br.com.theroguedev.api.exceptions.UnauthorizedException;
import br.com.theroguedev.api.exceptions.UsernameOrPasswordInvalidException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ApplicationControllerAdvice {

    @ExceptionHandler(UnauthorizedException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public String handleUnauthorizedException(UnauthorizedException exception) {
        return exception.getMessage();
    }


    @ExceptionHandler(UsernameOrPasswordInvalidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String handleUsernameOrPasswordInvalidException(UsernameOrPasswordInvalidException exception) {
        return exception.getMessage();
    }

    @ExceptionHandler(CustomBadRequestException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String handleCustomBadRequestException(CustomBadRequestException exception) {
        return exception.getMessage();
    }

    @ExceptionHandler(CustomNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public String handleCustomNotFoundException(CustomNotFoundException exception) {
        return exception.getMessage();
    }


}
