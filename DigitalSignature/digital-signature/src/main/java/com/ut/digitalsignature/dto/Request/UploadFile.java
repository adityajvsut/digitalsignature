package com.ut.digitalsignature.dto.Request;

import java.io.Serializable;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.ut.digitalsignature.exceptions.ColumnValueNotFoundException;

import org.springframework.web.multipart.MultipartFile;


public class UploadFile implements Serializable{

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    @NotNull
    @JsonProperty(value = "file")
    @Valid
    private MultipartFile file;

    public static long getSerialversionuid() {
        return serialVersionUID;
    }

    public MultipartFile getFile() {
        return file;
    }

    public void setFile(MultipartFile file){
        if(file.getContentType().equals("image/png") || file.getContentType().equals("image/jpeg")){
            this.file = file;
        }
        else{
            throw new ColumnValueNotFoundException("File should be image jpg or png");
        }
    }
    
}