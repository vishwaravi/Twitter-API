package com.vishwa.twitter.Controllers;

import org.springframework.web.bind.annotation.RestController;

import com.vishwa.twitter.Entities.UserEntity;
import com.vishwa.twitter.Services.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/createUser")
    UserEntity addUser(@RequestBody UserEntity user){
        return userService.saveUserData(user);
    }
    
}
