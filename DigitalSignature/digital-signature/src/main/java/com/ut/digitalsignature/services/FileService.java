package com.ut.digitalsignature.services;

import com.ut.digitalsignature.exceptions.InvalidFileTypeException;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class FileService {

    @Value("${documents.filepath}")
    String filePath;

    public String saveImage(MultipartFile file){
        if(!(file.getContentType().equals("image/png") || file.getContentType().equals("image/jpeg"))){
            throw new InvalidFileTypeException("File should be image jpg or png");
          }
        
        return "Hii";
    }
}