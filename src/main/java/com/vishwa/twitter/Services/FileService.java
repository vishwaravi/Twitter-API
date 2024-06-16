package com.vishwa.twitter.Services;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;


@Service
public class FileService {
    private static final String FILE_PATH_MEDIA = "/home/vishwa/SpringBootWS/FileSystem/media";
    private static final String FILE_PATH_PROFILE = "/home/vishwa/SpringBootWS/FileSystem/profile";

    public String[] saveFileToMedia(MultipartFile file,String where) throws IllegalStateException, IOException {
        String fileName = genrateFileName(file);
        Path filePathMedia;
        if(where.equals("media"))
            filePathMedia = Paths.get(FILE_PATH_MEDIA, fileName);
        else 
            filePathMedia = Paths.get(FILE_PATH_PROFILE,fileName);

        file.transferTo(filePathMedia.toFile());
        return new String[]{fileName,filePathMedia.toString()};
    }

    @SuppressWarnings("null")
    String genrateFileName(MultipartFile file){
        int dotIndex = 0;
        if (file == null) {
            throw new IllegalArgumentException("TweetDto or TweetFile cannot be null");
        }
        else return auth().getName()+System.currentTimeMillis()+file.getOriginalFilename().substring(dotIndex);
    }

    //Function for get the user name from the Security Context
    static public Authentication auth(){
        return SecurityContextHolder.getContext().getAuthentication();
    }
}
