package com.vishwa.twitter.Dto;

import org.springframework.web.multipart.MultipartFile;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
// this DTO Class for posting Tweet
public class TweetDto {
    private String tweetContent;
    private MultipartFile file;
    private String hashtags;
    private String fileUrl;
    private String filePubId;
}
