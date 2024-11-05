package com.vishwa.twitter.Controllers;

import java.io.IOException;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.vishwa.twitter.Dto.RegisterDto;
import com.vishwa.twitter.Dto.UserDto;
import com.vishwa.twitter.Entities.FollowingEntity;
import com.vishwa.twitter.Entities.UserEntity;
import com.vishwa.twitter.Services.FileService;
import com.vishwa.twitter.Services.FollowService;
import com.vishwa.twitter.Services.UserService;
import com.vishwa.twitter.utils.ResObj;
import com.vishwa.twitter.utils.UploadStatus;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;


@RestController
public class UserController {

    @Autowired
    private UserService userService;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private FollowService followService;
    @Autowired
    private ResObj resObj;
    @Autowired
    FileService fileService;
    
    @PostMapping("/register")
    ResponseEntity<?> registerUser(@ModelAttribute RegisterDto user){
        List<String> profileUrls = fileService.uploadFileToCloud(user.getProfile(),"profile");
        List<String> bannerUrls = fileService.uploadFileToCloud(user.getBanner(),"profile");
        
        if(profileUrls == null){
            resObj.setStatus("profile : unkown file");
            return new ResponseEntity<>(resObj,HttpStatus.BAD_REQUEST);
        }
        else if(bannerUrls == null){
            resObj.setStatus("banner : unkown file");
            return new ResponseEntity<>(resObj,HttpStatus.BAD_REQUEST);
        }
        else {
            try{
                user.setUserPasswd(passwordEncoder.encode(user.getUserPasswd()));
                user.setProfileUrl(profileUrls.get(0));
                user.setProfilePubId(profileUrls.get(1));
                user.setBannerUrl(bannerUrls.get(0));
                user.setBannerPubId(bannerUrls.get(1));
                System.out.println("\n\n profile Pub Id :"+profileUrls.get(1)+"\n"
                +"Banner PubId :"+bannerUrls.get(1)+ "\n\n");

                UserEntity newUser = userService.saveUserData(user);
                newUser.setUserPasswd("[protected]");
                return new ResponseEntity<>(newUser,HttpStatus.CREATED);
            }
            catch(DataIntegrityViolationException e){
                resObj.setStatus("User Name Already Taken");
                return new ResponseEntity<>(resObj,HttpStatus.BAD_REQUEST);
            }
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
        else{
            resObj.setStatus("User Not Found");
            return new ResponseEntity<>(resObj,HttpStatus.BAD_REQUEST);
        } 
    }

    @PatchMapping("/{userid}")
    ResponseEntity<?> updateUser(@ModelAttribute RegisterDto user,@PathVariable String userid){
        UploadStatus profileUrls,bannerUrls;
        profileUrls = fileService.checkFileAndUpload(user.getProfile(),"profile");
        bannerUrls = fileService.checkFileAndUpload(user.getBanner(),"profile");

        if(profileUrls.getStatus().equals("uft")){
            resObj.setStatus("Profile : unkown file type");
            return new ResponseEntity<>(resObj,HttpStatus.BAD_REQUEST);
        }

        if(bannerUrls.getStatus().equals("uft")){
            resObj.setStatus("Banner : unkown file type");
            return new ResponseEntity<>(resObj,HttpStatus.BAD_REQUEST);
        }

        if(userid.equals(auth().getName())){
            if(!profileUrls.getUrls().isEmpty()){
                user.setProfileUrl(profileUrls.getUrls().get(0));
                user.setProfilePubId(profileUrls.getUrls().get(1));
            }
            else if (!bannerUrls.getUrls().isEmpty()) {
                user.setBannerUrl(bannerUrls.getUrls().get(0));
                user.setBannerPubId(bannerUrls.getUrls().get(1));
            }
            return new ResponseEntity<>(userService.updateUser(user),HttpStatus.OK);
        }
        else {
            resObj.setStatus("Something Went Wrong");
            return new ResponseEntity<>(resObj,HttpStatus.BAD_REQUEST);
        }
        
    }

    @DeleteMapping("/{userid}")
    ResponseEntity<?> deleteUser(@PathVariable String userid,@RequestParam("userPasswd") String passwd) throws IOException{
        if(passwordEncoder.matches(passwd,userService.getUserData(userid).getUserPasswd())){
            if(userService.deleteUser(userid)){
                resObj.setStatus("User Deleted Successfully.");
                return new ResponseEntity<>(resObj,HttpStatus.OK);
            }
            else{
                resObj.setStatus("Something Went Wrong");
                return new ResponseEntity<>(resObj,HttpStatus.BAD_REQUEST);
            }
        }
        else{
            resObj.setStatus("Invalid Password");
            return new ResponseEntity<>(resObj,HttpStatus.BAD_REQUEST); 
        } 
    }

    @PutMapping("/{userId}/follow")
    ResponseEntity<?> followUser(@PathVariable String userId){
        FollowingEntity follow = followService.followUser(userId);
        if(follow != null){
            return new ResponseEntity<>(follow,HttpStatus.OK);
        }
        else{
            resObj.setStatus("Something Went Wrong");
            return new ResponseEntity<>(resObj,HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/{userId}/unfollow")
    ResponseEntity<?> unfollowUser(@PathVariable String userId){
        boolean status = followService.unfollowUser(userId);
        if (status){
            resObj.setStatus("Unfollowed.");
            return new ResponseEntity<>(resObj,HttpStatus.OK);
        }
        else{
            resObj.setStatus("Something Went Wrong");
            return new ResponseEntity<>(resObj,HttpStatus.BAD_REQUEST);
        }
    }

    static public Authentication auth(){
        return SecurityContextHolder.getContext().getAuthentication();
    }
}