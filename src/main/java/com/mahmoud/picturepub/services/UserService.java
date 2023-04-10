package com.mahmoud.picturepub.services;

import com.mahmoud.picturepub.data.entities.AppUser;
import org.springframework.security.core.userdetails.UserDetailsService;


public interface UserService extends UserDetailsService {
    void save(AppUser appUser);
    void saveToDefaults(AppUser appUser);
}
