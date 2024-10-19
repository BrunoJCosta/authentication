package br.com.login.controller;

import br.com.login.users.EntityUserServices;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin
@RestController
@RequestMapping
@RequiredArgsConstructor
public class UserController {

    private EntityUserServices userServices;

}


