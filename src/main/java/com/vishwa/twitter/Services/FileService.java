package com.vishwa.twitter.Services;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.cloudinary.*;
import com.cloudinary.utils.ObjectUtils;
import com.vishwa.twitter.utils.UploadStatus;

import io.github.cdimascio.dotenv.Dotenv;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

@Service
public class FileService {

    // -------------------------Cloud Operations----------------------------------------
    Dotenv dotenv = Dotenv.load();
    Cloudinary cloudinary = new Cloudinary(dotenv.get("CLOUDINARY_URL"));

    public List<String> uploadFileToCloud(MultipartFile file,String where) {
        try{
            if(file == null) return null;
            
            String pub_id = "image"+System.currentTimeMillis();
            Map<?,?> params1 = ObjectUtils.asMap("folder",where,
                                                "resource_type", "image",
                                                "public_id",pub_id,
                                                "overwrite",true);

            Map<?,?> uploaded = cloudinary.uploader().upload(file.getBytes(), params1);
            return Arrays.asList((String) uploaded.get("secure_url"),(String) uploaded.get("public_id"));
        } catch(Exception e){
            e.printStackTrace();
            return null;
        }
        
    }


    public Boolean deleteFileFromCloud(String pub_id){

        try{
            cloudinary.uploader().destroy(pub_id, ObjectUtils.emptyMap());
            return true;
        }
        catch(Exception e){
            e.printStackTrace();
            return true;
        }
    }
    // ---------------------------------------------------------------------------------
    public boolean checkFileType(MultipartFile file){
        if(file.isEmpty()) return false;
        if(file.getContentType().contains("image")) return true;
        else return false;
    }
    public UploadStatus checkFileAndUpload(MultipartFile file,String where){
        UploadStatus uploadStatus = new UploadStatus();
        if(file != null){
            if(!file.isEmpty() && checkFileType(file)){
                uploadStatus.setUrls(uploadFileToCloud(file,where));
                uploadStatus.setStatus("ud");//upload done
                return uploadStatus;
            }
            else {
                uploadStatus.setStatus("uft"); //Upload failed : unknown file type
                return uploadStatus;
            }
        }
        else{
                uploadStatus.setStatus("fn"); //file null
                return uploadStatus;
        }
    }
}
