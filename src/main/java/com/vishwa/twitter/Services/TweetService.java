package com.vishwa.twitter.Services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.vishwa.twitter.Config.Time.TimeStamp;
import com.vishwa.twitter.Entities.TweetEntity;
import com.vishwa.twitter.Entities.UserEntity;
import com.vishwa.twitter.Repositories.TweetRepo;
import com.vishwa.twitter.Repositories.UserRepo;

@Service
public class TweetService{
    @Autowired
    private UserRepo userRepo;
    @Autowired
    private TweetRepo tweetRepo;

    public TweetEntity postTweet(TweetEntity tweet){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userName = authentication.getName();
        UserEntity user = userRepo.findByUserId(userName).get();
        tweet.setUserId(user.getUserId());
        tweet.setTimeStamp(TimeStamp.getTStamp());
        System.out.println(tweet.toString());
        return tweetRepo.save(tweet);
    }

    public List<TweetEntity> getTweets(){
        return tweetRepo.findAll();
    }
}
