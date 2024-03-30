package com.vishwa.twitter.Services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.vishwa.twitter.Config.Time.TimeStamp;
import com.vishwa.twitter.Entities.UserEntity;
import com.vishwa.twitter.Repositories.UserRepo;

@Service
public class UserService {

    @Autowired
    private UserRepo userRepo;

    public UserEntity saveUserData(UserEntity user){

        user.setTimeStamp(TimeStamp.getTStamp());
        return userRepo.save(user);
    }

    public Optional<UserEntity> findUserId(String userId){
        return userRepo.findByUserId(userId);
    }

    public boolean checkPasswd(UserEntity user,String userPasswd){
        return user.getUserPasswd().equals(userPasswd);
    }
}
