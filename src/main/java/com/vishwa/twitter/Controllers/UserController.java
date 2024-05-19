package com.vishwa.twitter.Controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.vishwa.twitter.Dto.UserDto;
import com.vishwa.twitter.Entities.FollowingEntity;
import com.vishwa.twitter.Entities.UserEntity;
import com.vishwa.twitter.Services.FollowService;
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
    @Autowired
    private FollowService followService;
    
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

    @GetMapping("/{userid}")
    ResponseEntity<?> getUserDetails(@PathVariable String userid){
        UserEntity user = userService.getUserData(userid);
        if(user!=null){
            if(user.getUserId().equals(auth().getName())){
                user.setUserPasswd("[protected]");
                return new ResponseEntity<>(user,HttpStatus.OK);
            }
            else{
                UserDto userDto = UserDto.builder()
                    .userId(user.getUserId()).userName(user.getUserName())
                    .profileUrl(user.getProfileUrl()).bannerUrl(user.getBannerUrl())
                    .followers(user.getFollowers()).following(user.getFollowing())
                    .build();
                return new ResponseEntity<>(userDto,HttpStatus.OK);
            }
        }
        else return new ResponseEntity<>("{\"status\":\"User Not Found\"}",HttpStatus.BAD_REQUEST);
    }

    @DeleteMapping("/{userid}")
    ResponseEntity<?> deleteUser(@PathVariable String userid,@RequestBody UserEntity user){
        if(passwordEncoder.matches(user.getUserPasswd(),userService.getUserData(userid).getUserPasswd())){
            if(userService.deleteUser(userid)){
                return new ResponseEntity<>("{\"status\":\"User Deleted Successfully.\"}",HttpStatus.OK);
            }
            else return new ResponseEntity<>("{\"status\":\"You are not authorized to access this resource\"}",HttpStatus.BAD_REQUEST);
        }
        else return new ResponseEntity<>("{\"status\":\"Invalid Password\"}",HttpStatus.BAD_REQUEST);  
    }

    @PutMapping("/{userId}/follow")
    ResponseEntity<?> followUser(@PathVariable String userId){
        FollowingEntity follow = followService.followUser(userId);
        if(follow != null){
            return new ResponseEntity<>(follow,HttpStatus.OK);
        }
        return new ResponseEntity<>("{\"status\":\"Something Went Wrong\"}",HttpStatus.BAD_REQUEST);
    }

    @PutMapping("/{userId}/unfollow")
    ResponseEntity<?> unfollowUser(@PathVariable String userId){
        boolean status = followService.unfollowUser(userId);
        if (status)
            return new ResponseEntity<>("{\"status\":\"Unfollowed\"}",HttpStatus.OK);
        else return new ResponseEntity<>("{\"status\":\"Something Went Wrong\"}",HttpStatus.BAD_REQUEST);
    }

    static public Authentication auth(){
        return SecurityContextHolder.getContext().getAuthentication();
    }
}