package com.ut.digitalsignature.exceptions;

import com.ut.digitalsignature.dto.Response.ResponseField;
import com.ut.digitalsignature.dto.Response.ResponseFile;

import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@Order(Ordered.HIGHEST_PRECEDENCE)
@ControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(RecordNotFoundException.class)
    public final ResponseEntity<Object> handleUserNotFoundException(RecordNotFoundException ex, WebRequest request) {
        return new ResponseEntity<Object>("record not found", HttpStatus.NOT_FOUND);
    }
     @Override
     protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
            HttpHeaders headers, HttpStatus status, WebRequest request) {
                ResponseFile<ResponseField> responseFile = new ResponseFile<ResponseField>();
                ResponseField responseField = new ResponseField();
                responseField.setResult("30");
                responseField.setNotif("Invalid Request Format");
                responseFile.setJSONFile(responseField);
                return new ResponseEntity<Object>(responseFile,HttpStatus.BAD_REQUEST);
     }
    
     @ExceptionHandler(value = ColumnValueNotFoundException.class)
     public ResponseEntity<Object> notfoundexception(ColumnValueNotFoundException exception) {
        ResponseFile<ResponseField> responseFile = new ResponseFile<ResponseField>();
        ResponseField responseField = new ResponseField();
        responseField.setResult("05");
        responseField.setNotif(exception.getMessage());
        responseFile.setJSONFile(responseField);
        return new ResponseEntity<>(responseFile, HttpStatus.NOT_FOUND);
     }

     @ExceptionHandler(value = CustomerAlreadyExistsException.class)
     public ResponseEntity<Object> alreadyexistexception(CustomerAlreadyExistsException exception) {
        ResponseFile<ResponseField> responseFile = new ResponseFile<ResponseField>();
        ResponseField responseField = new ResponseField();
        responseField.setResult("14");
        responseField.setNotif(exception.getMessage());
        responseFile.setJSONFile(responseField);
        return new ResponseEntity<>(responseFile, HttpStatus.NOT_FOUND);
     }
   //other exception handlers below

}