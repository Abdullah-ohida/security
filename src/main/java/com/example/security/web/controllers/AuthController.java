package com.example.security.web.controllers;

import com.example.security.data.model.Role;
import com.example.security.data.model.RoleName;
import com.example.security.data.model.User;
import com.example.security.data.repository.RoleRepository;
import com.example.security.data.repository.UserRepository;
import com.example.security.web.exceptions.AppException;
import com.example.security.web.payloads.ApiResponse;
import com.example.security.web.payloads.JwtAuthenticationResponse;
import com.example.security.web.payloads.LoginRequest;
import com.example.security.web.payloads.RegisterRequest;
import com.example.security.web.security.JwtTokenProvider;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.Collections;

@RestController
@Slf4j
@RequestMapping("api/auth")
public class AuthController {

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    UserRepository userRepository;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    JwtTokenProvider tokenProvider;

    @PostMapping("/signin")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest){
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.getUsernameOrEmail(),
                        loginRequest.getPassword()
                )
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = tokenProvider.generateToken(authentication);
        return ResponseEntity.ok(new JwtAuthenticationResponse(jwt));
    }

    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@Valid @RequestBody RegisterRequest registerRequest) {
        log.info("Hit this end point");

        if (userRepository.existsByUsername(registerRequest.getUsername())) {
            return new ResponseEntity<>(new ApiResponse(false, "Username is already taken"), HttpStatus.CONFLICT);
        }
        if (userRepository.existsByEmail(registerRequest.getEmail())) {
            return new ResponseEntity<>(new ApiResponse(false, "Email is already in use!"), HttpStatus.CONFLICT);
        }
//     Create user account
        User user = new User(registerRequest.getName(), registerRequest.getUsername(), registerRequest.getEmail(), passwordEncoder.encode(registerRequest.getPassword()));

        log.info("User to be saved --> {}", user);


        Role userRole = roleRepository.findRoleByRoleName(RoleName.USER).orElseThrow(() -> new AppException("User role is not set"));

        user.setRoles(Collections.singleton(userRole));

        log.info("Added role to user --> {}", user);


        User result = userRepository.save(user);

        log.info("Saved user details --> {}", result);

        URI location = ServletUriComponentsBuilder
                .fromCurrentContextPath().path("/api/users/{username}")
                .buildAndExpand(result.getUsername()).toUri();

        log.info("user uri location --> {}", location);

        return ResponseEntity.created(location).body(new ApiResponse(true, "User registered successfully"));
    }

}
