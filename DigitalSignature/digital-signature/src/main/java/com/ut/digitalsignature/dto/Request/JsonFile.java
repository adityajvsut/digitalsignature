package com.ut.digitalsignature.dto.Request;

import java.io.Serializable;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class JsonFile<T> implements Serializable{

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    @NotNull
    @JsonProperty(value = "JSONFile", required = true)
    @Valid
    private T JSONFile;
    
    
}