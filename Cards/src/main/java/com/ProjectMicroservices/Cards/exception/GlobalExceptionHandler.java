package com.ProjectMicroservices.Cards.exception;

import com.ProjectMicroservices.Cards.dto.ErrorReponseDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import java.time.LocalDateTime;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ErrorReponseDTO> handleResourceNotFoundException(ResourceNotFoundException rnf, WebRequest webRequest){
        ErrorReponseDTO errorResponseDTO = new ErrorReponseDTO();
        errorResponseDTO.setApiPath(webRequest.getDescription(false));
        errorResponseDTO.setStatusCode(HttpStatus.NOT_FOUND);
        errorResponseDTO.setErrorMessage(rnf.getMessage());
        errorResponseDTO.setErrorTimeStamp(LocalDateTime.now());
        return new ResponseEntity<>(errorResponseDTO, HttpStatus.NOT_FOUND);
    }
}
