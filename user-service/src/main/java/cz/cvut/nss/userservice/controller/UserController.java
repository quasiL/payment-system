package cz.cvut.nss.userservice.controller;

import cz.cvut.nss.userservice.model.AuthenticationResponse;
import cz.cvut.nss.userservice.model.ChangeRequest;
import cz.cvut.nss.userservice.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/api/v1/user")
public class UserController {

    private final UserService userService;

    @PostMapping("/avatar")
    public ResponseEntity<AuthenticationResponse> changeAvatar(@RequestBody ChangeRequest request) {
        userService.changeAvatar(request);
        log.info("User avatar has been changed");
        return ResponseEntity.ok(AuthenticationResponse.builder().build());
    }
}
