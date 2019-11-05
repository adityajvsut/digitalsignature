package com.ut.digitalsignature.dto.Request;

import java.io.Serializable;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class DocumentReqSign implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 1L;
    
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
    @Email
    @JsonProperty(value = "user", required = true)
    private String user;

    @NotNull
    @NotEmpty
    @JsonProperty(value = "aksi_ttd", required = true)
    private String aksi_ttd;

    @NotNull
    @NotEmpty
    @JsonProperty(value = "page", required = true)
    private String page;

    @NotNull
    @NotEmpty
    @JsonProperty(value = "llx", required = true)
    private String llx;

    @NotNull
    @NotEmpty
    @JsonProperty(value = "lly", required = true)
    private String lly;

    @NotNull
    @NotEmpty
    @JsonProperty(value = "urx", required = true)
    private String urx;

    @NotNull
    @NotEmpty
    @JsonProperty(value = "ury", required = true)
    private String ury;
    
}