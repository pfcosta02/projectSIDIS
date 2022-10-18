package com.example.project.usermanagement.api;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class SearchUsersQuery {
    String id;
    String username;
    String fullName;
}
