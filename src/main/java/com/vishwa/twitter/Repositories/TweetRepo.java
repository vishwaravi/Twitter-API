package com.vishwa.twitter.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.vishwa.twitter.Entities.TweetEntity;
import java.util.List;
import java.util.Optional;

@Repository
public interface TweetRepo extends JpaRepository<TweetEntity,Long>{
    List<TweetEntity> findByUserId(String UserId);
    Optional<TweetEntity> findById(int tweetId);
    boolean deleteAllByUserId(String userId);
}
