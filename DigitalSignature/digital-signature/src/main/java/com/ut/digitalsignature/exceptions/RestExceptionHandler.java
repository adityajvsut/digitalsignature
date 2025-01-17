package com.ut.digitalsignature.exceptions;

import com.ut.digitalsignature.dto.Response.ResponseField;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.multipart.support.MissingServletRequestPartException;

@ControllerAdvice
public class RestExceptionHandler{
   ResponseField responseField = new ResponseField();

   @ExceptionHandler(GlobalException.class)
   public ResponseEntity<?> globalException(GlobalException ex) {
      return new ResponseEntity<>("Error", HttpStatus.BAD_REQUEST);
   }

   //       @Override
   //   protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
   //          HttpHeaders headers, HttpStatus status, WebRequest request) {
   //             BindingResult result = ex.getBindingResult();
   //             List<FieldError> fieldErrors = result.getFieldErrors();
   //             for (FieldError fieldError : fieldErrors) {
   //                 System.out.println(fieldError);
   //             }
   //             return new ResponseEntity<Object>(responseField.setValueNotFound("error"),HttpStatus.BAD_REQUEST);
   //   }


    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> exceptionHandler(Exception ex) {
      if(ex instanceof IllegalStateException){
         return new ResponseEntity<>(responseField.setValueNotFound("Bad Request Format"), HttpStatus.BAD_REQUEST);
        }
        if(ex instanceof MethodArgumentNotValidException){
         return new ResponseEntity<>(responseField.setValueNotFound("Bad Request Format"), HttpStatus.BAD_REQUEST);
        }
        if(ex instanceof RecordNotFoundException){
          return new ResponseEntity<>(responseField.setValueNotFound(ex.getMessage()),HttpStatus.BAD_REQUEST);
         }
        if(ex instanceof BindException){
         return new ResponseEntity<>(responseField.setValueNotFound(ex.getMessage()), HttpStatus.BAD_REQUEST);
        }
        if(ex instanceof MissingServletRequestPartException){
         return new ResponseEntity<>(responseField.setValueNotFound("Bad Request Format"), HttpStatus.BAD_REQUEST);
        }
        else{
        ex.printStackTrace();
        System.out.println(ex.getClass().getCanonicalName());
        return new ResponseEntity<>(ex.getClass().getCanonicalName(),HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

   @ExceptionHandler(value = IllegalStateException.class)
   public ResponseEntity<Object> requestjsonexception(IllegalStateException exception) {
        return new ResponseEntity<>(responseField.setValueNotFound("Bad Request Format"), HttpStatus.BAD_REQUEST);
   }

   @ExceptionHandler(value = BindException.class)
   public ResponseEntity<Object> bindexception(BindException exception) {
        return new ResponseEntity<>(responseField.setValueNotFound("Bad Request Format"), HttpStatus.BAD_REQUEST);
   }

   @ExceptionHandler(value = InvalidFileTypeException.class)
   public ResponseEntity<Object> filetypeexception(InvalidFileTypeException exception) {
      return new ResponseEntity<>(responseField.setFileTypeError(exception.getMessage()), HttpStatus.BAD_REQUEST);
   }

   @ExceptionHandler(value = CustomerAlreadyExistsException.class)
   public ResponseEntity<Object> alreadyexistexception(CustomerAlreadyExistsException exception) {
      return new ResponseEntity<>(responseField.setValueAlreadyExists(exception.getMessage()), HttpStatus.FORBIDDEN);
   }

   @ExceptionHandler(value = ColumnValueNotFoundException.class)
   public ResponseEntity<Object> alreadyexistexception(ColumnValueNotFoundException exception) {
      return new ResponseEntity<>(responseField.setValueAlreadyExists(exception.getMessage()), HttpStatus.FORBIDDEN);
   }
    
}