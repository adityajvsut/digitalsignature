package com.ut.digitalsignature.exceptions;

public class ColumnValueNotFoundException extends RuntimeException {

	/**
	 *
	 */
    private static final long serialVersionUID = 1L;
    
    public ColumnValueNotFoundException(String message){
        super(message);
    }
}
