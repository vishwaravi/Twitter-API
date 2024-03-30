package com.vishwa.twitter.Controllers;

import org.springframework.web.bind.annotation.RestController;

import com.vishwa.twitter.Entities.UserEntity;
import com.vishwa.twitter.Services.UserService;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;


@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/createuser")
    ResponseEntity<?> createUser(@RequestBody UserEntity user){
        try{
            UserEntity newUser = userService.saveUserData(user);
            return new ResponseEntity<>(newUser,HttpStatus.CREATED);
        }
        catch(DataIntegrityViolationException e){
            return new ResponseEntity<>("{\"status\":\"User Name Already Taken\"}",HttpStatus.BAD_REQUEST);
        }
    }
    
    @PostMapping("/login")
    ResponseEntity<String> loginUser(@RequestBody UserEntity userLogin){
        Optional<UserEntity> userStatus = userService.findUserId(userLogin.getUserId());
        if(userStatus.isPresent()){
            UserEntity user = userStatus.get();
            if (userService.checkPasswd(user,userLogin.getUserPasswd())){
                return ResponseEntity.ok("Login SuccessFul");
            }
            else return ResponseEntity.badRequest().body("Invalid Password");  
        }
        return ResponseEntity.badRequest().body("UserId Not Found");
    }
}
