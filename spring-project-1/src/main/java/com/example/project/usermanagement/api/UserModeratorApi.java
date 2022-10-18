package com.example.project.usermanagement.api;

import javax.annotation.security.RolesAllowed;
import javax.validation.Valid;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.project.usermanagement.model.Role;
import com.example.project.usermanagement.service.UserService;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@Tag(name = "UserModerator")
@RestController
@RequestMapping(path = "api/moderator/user")
@RolesAllowed(Role.MODERATOR)
@RequiredArgsConstructor
public class UserModeratorApi {

    private final UserService userService;

    @PostMapping
    public UserView create(@RequestBody @Valid final CreateUserRequest request) {
        return userService.create(request);
    }

    @PutMapping("{id}")
    public UserView update(@PathVariable final Long id, @RequestBody @Valid final UpdateUserRequest request) {
        return userService.update(id, request);
    }

    @DeleteMapping("{id}")
    public UserView delete(@PathVariable final Long id) {
        return userService.delete(id);
    }

    @GetMapping("{id}")
    public UserView get(@PathVariable final Long id) {
        return userService.getUser(id);
    }

    /*
     * @PostMapping("search") public ListResponse<UserView> search(@RequestBody
     * final SearchRequest<SearchUsersQuery> request) { return new
     * ListResponse<UserView>(userService.searchUsers(request.page(),
     * request.query())); }
     */
}
