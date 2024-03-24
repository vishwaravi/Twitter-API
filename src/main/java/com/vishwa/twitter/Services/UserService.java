package com.vishwa.twitter.Services;

import com.vishwa.twitter.MyLibs.Time.TimeStamp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.vishwa.twitter.Entities.UserEntity;
import com.vishwa.twitter.Repositories.UserRepo;

@Service
public class UserService {

    @Autowired
    private UserRepo userRepo;

    public UserEntity saveUserData(UserEntity user){

        user.setTime_stamp(TimeStamp.getTStamp());
        return userRepo.save(user);
    }

}
