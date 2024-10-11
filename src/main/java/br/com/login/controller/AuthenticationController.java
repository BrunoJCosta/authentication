package br.com.login.controller;

import br.com.login.configuration.UserDTO;
import br.com.login.configuration.token.TokenService;
import br.com.login.users.EntityUserServices;
import br.com.login.users.LoginDTO;
import br.com.login.users.LoginFom;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthenticationController {

    private final EntityUserServices userServices;
    private final TokenService tokenService;

    @PostMapping
    public ResponseEntity<LoginDTO> login(@RequestBody LoginFom fom) throws Exception {
        try {
            LoginDTO loginDTO = userServices.findByEmailUserDTO(fom.email(), fom.password());
            return ResponseEntity.ok(loginDTO);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PostMapping("/refresh")
    public ResponseEntity<String> refreshToken(@RequestBody TokenForm form, Authentication authentication) throws Exception {
        tokenService.verifyToken(form, (UserDTO) authentication.getPrincipal());
        return ResponseEntity.ok("");
    }

}