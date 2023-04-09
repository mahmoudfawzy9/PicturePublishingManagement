package com.mahmoud.picturepub.service;

public interface SecurityService {
    boolean isAuthenticated();
    void autoLogin(String username, String password);
    UserService userService();
}