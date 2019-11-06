package com.ut.digitalsignature.exceptions;

import com.ut.digitalsignature.dto.Response.ResponseField;
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

   ResponseField responseField = new ResponseField();

   //   @Override
   //   protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
   //          HttpHeaders headers, HttpStatus status, WebRequest request) {
   //             return new ResponseEntity<Object>(responseField.setInvalidFormat(),HttpStatus.BAD_REQUEST);
   //   }
    
     @ExceptionHandler(value = ColumnValueNotFoundException.class)
     public ResponseEntity<Object> notfoundexception(ColumnValueNotFoundException exception) {
        return new ResponseEntity<>(responseField.setValueNotFound(exception.getMessage()), HttpStatus.NOT_FOUND);
     }

     @ExceptionHandler(value = CustomerAlreadyExistsException.class)
     public ResponseEntity<Object> alreadyexistexception(CustomerAlreadyExistsException exception) {
        return new ResponseEntity<>(responseField.setValueAlreadyExists(exception.getMessage()), HttpStatus.NOT_FOUND);
     }

     @ExceptionHandler(value = InvalidFileTypeException.class)
     public ResponseEntity<Object> filetypeexception(InvalidFileTypeException exception) {
        System.out.println("Hii");
        return new ResponseEntity<>(responseField.setFileTypeError(exception.getMessage()), HttpStatus.BAD_REQUEST);
     }

}