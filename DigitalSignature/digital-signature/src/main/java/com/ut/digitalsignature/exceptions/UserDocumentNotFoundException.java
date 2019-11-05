package com.ut.digitalsignature.exceptions;

public class UserDocumentNotFoundException extends RuntimeException {

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    public UserDocumentNotFoundException(String message) {
        super(message);
    }
}