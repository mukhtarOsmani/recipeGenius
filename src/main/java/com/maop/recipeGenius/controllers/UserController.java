package com.maop.recipeGenius.controllers;

import com.maop.recipeGenius.entity.User;
import com.maop.recipeGenius.services.UserService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/register")
    public User register(@RequestBody User user) {
        return userService.registerUser(user);
    }

    @PutMapping("/profile")
    public User updateProfile(@AuthenticationPrincipal User currentUser, @RequestBody User updatedUser) {
        return userService.updateProfile(currentUser.getId(), updatedUser);
    }

    @GetMapping("/profile")
    public User getProfile(@AuthenticationPrincipal User currentUser) {
        return userService.findByEmail(currentUser.getEmail());
    }


}