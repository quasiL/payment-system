package cz.cvut.nss.userservice.service;

import cz.cvut.nss.userservice.model.*;
import cz.cvut.nss.userservice.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthenticationService {

    private final UserRepository repository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final KafkaMessageProducer kafkaMessageProducer;

    @Value("${us.basic-avatar-url}")
    private String avatarUrl;

    /**
     * Main authentication method. Checks if the user exists and generate new JWT token for him
     * @param request contains user's email and password
     * @return AuthenticationResponse entity with a new JWT token and information about this user
     */
    public AuthenticationResponse authenticate(AuthenticationRequest request) {

        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );

        var user = repository.findByEmail(request.getEmail())
                .orElseThrow();
        var jwtToken = jwtService.generateToken(user);
        return AuthenticationResponse.builder()
                .id(user.getId())
                .token(jwtToken)
                .firstname(user.getFirstName())
                .lastname(user.getLastName())
                .email(user.getEmail())
                .avatar(user.getAvatar())
                .build();
    }

    /**
     * Main registration method. Checks if such user does not exist, registers a new user
     * and generate a new JWT token for him
     * @param request contains information about the new user
     * @return AuthenticationResponse entity with new JWT token
     */
    public AuthenticationResponse register(RegisterRequest request) {
        if (repository.findByEmail(request.getEmail()).isPresent()) {
            throw new NoSuchElementException("User already exists");
        }

        var user = User.builder()
                .firstName(request.getFirstname())
                .lastName(request.getLastname())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(Role.USER)
                .avatar(avatarUrl)
                .build();
        repository.save(user);
        log.info("New User with ID {} was created", user.getId());

        kafkaMessageProducer.sendMessage("user-topic", new KafkaMessage(user.getId()));

        var jwtToken = jwtService.generateToken(user);
        return AuthenticationResponse.builder()
                .token(jwtToken)
                .build();
    }
}
