package com.ut.digitalsignature.dto.Request;

import java.io.Serializable;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class DigiSignDocs implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 1L;
    
    @NotNull
    @NotEmpty
    @Email
    @JsonProperty(value = "userid", required = true)
    private String userid;

    @NotNull
    @NotEmpty
    @Email
    @JsonProperty(value = "email", required = true)
    private String email;

    @NotNull
    @NotEmpty
    @JsonProperty(value = "document_id", required = true)
    private String document_id;

}