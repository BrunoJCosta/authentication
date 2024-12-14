package br.com.login.controller;

import br.com.login.configuration.UserDTO;
import br.com.login.configuration.handler.Response;
import br.com.login.exception.UserNotFound;
import br.com.login.users.EntityUserServices;
import br.com.login.users.ProfileDTO;
import br.com.login.users.UserForm;
import br.com.login.users.UserListDTO;
import br.com.login.utils.RequestUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {

    private final EntityUserServices userServices;

    @GetMapping("/profile")
    public ResponseEntity<ProfileDTO> profile(Authentication authentication) throws UserNotFound {
        UserDTO principal = (UserDTO) authentication.getPrincipal();
        var profileDTO = userServices.detail(principal);
        return ResponseEntity.ok(profileDTO);
    }

    @PostMapping("/create")
    @PreAuthorize("hasAuthority('USER_ADMIN')")
    public ResponseEntity<?> createdUser(@RequestBody UserForm userForm, Authentication authentication) throws Exception {
        UserDTO principal = (UserDTO) authentication.getPrincipal();
        ProfileDTO created = userServices.created(userForm, principal);
        var uri = new URI(RequestUtils.PREFIX_URL + "/user/");
        return ResponseEntity.created(uri).body(created);
    }

    @GetMapping
    public ResponseEntity<Response> list(@PageableDefault Pageable pageable,
                                         @RequestParam(required = false) String name,
                                         @RequestParam(required = false) String permission,
                                         @RequestParam(required = false) String automatic,
                                         @RequestParam(required = false) String cpf,
                                         @RequestParam(required = false) String garden,
                                         @RequestParam(required = false) String email) {
        List<UserListDTO> dto = userServices.list(pageable, name, permission, automatic, cpf, garden, email);
        return ResponseEntity.ok(Response.ok(dto));
    }
}


