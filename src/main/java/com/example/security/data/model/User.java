package com.example.security.data.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.HashSet;
import java.util.Set;

@Data
@Entity
@RequiredArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String firstName;

    @Column(nullable = false)
    @NotBlank
    private String lastName;

    @Column(unique = true)
    @NotBlank
    private String username;

    @Column(unique = true)
    @NotBlank
    private String email;

    @Column(unique = true)
    @NotBlank
    private String password;

    @ManyToMany
    Set<Role> roles = new HashSet<>();

    public User(String name, String username, String email, String password) {
        this.lastName = name;
        this.username = username;
        this.email = email;
        this.password = password;
    }
}
