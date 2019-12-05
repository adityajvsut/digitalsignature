package com.ut.digitalsignature.dto.Request;

import java.io.Serializable;
import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonProperty;


public class DocumentJSONFile implements Serializable {

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
    @JsonProperty(value = "document_id", required = true)
    private String document_id;

    @NotNull
    @NotEmpty
    @JsonProperty(value = "send-to", required = true)
    @Valid
    private List<DocumentViewers> send_to;

    @NotNull
    @NotEmpty
    @JsonProperty(value = "req-sign", required = true)
    @Valid
    private List<DocumentReqSign> req_sign;

    public static long getSerialversionuid() {
        return serialVersionUID;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getDocument_id() {
        return document_id;
    }

    public void setDocument_id(String document_id) {
        this.document_id = document_id;
    }

    public List<DocumentViewers> getSend_to() {
        return send_to;
    }

    public void setSend_to(List<DocumentViewers> send_to) {
        this.send_to = send_to;
    }

    public List<DocumentReqSign> getReq_sign() {
        return req_sign;
    }

    public void setReq_sign(List<DocumentReqSign> req_sign) {
        this.req_sign = req_sign;
    }
    
}