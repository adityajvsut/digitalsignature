package com.ut.digitalsignature.dto.Request;

import java.io.Serializable;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.ut.digitalsignature.exceptions.RecordNotFoundException;


public class JsonField<T> implements Serializable{

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    @NotNull
    @JsonProperty(value = "jsonfield", required = true)
    @Valid
    private JsonFile<T> jsonfield;

    public static long getSerialversionuid() {
        return serialVersionUID;
    }

    public JsonFile<T> getJsonfield() {
        return jsonfield;
    }

    public void setJsonfield(JsonFile<T> jsonfield) {
        if(jsonfield.equals(null)){
            throw new RecordNotFoundException("jsonfield is not present");
        }
        this.jsonfield = jsonfield;
    }
    
    
}