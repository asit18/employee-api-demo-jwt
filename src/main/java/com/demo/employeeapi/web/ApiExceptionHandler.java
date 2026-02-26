package com.demo.employeeapi.web;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.server.ResponseStatusException;

import java.util.Map;

@RestControllerAdvice
public class ApiExceptionHandler {

  @ExceptionHandler(ResponseStatusException.class)
  ResponseEntity<Map<String, Object>> handleRse(ResponseStatusException ex) {
    return ResponseEntity.status(ex.getStatusCode())
        .body(Map.of("error", ex.getReason()));
  }

  @ExceptionHandler(AccessDeniedException.class)
  ResponseEntity<Map<String, Object>> forbidden() {
    return ResponseEntity.status(403).body(Map.of("error", "Forbidden"));
  }

  @ExceptionHandler(Exception.class)
  ResponseEntity<Map<String, Object>> serverError() {
    return ResponseEntity.status(500).body(Map.of("error", "Internal Server Error"));
  }
}
