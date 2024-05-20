package com.vishwa.twitter.Controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.vishwa.twitter.Entities.CommentEntity;
import com.vishwa.twitter.Entities.TweetEntity;
import com.vishwa.twitter.Services.TweetService;

@RestController
@RequestMapping("/home")
public class TweetController {

    @Autowired
    private TweetService tweetService;
   
    @GetMapping
    List<TweetEntity> getTweets(){
        return tweetService.getTweets();
    }

    @GetMapping("/myposts")
    List<TweetEntity> myTweets(){
        return tweetService.getMyTweets();
    }

    @GetMapping("/{tweetId}")
    TweetEntity getTweets(@PathVariable int tweetId){
        return tweetService.getTweet(tweetId).get();
    }

    @PostMapping
    TweetEntity postTweet(@RequestBody TweetEntity tweet){
        return tweetService.postTweet(tweet);
    }

    @DeleteMapping("/{tweetId}")
    ResponseEntity<?> deleteTweet(@PathVariable int tweetId){
        if(tweetService.deleteTweet(tweetId))
        return new ResponseEntity<>("{\"status\":\"tweet deleted.\"}",HttpStatus.OK);
        else return new ResponseEntity<>("{\"status\":\"Something went Wrong.\"}",HttpStatus.BAD_REQUEST);
    }
    
    @PutMapping("/{tweetId}/comment")
    ResponseEntity<?> postComment(@RequestBody CommentEntity comment,@PathVariable int tweetId){
        if(tweetService.postComment(comment,tweetId)!=null)
        return new ResponseEntity<>(tweetService.postComment(comment,tweetId),HttpStatus.OK);
        else return new ResponseEntity<>("{\"status\":\"Something went Wrong. Comment not posted\"}",HttpStatus.BAD_REQUEST);
    }
    
    @DeleteMapping("/{tweetId}/comment")
    ResponseEntity<?> deleteComment(@RequestBody CommentEntity comment,@PathVariable long tweetId){
        if(tweetService.deleteComment(comment.getId(), tweetId)){
            return new ResponseEntity<>("{\"status\":\"comment deleted.\"}",HttpStatus.OK);
        }
        else return new ResponseEntity<>("{\"status\":\"Something went Wrong.\"}",HttpStatus.BAD_REQUEST);
    }

    @PutMapping("/{tweetId}/like")
    ResponseEntity<?> postLike(@PathVariable long tweetId){
        if(tweetService.postLike(tweetId)!=null){
            return new ResponseEntity<>("{\"status\":\"post Liked\"}",HttpStatus.OK);
        }
        else return new ResponseEntity<>("{\"status\":\"Something went Wrong.\"}",HttpStatus.BAD_REQUEST);
    }
    @PutMapping("/{tweetId}/dislike")
    ResponseEntity<?> dislike(@PathVariable long tweetId){
        if(tweetService.dislikePost(tweetId)){
            return new ResponseEntity<>("{\"status\":\"post Disliked\"}",HttpStatus.OK);
        }
        else return new ResponseEntity<>("{\"status\":\"Something went Wrong.\"}",HttpStatus.BAD_REQUEST);
    }
}
