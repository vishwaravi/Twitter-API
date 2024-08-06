package com.vishwa.twitter.Services;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;


@Service
public class FileService {
    @Value("${file.upload-dir}")
    private String uploadDir;

    public String saveFileToMedia(MultipartFile file,String where) throws IllegalStateException, IOException {
        byte[] data;

        if(file == null) return null;
        else data = file.getBytes();

        String fileName = genrateFileName(file);
        Path filePath;

        if(where.equals("media"))
            filePath = Paths.get(uploadDir,"/","media","/",fileName);
        else 
            filePath = Paths.get(uploadDir,"/","profile","/",fileName);

        Files.write(filePath,data);
        return filePath.toString();
    }

    public boolean deleteFile(String path){
        if(path == null) return true;
        
        Path filePath = Paths.get(path);
        try{
            Files.deleteIfExists(filePath);
            return true;
        }
        catch(IOException e){
            e.printStackTrace();
        }
        return false;         
    }

    @SuppressWarnings("null")
    private String genrateFileName(MultipartFile file){
        int dotIndex = 0;
        if (file == null) {
            throw new IllegalArgumentException("TweetDto or TweetFile cannot be null");
        }
        else return auth().getName()+System.currentTimeMillis()+file.getOriginalFilename().substring(dotIndex);
    }

    //Function for get the user name from the Security Context
    private static Authentication auth(){
        return SecurityContextHolder.getContext().getAuthentication();
    }
}
