package com.ut.digitalsignature.dto.Request;

import java.io.Serializable;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;


@Data
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


    
}