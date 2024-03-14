package com.ProjectMicroservices.Cards.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
@Data
@AllArgsConstructor
@NoArgsConstructor
@Component
public class ErrorReponseDTO {

    private String apiPath;
    private HttpStatus statusCode;
    private String errorMessage;
    private LocalDateTime errorTimeStamp;

}
