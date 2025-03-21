package br.com.login.controller;

import br.com.login.configuration.handler.Response;
import br.com.login.users.imutavel.EntityUserImmutableService;
import br.com.login.configuration.token.TokenForm;
import br.com.login.configuration.token.TokenService;
import br.com.login.users.LoginDTO;
import br.com.login.users.LoginFom;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
@RequestMapping("/authentication")
@RequiredArgsConstructor
public class AuthenticationController {

    private final EntityUserImmutableService userServices;
    private final TokenService tokenService;

    @PostMapping("/auth")
    public ResponseEntity<LoginDTO> login(@RequestBody LoginFom fom) throws Exception {
        LoginDTO loginDTO = userServices.findByEmailUserDTO(fom.email(), fom.password());
        return ResponseEntity.ok(loginDTO);
    }

    @PostMapping("/refresh")
    public ResponseEntity<Response> refreshToken(@RequestBody TokenForm form) throws Exception {
        LoginDTO loginDTO = tokenService.verifyToken(form);
        return ResponseEntity.ok(Response.ok(loginDTO));
    }

    @PutMapping("/logout")
    public ResponseEntity<?> logout() {
        SecurityContextHolder.getContext().setAuthentication(null);
        return ResponseEntity.noContent().build();
    }

}