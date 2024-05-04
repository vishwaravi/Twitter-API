package com.vishwa.twitter.Repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.vishwa.twitter.Entities.CommentEntity;

@Repository
public interface CommentRepo extends JpaRepository<CommentEntity,Long>{
    Optional<CommentEntity> findByIdAndTweetId(long commentId,long tweetId);
}
