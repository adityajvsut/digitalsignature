package com.ut.digitalsignature.dto.Request;

import java.io.Serializable;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonProperty;

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

    public static long getSerialversionuid() {
        return serialVersionUID;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getDocument_id() {
        return document_id;
    }

    public void setDocument_id(String document_id) {
        this.document_id = document_id;
    }

}