package com.ut.digitalsignature.dto.Request;

import java.io.Serializable;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonProperty;


public class JsonFile<T> implements Serializable{

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    @NotNull
    @JsonProperty(value = "JSONFile", required = true)
    @Valid
    private T JSONFile;

    public static long getSerialversionuid() {
        return serialVersionUID;
    }

    public T getJSONFile() {
        return JSONFile;
    }

    public void setJSONFile(T jSONFile) {
        JSONFile = jSONFile;
    }
    
    
}