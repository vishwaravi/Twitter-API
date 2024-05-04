package com.vishwa.twitter.Services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.vishwa.twitter.Config.Time.TimeStamp;
import com.vishwa.twitter.Entities.CommentEntity;
import com.vishwa.twitter.Entities.TweetEntity;
import com.vishwa.twitter.Entities.UserEntity;
import com.vishwa.twitter.Repositories.CommentRepo;
import com.vishwa.twitter.Repositories.TweetRepo;
import com.vishwa.twitter.Repositories.UserRepo;

@Service
public class TweetService{
    @Autowired
    private UserRepo userRepo;
    @Autowired
    private TweetRepo tweetRepo;
    @Autowired
    private CommentRepo commentRepo;

    public TweetEntity postTweet(TweetEntity tweet){
        String userName = auth().getName();
        UserEntity user = userRepo.findByUserId(userName).get();
        tweet.setUserId(user.getUserId());
        tweet.setTimeStamp(TimeStamp.getTStamp());
        return tweetRepo.save(tweet);
    }

    public Optional<TweetEntity> getTweet(int tweetId){
        return tweetRepo.findById(tweetId);
    }

    public List<TweetEntity> getTweets(){
        return tweetRepo.findAll();
    }

    public List<TweetEntity> getMyTweets(){
        String userName = auth().getName();
        return tweetRepo.findByUserId(userName);
    }

    public boolean deleteTweet(long tweetId){
        Optional<TweetEntity> tweet = tweetRepo.findById(tweetId);
        if (tweet.isPresent() && tweet.get().getUserId().equals(auth().getName())) {
            tweetRepo.delete(tweet.get());
            return true;
        }
        else return false;
    }

    public CommentEntity postComment(CommentEntity comment){
        String userName = auth().getName();
        comment.setUserId(userName);
        comment.setTimeStamp(TimeStamp.getTStamp());
        return commentRepo.save(comment);
    }

    public boolean deleteComment(long commentId,long tweetId){
        Optional<CommentEntity> comment = commentRepo.findByIdAndTweetId(commentId, tweetId);
        if(comment.isPresent()&&comment.get().getUserId().equals(auth().getName())){
            commentRepo.delete(comment.get());
            return true;
        }
        else return false;
    }

    static public Authentication auth(){
        return SecurityContextHolder.getContext().getAuthentication();
    }
}
