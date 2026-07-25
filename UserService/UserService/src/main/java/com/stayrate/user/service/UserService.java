package com.stayrate.user.service;

import com.stayrate.user.entity.User;
import java.util.List;

public interface UserService {

    // Create
    User saveUser(User user);

    // Read All
    List<User> getAllUser();

    // Read Single
    User getUser(String userId);

    // Update
    User updateUser(String userId, User user);

    // Delete
    void deleteUser(String userId);
}