package com.ut.digitalsignature.dto.Request;

import java.io.Serializable;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonProperty;


public class RegisterUser implements Serializable {
    /**
    *
    */
    private static final long serialVersionUID = 1L;

    @NotNull
    @Email
    @NotEmpty
    @JsonProperty(value = "user_id")
    private String user_id;

    @NotNull
    @NotEmpty
    @JsonProperty(value = "name", required = true)
    private String name;

    @NotNull
    @NotEmpty
    @Email
    @JsonProperty(value = "email", required = true)
    private String email;

    @NotNull
    @NotEmpty
    @Size(min=10,max=13)
    @JsonProperty(value = "mobile_number", required = true)
    private String mobile_number;

    public static long getSerialversionuid() {
        return serialVersionUID;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMobile_number() {
        return mobile_number;
    }

    public void setMobile_number(String mobile_number) {
        this.mobile_number = mobile_number;
    }


    
}