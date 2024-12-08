package br.com.login.configuration.handler;

import br.com.login.exception.AlreadyExistsException;
import br.com.login.exception.BadRequestException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class ExceptionHandlerCustom {

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    @ExceptionHandler(BadRequestException.class)
    public Response badRequest(BadRequestException exception) {
        return new Response(exception.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ResponseStatus(HttpStatus.CONFLICT)
    @ResponseBody
    @ExceptionHandler(AlreadyExistsException.class)
    public Response AlreadyExists(AlreadyExistsException exception) {
        return new Response(exception.getMessage(), HttpStatus.CONFLICT);
    }

}
