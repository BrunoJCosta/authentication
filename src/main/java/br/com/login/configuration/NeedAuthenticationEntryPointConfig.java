package br.com.login.configuration;

import br.com.login.configuration.handler.Response;
import br.com.login.utils.StringUtils;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
@Primary
@RequiredArgsConstructor
public class NeedAuthenticationEntryPointConfig implements AuthenticationEntryPoint {

    private final ObjectMapper objectMapper;


    @Override
    public void commence(HttpServletRequest request,
                         HttpServletResponse response,
                         AuthenticationException authException) throws IOException {
        String authorization = request.getHeader("Authorization");
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        if (authorizationIsNull(authorization)) {
            forbidden(response);
            return;
        }
        unauthorized(response);
    }

    private boolean authorizationIsNull(String authorization) {
        return StringUtils.empty(authorization) || authorization.contains("Bearer null");
    }

    private void unauthorized(HttpServletResponse response) throws IOException {
        Response unauthorized = Response.unauthorized();
        response.setStatus(unauthorized.getCode());
        objectMapper.writeValue(response.getWriter(), unauthorized);
    }

    private void forbidden(HttpServletResponse response) throws IOException {
        Response forbidden = Response.forbidden();
        response.setStatus(forbidden.getCode());
        objectMapper.writeValue(response.getWriter(), forbidden);
    }


}
