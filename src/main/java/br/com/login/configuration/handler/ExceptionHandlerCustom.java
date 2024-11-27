package br.com.login.configuration.handler;

import br.com.login.exception.IsEmptyException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class ExceptionHandlerCustom {


    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    @ExceptionHandler(IsEmptyException.class)
    public Response isEmpty(IsEmptyException exception) {
        return new Response(exception.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ResponseStatus(HttpStatus.CONFLICT)
    @ResponseBody
    @ExceptionHandler(IsEmptyException.class)
    public Response AlreadyExists(IsEmptyException exception) {
        return new Response(exception.getMessage(), HttpStatus.CONFLICT);
    }

}
