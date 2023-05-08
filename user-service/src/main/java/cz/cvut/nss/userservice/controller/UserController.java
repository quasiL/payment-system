package cz.cvut.nss.userservice.controller;

import cz.cvut.nss.userservice.model.User;
import cz.cvut.nss.userservice.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
public class UserController {

    private UserRepository userRepository;

    @GetMapping("/hello")
    public String helloWorld() {
        return "Hello, World!";
    }

    @GetMapping("/users")
    public List<User> getUsers() {
        return userRepository.findAll();
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/add")
    public void createUser(@RequestBody User user) {
        userRepository.save(user);
    }
}
