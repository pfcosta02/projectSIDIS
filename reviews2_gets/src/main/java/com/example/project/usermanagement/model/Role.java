package com.example.project.usermanagement.model;

import lombok.AllArgsConstructor;
import lombok.Value;
import org.springframework.security.core.GrantedAuthority;

@Value
@AllArgsConstructor
public class Role implements GrantedAuthority {

    private static final long serialVersionUID = 1L;

    public static final String ADMIN = "ADMIN";
    public static final String MODERATOR = "MODERATOR";
    public static final String ANONYMOUS = "ANONYMOUS";
    public static final String CUSTOMER = "CUSTOMER";

    private String authority;
}
