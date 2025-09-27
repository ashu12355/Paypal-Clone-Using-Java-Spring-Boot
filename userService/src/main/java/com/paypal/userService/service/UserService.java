package com.paypal.userService.service;

import com.paypal.userService.entity.User;

import java.util.List;
import java.util.Optional;

public interface UserService {
    User createUser(User user);

    Optional<User> getUserById(Long id);

    List<User> getAllUsers();


}
