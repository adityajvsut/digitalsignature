package com.ut.digitalsignature.dto.Response;

import java.io.Serializable;

import javax.validation.constraints.NotEmpty;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ResponseFile< T > implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    @NotEmpty
    @JsonProperty(value = "JSONFile", required = true)
    private T JSONFile;

    public ResponseFile<T> getResponseFile(T json){
        this.setJSONFile(json);
        return this;
    }

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