package com.vishwa.twitter.Controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.vishwa.twitter.Entities.TweetEntity;
import com.vishwa.twitter.Services.TweetService;

@RestController
@RequestMapping("/api/tweets")
public class TweetController {

    @Autowired
    private TweetService tweetService;
    @GetMapping
    List<TweetEntity> getTweets(){
        return tweetService.getTweets();
    }

    @PostMapping
    ResponseEntity<?> postTweet(@RequestBody TweetEntity tweet){
        TweetEntity postedTweet = tweetService.postTweet(tweet);
        return new ResponseEntity<>(postedTweet,HttpStatus.CREATED);
    }
}
