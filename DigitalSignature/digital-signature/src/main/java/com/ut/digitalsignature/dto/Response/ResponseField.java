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

    public ResponseFile<ResponseField> setInvalidFormat() {
        this.setResult("30");
        this.setNotif("Invalid Request Format");
        return new ResponseFile<ResponseField>().getResponseFile(this);
    }

    public ResponseFile<ResponseField> setValueNotFound(String message) {
        this.setResult("05");
        this.setNotif(message);
        return new ResponseFile<ResponseField>().getResponseFile(this);
    }

    public ResponseFile<ResponseField> setValueAlreadyExists(String message) {
        this.setResult("14");
        this.setNotif(message);
        return new ResponseFile<ResponseField>().getResponseFile(this);
    }

	public ResponseFile<ResponseField> setFileTypeError(String message) {
		this.setResult("08");
        this.setNotif(message);
        return new ResponseFile<ResponseField>().getResponseFile(this);
    }
    
    public ResponseFile<ResponseField> setSuccess() {
        System.out.println("Hii");
        System.out.println(this.toString());
		this.setResult("00");
        this.setNotif("Success");
        return new ResponseFile<ResponseField>().getResponseFile(this);
	}

    public static long getSerialversionuid() {
        return serialVersionUID;
    }

    // public String getResult() {
    //     return result;
    // }

    // public void setResult(String result) {
    //     this.result = result;
    // }

    // public String getNotif() {
    //     return notif;
    // }

    // public void setNotif(String notif) {
    //     this.notif = notif;
    // }

}