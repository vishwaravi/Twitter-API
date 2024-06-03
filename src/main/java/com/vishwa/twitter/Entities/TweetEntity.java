package com.vishwa.twitter.Entities;

import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "tweets_table")
public class TweetEntity {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name="tweet_id")
    private Long Id;

    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "tweet_file_id",referencedColumnName = "id")
    private TweetFile tweetFile;

    @Column(name = "tweet_content")
    private String tweetContent;

    @Column(name = "user_id")
    private String userId;

    @Column(name = "hashtags")
    private String hashtags;

    @Column(name = "time_stamp")
    private String timeStamp;

    @OneToMany(mappedBy = "tweetId", orphanRemoval = true)
    private List<CommentEntity> comments;

    @Column(name = "likes_count")
    @Builder.Default
    private Long likesCount = 0L;
}
