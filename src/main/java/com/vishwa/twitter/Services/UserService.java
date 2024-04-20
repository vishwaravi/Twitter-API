package com.vishwa.twitter.Services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.vishwa.twitter.Config.Time.TimeStamp;
import com.vishwa.twitter.Entities.UserEntity;
import com.vishwa.twitter.Repositories.UserRepo;

@Service
public class UserService implements UserDetailsService{
    @Autowired
    private UserRepo userRepo;
    public UserEntity saveUserData(UserEntity user){
        user.setTimeStamp(TimeStamp.getTStamp());
        return userRepo.save(user);
    }
    public UserEntity getUserData(String userid){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String curUserId = authentication.getName();
        if(userid!=null&&userid.equals(curUserId)){
            return userRepo.findByUserId(curUserId).get();
        }
        else{
            return null;
        }
    }

    @Override
    public UserDetails loadUserByUsername(String userId) throws UsernameNotFoundException {
        Optional<UserEntity> user = userRepo.findByUserId(userId);
        if(user.isPresent()){
            var userobj = user.get();
            return User.builder()
                        .username(userobj.getUserId())
                        .password(userobj.getUserPasswd())
                        .build();
        } else{
            throw new UsernameNotFoundException(userId);
        }
    }
}
