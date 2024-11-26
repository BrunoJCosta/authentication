package br.com.login.controller;

import br.com.login.users.imutavel.EntityUserImmutableService;
import br.com.login.configuration.token.TokenForm;
import br.com.login.configuration.token.TokenService;
import br.com.login.users.LoginDTO;
import br.com.login.users.LoginFom;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
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
        try {
            LoginDTO loginDTO = userServices.findByEmailUserDTO(fom.email(), fom.password());
            return ResponseEntity.ok(loginDTO);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PostMapping("/refresh")
    public ResponseEntity<LoginDTO> refreshToken(@RequestBody TokenForm form) throws Exception {
        LoginDTO loginDTO = tokenService.verifyToken(form);
        return ResponseEntity.ok(loginDTO);
    }

}