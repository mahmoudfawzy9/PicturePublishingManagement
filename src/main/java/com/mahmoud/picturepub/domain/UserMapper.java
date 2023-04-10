package com.mahmoud.picturepub.domain;


import com.mahmoud.picturepub.data.entities.AppUser;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {

    public AppUser toUser(UserDTO input){
        AppUser appUser =new AppUser();
        appUser.setUsername(input.getUsername());
        appUser.setPassword(input.getPassword());
        appUser.setEmail(input.getEmail());
        return appUser;
    }

}
