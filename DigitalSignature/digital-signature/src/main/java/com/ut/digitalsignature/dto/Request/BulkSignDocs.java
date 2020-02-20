package com.ut.digitalsignature.dto.Request;

import java.io.Serializable;
import java.util.List;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;


@Data
public class BulkSignDocs implements Serializable {

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
    @Email
    @JsonProperty(value = "document_id", required = true)
    private List<String> document_id;

}