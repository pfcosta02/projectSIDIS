package com.example.project.usermanagement.api;

import java.util.HashSet;
import java.util.Set;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

import lombok.Data;

@Data
public class CreateUserRequest {
    @NotBlank
    @Email
    String username;

    @NotBlank
    String fullName;

    @NotBlank
    String password;

    @NotBlank
    String rePassword;

    Set<String> authorities = new HashSet<>();
}
