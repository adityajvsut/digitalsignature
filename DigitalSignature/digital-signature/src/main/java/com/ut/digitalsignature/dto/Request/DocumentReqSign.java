package com.ut.digitalsignature.dto.Request;

import java.io.Serializable;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonProperty;

import org.springframework.stereotype.Component;

@Component
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

    public static long getSerialversionuid() {
        return serialVersionUID;
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

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getAksi_ttd() {
        return aksi_ttd;
    }

    public void setAksi_ttd(String aksi_ttd) {
        this.aksi_ttd = aksi_ttd;
    }

    public String getPage() {
        return page;
    }

    public void setPage(String page) {
        this.page = page;
    }

    public String getLlx() {
        return llx;
    }

    public void setLlx(String llx) {
        this.llx = llx;
    }

    public String getLly() {
        return lly;
    }

    public void setLly(String lly) {
        this.lly = lly;
    }

    public String getUrx() {
        return urx;
    }

    public void setUrx(String urx) {
        this.urx = urx;
    }

    public String getUry() {
        return ury;
    }

    public void setUry(String ury) {
        this.ury = ury;
    }
    
}