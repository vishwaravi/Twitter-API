package com.vishwa.twitter.Entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "comments_table")
public class CommentEntity {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;

    @Column(name = "comment_content" , nullable = false)
    private String commentContent;

    @Column(name = "tweet_id")
    private Long tweetId;

    @Column(name = "user_id")
    private String userId;

    @Column(name = "time_stamp")
    private String timeStamp;
}
