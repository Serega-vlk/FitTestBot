package com.example.fittestbot.exception;

import jakarta.ws.rs.NotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MissingRequestHeaderException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.sql.SQLException;

@ControllerAdvice
public class GlobalExceptionHandler {

  @ExceptionHandler(AccessDeniedException.class)
  public ResponseEntity<String> denied(){
    return ResponseEntity.status(403)
        .body("You have no permissions to add test");
  }

  @ExceptionHandler(NotFoundException.class)
  public ResponseEntity<String> notFound(){
    return ResponseEntity.status(404)
        .body("Id is not registered");
  }

  @ExceptionHandler(SQLException.class)
  public ResponseEntity<String> dataBase(SQLException throwables){
    return ResponseEntity.status(500)
        .body(throwables.getMessage());
  }

  @ExceptionHandler(MissingRequestHeaderException.class)
  public ResponseEntity<String> header(MissingRequestHeaderException throwables){
    return ResponseEntity.status(400)
        .body("Header 'id' is not presented");
  }
}
