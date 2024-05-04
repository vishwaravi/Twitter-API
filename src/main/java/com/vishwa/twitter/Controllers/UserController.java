package com.vishwa.twitter.Controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.vishwa.twitter.Entities.UserEntity;
import com.vishwa.twitter.Services.UserService;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;


@RestController
public class UserController {

    @Autowired
    private UserService userService;
    @Autowired
    private PasswordEncoder passwordEncoder;
    
    @PostMapping("/register")
    ResponseEntity<?> registerUser(@RequestBody UserEntity user){
        try{
            user.setUserPasswd(passwordEncoder.encode(user.getUserPasswd()));
            UserEntity newUser = userService.saveUserData(user);
            newUser.setUserPasswd("[protected]");
            return new ResponseEntity<>(newUser,HttpStatus.CREATED);
        }
        catch(DataIntegrityViolationException e){
            return new ResponseEntity<>("{\"status\":\"User Name Already Taken\"}",HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/users/{userid}")
    ResponseEntity<?> getUserDetails(@PathVariable String userid){
        if(userService.getUserData(userid)!=null){
            UserEntity res = userService.getUserData(userid);
            res.setId(null);
            res.setUserPasswd("[protected]");
            return new ResponseEntity<>(res,HttpStatus.OK);
        }
        else return new ResponseEntity<>("{\"status\":\"You are not authorized to access this resource\"}",HttpStatus.BAD_REQUEST);
    }

    @DeleteMapping("/users/{userid}")
    ResponseEntity<?> deleteUser(@PathVariable String userid,@RequestBody UserEntity user){
        System.out.println(user.getUserPasswd());
        if(passwordEncoder.matches(user.getUserPasswd(),userService.getUserData(userid).getUserPasswd())){
            if(userService.deleteUser(userid)){
                return new ResponseEntity<>("{\"status\":\"User Deleted Successfully.\"}",HttpStatus.OK);
            }
            else return new ResponseEntity<>("{\"status\":\"You are not authorized to access this resource\"}",HttpStatus.BAD_REQUEST);
        }
        else return new ResponseEntity<>("{\"status\":\"Invalid Password\"}",HttpStatus.BAD_REQUEST);  
    }
}