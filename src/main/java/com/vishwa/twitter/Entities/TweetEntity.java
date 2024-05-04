package com.vishwa.twitter.Entities;

import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "tweets_table")
public class TweetEntity {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name="tweet_id")
    private Long Id;

    @Column(name = "tweet_content")
    private String tweetContent;

    @Column(name = "user_id")
    private String userId;

    @Column(name = "hashtags")
    private String hashtags;

    @Column(name = "time_stamp")
    private String timeStamp;

    @OneToMany(mappedBy = "tweetId")
    List<CommentEntity> comments;
}
