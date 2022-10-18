package com.example.project.usermanagement.api;

import java.util.List;

import org.mapstruct.Mapper;
import org.springframework.beans.factory.annotation.Autowired;

import com.example.project.usermanagement.model.User;
import com.example.project.usermanagement.repositories.UserRepository;

/**
 * Based on https://github.com/Yoh0xFF/java-spring-security-example
 *
 */
@Mapper(componentModel = "spring")
public abstract class UserViewMapper {

    @Autowired
    private UserRepository userRepo;

    public abstract UserView toUserView(User user);

    public abstract List<UserView> toUserView(List<User> users);

    public UserView toUserViewById(final Long id) {
        if (id == null) {
            return null;
        }
        return toUserView(userRepo.getById(id));
    }

}
