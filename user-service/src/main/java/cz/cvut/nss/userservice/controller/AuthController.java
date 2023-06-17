package cz.cvut.nss.userservice.controller;

import cz.cvut.nss.userservice.model.AuthenticationRequest;
import cz.cvut.nss.userservice.model.AuthenticationResponse;
import cz.cvut.nss.userservice.model.ChangeRequest;
import cz.cvut.nss.userservice.model.RegisterRequest;
import cz.cvut.nss.userservice.service.AuthenticationService;
import cz.cvut.nss.userservice.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthenticationService service;
    private final UserService userService;

    @PostMapping("/avatar")
    public ResponseEntity<String> changeAvatar(@RequestBody ChangeRequest request) {
        userService.changeAvatar(request);
        return ResponseEntity.ok("Avatar was changed");
    }

    @PostMapping("/register")
    public ResponseEntity<AuthenticationResponse> register(
            @RequestBody RegisterRequest request
    ) {
        return ResponseEntity.ok(service.register(request));
    }

    @PostMapping("/authenticate")
    public ResponseEntity<AuthenticationResponse> authenticate(
            @RequestBody AuthenticationRequest request
    ) {
        return ResponseEntity.ok(service.authenticate(request));
    }
}
