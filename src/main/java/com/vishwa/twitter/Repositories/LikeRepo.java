package com.vishwa.twitter.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.vishwa.twitter.Entities.LikeEntity;

@Repository
public interface LikeRepo extends JpaRepository<LikeEntity,Long>{
    boolean existsByLikedBy(String likedBy);
    
    @Transactional
    void deleteByTweetId(long tweetId);
}
