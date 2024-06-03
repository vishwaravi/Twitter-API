package com.vishwa.twitter.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.vishwa.twitter.Entities.TweetFile;

@Repository
public interface TweetFileRepo extends JpaRepository<TweetFile,Long>{
}
