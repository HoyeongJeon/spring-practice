package com.group.libraryapp.controller.user;

import com.group.libraryapp.dto.user.request.UserCreateRequestDTO;
import com.group.libraryapp.dto.user.request.UserUpdateRequestDTO;
import com.group.libraryapp.dto.user.response.UserResponse;
import com.group.libraryapp.service.user.UserServiceV1;
import com.group.libraryapp.service.user.UserServiceV2;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("user")
public class UserController {

    private final UserServiceV1 userServiceV1;
    private final UserServiceV2 userServiceV2;

    public UserController(UserServiceV1 userServiceV1, UserServiceV2 userServiceV2) {
        this.userServiceV1 = userServiceV1;
        this.userServiceV2 = userServiceV2;
    }

    @PostMapping()
    public void saveUser(@RequestBody UserCreateRequestDTO request) {
        userServiceV2.saveUser(request);
    }

    @GetMapping()
    public List<UserResponse> getUsers() {
        return userServiceV2.getUsers();
    }

    @PutMapping()
    public void updateUser(@RequestBody UserUpdateRequestDTO request) {
        userServiceV2.updateUser(request);
    }

    @DeleteMapping()
    public void deleteUser(@RequestParam String name) {
        userServiceV2.deleteUser(name);
    }
}