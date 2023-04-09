package com.mahmoud.picturepub.utils;

import com.mahmoud.picturepub.entity.User;
import com.mahmoud.picturepub.request.UserDTO;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {

    public User toUser(UserDTO input){
        User appUser =new User();
        appUser.setUserName(input.getUsername());
        appUser.setPassword(input.getPassword());
        return appUser;
    }
}
