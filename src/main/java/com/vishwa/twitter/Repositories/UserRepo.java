package com.vishwa.twitter.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.vishwa.twitter.Entities.UserEntity;
import java.util.Optional;



@Repository
public interface UserRepo extends JpaRepository<UserEntity,Long>{
    Optional<UserEntity> findByUserId(String userId);
}
