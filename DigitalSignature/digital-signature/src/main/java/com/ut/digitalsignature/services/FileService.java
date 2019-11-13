package com.ut.digitalsignature.services;

import java.io.File;

import com.ut.digitalsignature.exceptions.InvalidFileTypeException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class FileService {

    @Value("${documents.filepath}")
    String filePath;

    @Autowired
    AesService aesService;

    public String saveImage(String fileName, MultipartFile file) throws Exception {
        if(!(file.getContentType().equals("image/png") || file.getContentType().equals("image/jpeg"))){
            throw new InvalidFileTypeException("File should be image jpg or png");}
        String fileEncr = aesService.AesEncryption(fileName);  
        File savefile = new File(filePath+fileEncr);
        savefile.mkdir();
        file.transferTo(new File(filePath+fileEncr+"/"+file.getOriginalFilename()));
        return fileEncr+"/"+file.getOriginalFilename();
    }
}