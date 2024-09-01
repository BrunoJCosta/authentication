package br.com.login.controller;

import br.com.login.users.EntityUserServices;
import br.com.login.users.LoginFom;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthenticationController {

    private final EntityUserServices userServices;

    @PostMapping
    public ResponseEntity<String> login(@RequestBody LoginFom fom) throws Exception {
        userServices.findByEmailUserDTO(fom.getEmail(), fom.getPassword());
        return ResponseEntity.ok("");
    }

}
