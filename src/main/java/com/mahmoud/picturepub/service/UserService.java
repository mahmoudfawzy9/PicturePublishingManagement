package com.mahmoud.picturepub.service;

import com.mahmoud.picturepub.entity.User;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.Optional;

public interface UserService extends UserDetailsService {

     User registerUser(String name, String email, String password);

     Optional<User> findByEmail(String email);

     void save(User appUser);
     void saveToDefaults(User appUser);

     User getUserById(Long id);

     User createUser(User user);

     User updateUserById(Long id, User user);

     void deleteUserById(Long id);
}
