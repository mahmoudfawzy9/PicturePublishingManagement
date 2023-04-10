package com.mahmoud.picturepub.services;

public interface SecurityService {
    boolean isAuthenticated();
    void autoLogin(String username, String password);
    UserService userService();
}
