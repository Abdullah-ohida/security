package com.example.security.web.payloads;

import com.sun.istack.NotNull;
import lombok.Data;

@Data
public class LoginRequest {
    @NotNull
    private String usernameOrEmail;

    @NotNull
    private String password;
}
