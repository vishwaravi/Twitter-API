package com.vishwa.twitter.Repositories;


import org.springframework.data.jpa.repository.JpaRepository;

import com.vishwa.twitter.Entities.TweetEntity;

public interface TweetRepo extends JpaRepository<TweetEntity,Long>{
}
