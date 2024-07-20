package br.com.login.controller;

import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<String> testPost() {
        return ResponseEntity.ok("test post");
    }

}

