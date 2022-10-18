package com.example.project.usermanagement.service;

import java.util.Optional;

import javax.validation.ValidationException;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.project.usermanagement.api.CreateUserRequest;
import com.example.project.usermanagement.api.UpdateUserRequest;
import com.example.project.usermanagement.api.UserEditMapper;
import com.example.project.usermanagement.api.UserView;
import com.example.project.usermanagement.api.UserViewMapper;
import com.example.project.usermanagement.model.User;
import com.example.project.usermanagement.repositories.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserService implements UserDetailsService {

    private final UserRepository userRepo;
    private final UserEditMapper userEditMapper;
    private final UserViewMapper userViewMapper;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public UserView create(final CreateUserRequest request) {
        if (userRepo.findByUsername(request.getUsername()).isPresent()) {
            throw new ValidationException("Username exists!");
        }
        if (!request.getPassword().equals(request.getRePassword())) {
            throw new ValidationException("Passwords don't match!");
        }

        User user = userEditMapper.create(request);
        user.setPassword(passwordEncoder.encode(request.getPassword()));

        user = userRepo.save(user);

        return userViewMapper.toUserView(user);
    }

    @Transactional
    public UserView update(final Long id, final UpdateUserRequest request) {
        User user = userRepo.getById(id);
        userEditMapper.update(request, user);

        user = userRepo.save(user);

        return userViewMapper.toUserView(user);
    }

    @Transactional
    public UserView upsert(final CreateUserRequest request) {
        final Optional<User> optionalUser = userRepo.findByUsername(request.getUsername());

        if (optionalUser.isEmpty()) {
            return create(request);
        }
        final UpdateUserRequest updateUserRequest = new UpdateUserRequest(request.getFullName(),
                request.getAuthorities());
        return update(optionalUser.get().getId(), updateUserRequest);
    }

    @Transactional
    public UserView delete(final Long id) {
        User user = userRepo.getById(id);

        user.setUsername(user.getUsername().replace("@", String.format("_%s@", user.getId().toString())));
        user.setEnabled(false);
        user = userRepo.save(user);

        return userViewMapper.toUserView(user);
    }

    @Override
    public UserDetails loadUserByUsername(final String username) throws UsernameNotFoundException {
        return userRepo.findByUsername(username).orElseThrow(
                () -> new UsernameNotFoundException(String.format("User with username - %s, not found", username)));
    }

    public boolean usernameExists(final String username) {
        return userRepo.findByUsername(username).isPresent();
    }

    public UserView getUser(final Long id) {
        return userViewMapper.toUserView(userRepo.getById(id));
    }

    /*
     * public List<UserView> searchUsers(final Page page, final SearchUsersQuery
     * query) { final List<User> users = userRepo.searchUsers(page, query); return
     * userViewMapper.toUserView(users); }
     */
}
