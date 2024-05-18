package com.vishwa.twitter.Services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.vishwa.twitter.Config.Time.TimeStamp;
import com.vishwa.twitter.Entities.CommentEntity;
import com.vishwa.twitter.Entities.LikeEntity;
import com.vishwa.twitter.Entities.TweetEntity;
import com.vishwa.twitter.Entities.UserEntity;
import com.vishwa.twitter.Repositories.CommentRepo;
import com.vishwa.twitter.Repositories.LikeRepo;
import com.vishwa.twitter.Repositories.TweetRepo;
import com.vishwa.twitter.Repositories.UserRepo;

import jakarta.transaction.Transactional;

@Service
public class TweetService{
    @Autowired
    private UserRepo userRepo;
    @Autowired
    private TweetRepo tweetRepo;
    @Autowired
    private CommentRepo commentRepo;
    @Autowired
    private LikeRepo likeRepo;

    //For post the tweet
    public TweetEntity postTweet(TweetEntity tweet){
        String userName = auth().getName();
        UserEntity user = userRepo.findByUserId(userName).get();
        tweet.setUserId(user.getUserId());
        tweet.setTimeStamp(TimeStamp.getTStamp());
        return tweetRepo.save(tweet);
    }

    //for get the tweet using tweet id
    public Optional<TweetEntity> getTweet(long tweetId){
        return tweetRepo.findById(tweetId);
    }

    //for fetch all tweets
    public List<TweetEntity> getTweets(){
        return tweetRepo.findAll();
    }

    //for get the tweets by the user
    public List<TweetEntity> getMyTweets(){
        String userName = auth().getName();
        return tweetRepo.findByUserId(userName);
    }

    //for delete the tweet
    public boolean deleteTweet(long tweetId){
        Optional<TweetEntity> tweet = tweetRepo.findById(tweetId);
        if (tweet.isPresent() && tweet.get().getUserId().equals(auth().getName())) {
            commentRepo.deleteByTweetId(tweetId);
            tweetRepo.delete(tweet.get());
            return true;
        }
        else return false;
    }

    //For post the comment
    public CommentEntity postComment(CommentEntity comment, long tweetId){
        String userName = auth().getName();
        if(getTweet(tweetId).isPresent()){
            comment.setTweetId(tweetId);
            comment.setUserId(userName);
            comment.setTimeStamp(TimeStamp.getTStamp());
            return commentRepo.save(comment);
        }
        else return null;
    }

    //For delete the comment
    public boolean deleteComment(long commentId,long tweetId){
        Optional<CommentEntity> comment = commentRepo.findByIdAndTweetId(commentId, tweetId);
        if(comment.isPresent()&&comment.get().getUserId().equals(auth().getName())){
            commentRepo.delete(comment.get());
            return true;
        }
        else return false;
    }

    //Function for post the like to tweet
    @Modifying
    @Transactional
    public LikeEntity postLike(long tweetId){
        Optional<TweetEntity> tweet = tweetRepo.findById(tweetId);
        if(tweet.isPresent()){
            LikeEntity like = LikeEntity.builder()
                            .likedBy(auth().getName())
                            .tweetId(tweetId)
                            .build();
            if(tweet.get().getLikesCount()==null){
                tweet.get().setLikesCount(1);
            } else tweet.get().setLikesCount(tweet.get().getLikesCount()+1); 
            tweetRepo.save(tweet.get());
            return likeRepo.save(like);
        }
        return null;
    }

    //Function for get the user name from the Security Context
    static public Authentication auth(){
        return SecurityContextHolder.getContext().getAuthentication();
    }
}
