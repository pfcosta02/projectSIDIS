package com.example.project.auth.api;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;

import lombok.Data;

@Data
public class AuthRequest {
    @NotNull
    @Email
    String username;
    @NotNull
    String password;
}
