package br.com.login.configuration.handler;

import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

@Getter
@Setter
@SuppressWarnings("unused")
public class Response {

    private Object response;
    private final String message;
    private final String statusCode;
    private final int code;
    private String url;


    private Response(String message, HttpStatus httpStatus) {
        this.message = message;
        this.code = httpStatus.value();
        this.statusCode = httpStatus.name();
    }

    private Response(HttpStatus httpStatus, Object response) {
        this.message = "";
        this.code = httpStatus.value();
        this.statusCode = httpStatus.name();
        this.response = response;
    }

    private Response(HttpStatus httpStatus, Object response, String url) {
        this.message = "";
        this.code = httpStatus.value();
        this.statusCode = httpStatus.name();
        this.response = response;
        this.url = url;
    }

    public static Response forbidden() {
        return new Response("Credentials invalid", HttpStatus.FORBIDDEN);
    }

    public static Response unauthorized() {
        return new Response("Credentials Unauthorized", HttpStatus.UNAUTHORIZED);
    }

    public static Response ok(Object response) {
        return new Response(HttpStatus.OK, response);
    }

    public static Response badRequest(Object mensagem) {
        return new Response(HttpStatus.BAD_REQUEST, mensagem);
    }

    public static Response created(Object response, String uri) {
        return new Response(HttpStatus.CREATED, response, uri);
    }

    public static Response conflict(String mensagem) {
        return new Response(mensagem, HttpStatus.CONFLICT);
    }

}
