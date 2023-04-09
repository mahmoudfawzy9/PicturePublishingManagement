package com.mahmoud.picturepub.service;

import com.mahmoud.picturepub.entity.Role;
import com.mahmoud.picturepub.entity.User;
import com.mahmoud.picturepub.exception.UserNotFoundException;
import com.mahmoud.picturepub.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;


@Slf4j
@Service
public class UserServiceImp implements UserService{

    private final UserRepository userRepository;

    private PasswordEncoder bCryptPasswordEncoder;

    @Autowired
    public UserServiceImp(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.bCryptPasswordEncoder = passwordEncoder;
    }

    public UserServiceImp(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public User registerUser(String name, String email, String password) {
        User user = new User();
        user.setUserName(name);
        user.setEmail(email);
        user.setPassword(password);
        return userRepository.save(user);
    }

    @Override
    public Optional<User> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    public void save(User appUser) {
        //todo: check authorities
        appUser.setPassword(bCryptPasswordEncoder.encode(appUser.getPassword()));

        appUser.grantAuthority(Role.USER);

        userRepository.save(appUser);
    }

    @Override
    public void saveToDefaults(User appUser) {
        appUser.setPassword(bCryptPasswordEncoder.encode(appUser.getPassword()));

        appUser.grantAuthority(Role.USER);

        userRepository.save(appUser);

    }

    @Override
    public User getUserById(Long id) {
        return userRepository.findById(id).orElseThrow(() ->
                new ResourceNotFoundException("User with id " +id + " is not found."));
    }

    @Override
    public User createUser(User user) {
        return userRepository.save(user);
    }

    @Override
    public User updateUserById(Long id, User updatedUser) {
        {
            Optional<User> optionalUser = userRepository.findById(id);

            if (optionalUser.isPresent()) {
                User user = optionalUser.get();
                user.setUserName(updatedUser.getUserName());
                user.setEmail(updatedUser.getEmail());
                user.setPassword(updatedUser.getPassword());
                user.setUpdatedAt(LocalDateTime.now());

                    return userRepository.save(user);
            } else {
                throw new UserNotFoundException("User with ID " + id + " not found");
            }
        }
    }

    @Override
    public void deleteUserById(Long id) {
        userRepository.deleteById(id);
    }

    @Transactional(readOnly = true)
    @Override
    public UserDetails loadUserByUsername(String username) throws UserNotFoundException {
        log.info("find by username "+username);

        return (UserDetails) userRepository.findByUserName(username);

    }
}
