package com.mahmoud.picturepub.service;

import com.mahmoud.picturepub.entity.User;

import java.util.Optional;

public interface UserService {

     User registerUser(String name, String email, String password);

     Optional<User> findByEmail(String email);

     User getUserById(Long id);

     User createUser(User user);

     User updateUserById(Long id, User user);

     void deleteUserById(Long id);
}
