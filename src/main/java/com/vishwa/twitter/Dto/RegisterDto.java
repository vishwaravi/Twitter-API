package com.vishwa.twitter.Dto;

import java.time.LocalDate;

import org.springframework.web.multipart.MultipartFile;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RegisterDto {
    private String userId;
    private String userName;
    private String userEmail;
    private LocalDate userDob;
    private String userPasswd;
    private MultipartFile profile;
    private MultipartFile banner;
    private String profileUrl,bannerUrl,profilePubId,bannerPubId;
}
