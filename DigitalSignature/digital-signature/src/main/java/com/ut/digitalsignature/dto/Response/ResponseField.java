package com.ut.digitalsignature.dto.Response;

import java.io.Serializable;

import javax.validation.constraints.NotEmpty;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class ResponseField implements Serializable {
    /**
    *
    */
    private static final long serialVersionUID = 1L;

    @NotEmpty
    @JsonProperty(value = "result", required = true)
    private String result;

    @NotEmpty
    @JsonProperty(value = "notif", required = true)
    private String notif;

}