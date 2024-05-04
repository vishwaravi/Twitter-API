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
        if(userid.equals(auth().getName())){
            return userRepo.findByUserId(auth().getName()).get();
        }
        else return null;
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

    public Boolean deleteUser(String userid){
        if(getUserData(userid)!=null){
            userRepo.delete(getUserData(userid));
            return true;
        }
        else return false;
    }

    static public Authentication auth(){
        return SecurityContextHolder.getContext().getAuthentication();
    } 
}