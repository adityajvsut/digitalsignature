package com.ut.digitalsignature.dto.Request;

import java.io.Serializable;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonProperty;

import org.springframework.web.multipart.MultipartFile;

public class UploadDoc implements Serializable{

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

    public void setFile(MultipartFile file) {
        this.file = file;
    }
    
}