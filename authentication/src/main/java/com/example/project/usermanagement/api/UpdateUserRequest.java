package com.example.project.usermanagement.api;

import java.util.Set;

import javax.validation.constraints.NotBlank;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UpdateUserRequest {
    @NotBlank
    String fullName;

    Set<String> authorities;
}
