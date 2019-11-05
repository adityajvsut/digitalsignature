package com.ut.digitalsignature.dto.Request;

import java.io.Serializable;
import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
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
    
}