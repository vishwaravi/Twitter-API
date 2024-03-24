package com.vishwa.twitter.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.vishwa.twitter.Entities.UserEntity;

public interface UserRepo extends JpaRepository<UserEntity,Integer>{
}
