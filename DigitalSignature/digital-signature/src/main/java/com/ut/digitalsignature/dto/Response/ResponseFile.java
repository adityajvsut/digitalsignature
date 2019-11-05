package com.ut.digitalsignature.dto.Response;

import java.io.Serializable;

import javax.validation.constraints.NotEmpty;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class ResponseFile< T > implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    @NotEmpty
    @JsonProperty(value = "JSONFile", required = true)
    private T JSONFile;
    
}