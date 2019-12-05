package com.ut.digitalsignature.dto.Request;

import java.io.Serializable;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonProperty;


public class OtpJson implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    @NotNull
    @NotEmpty
    @Email
    @JsonProperty(value = "email", required = true)
    private String email;

    @NotNull
    @NotEmpty
    @Size(min = 4,max = 4)
    @JsonProperty(value = "otp", required = true)
    private String otp;

    public static long getSerialversionuid() {
        return serialVersionUID;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getOtp() {
        return otp;
    }

    public void setOtp(String otp) {
        this.otp = otp;
    }

}