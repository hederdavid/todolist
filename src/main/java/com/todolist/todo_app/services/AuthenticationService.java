package com.todolist.todo_app.services;

import com.todolist.todo_app.domain.user.AuthenticationDTO;
import com.todolist.todo_app.domain.user.LoginResponseDTO;
import com.todolist.todo_app.domain.user.RegisterDTO;
import com.todolist.todo_app.domain.user.User;
import com.todolist.todo_app.infra.security.TokenService;
import com.todolist.todo_app.repositories.UserRepository;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationService {

    private final AuthenticationManager authenticationManager;

    private final UserRepository userRepository;

    private final TokenService tokenService;


    public AuthenticationService(AuthenticationManager authenticationManager, UserRepository userRepository, TokenService tokenService) {
        this.authenticationManager = authenticationManager;
        this.userRepository = userRepository;
        this.tokenService = tokenService;
    }

    public ResponseEntity<LoginResponseDTO> authUser(AuthenticationDTO data) {
        UsernamePasswordAuthenticationToken usernamePassword = new UsernamePasswordAuthenticationToken(data.username(),
                data.password());
        Authentication authenticate = this.authenticationManager.authenticate(usernamePassword);
        String token = tokenService.generateToken( (User) authenticate.getPrincipal());
        return ResponseEntity.ok(new LoginResponseDTO(token));
    }

    public ResponseEntity<String> registerUser(@Valid RegisterDTO data) {
        System.out.println(data);
        if (this.userRepository.findByUsername(data.username()) != null) {
            return ResponseEntity.badRequest().build();
        }

        String encryptedPassword = new BCryptPasswordEncoder().encode(data.password());
        User newUser = new User(data.username(), encryptedPassword);
        userRepository.save(newUser);
        return ResponseEntity.ok("User registered successfully");
    }
}
