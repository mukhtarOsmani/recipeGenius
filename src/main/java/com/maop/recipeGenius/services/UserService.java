package com.maop.recipeGenius.services;

import com.maop.recipeGenius.entity.User;
import com.maop.recipeGenius.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User registerUser(User user) {

        user.setRole("USER");
        return userRepository.save(user);
    }

}