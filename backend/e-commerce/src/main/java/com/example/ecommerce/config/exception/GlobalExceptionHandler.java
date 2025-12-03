package com.example.ecommerce.config.exception;


import com.example.ecommerce.helper.MessageResponse;
import com.example.ecommerce.service.imp.BundleResourceService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.ArrayList;
import java.util.List;

@ControllerAdvice

public class GlobalExceptionHandler {
    private BundleResourceService bundleResourceService;
    @Autowired
    public GlobalExceptionHandler(BundleResourceService bundleResourceService) {
        this.bundleResourceService = bundleResourceService;
    }
    @ExceptionHandler
    public ResponseEntity<MessageResponse> handleAllExceptions(Exception ex) {
        return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(bundleResourceService.getMessages(ex.getMessage()));
    }
    @ExceptionHandler
    public ResponseEntity<List<MessageResponse>> handleValidationExceptions(MethodArgumentNotValidException exception){
        List<MessageResponse> errors = new ArrayList<>();
        exception.getFieldErrors().forEach(err ->
        {
            errors.add(bundleResourceService.getMessages(err.getDefaultMessage()));
        });
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errors);
    }
}
