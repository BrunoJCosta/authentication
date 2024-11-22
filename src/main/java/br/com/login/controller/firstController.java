package br.com.login.controller;

import br.com.login.configuration.UserDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/test")
public class firstController {

    @GetMapping
    public ResponseEntity<String> testGet() {
        return ResponseEntity.ok("test get");
    }

    @PostMapping
    @PreAuthorize("hasAuthority('USER_ADMIN')")
    public ResponseEntity<String> testPost(Authentication authentication) {
        UserDTO principal = (UserDTO) authentication.getPrincipal();
        return ResponseEntity.ok("test post, user: " + principal.getUsername());
    }

}

