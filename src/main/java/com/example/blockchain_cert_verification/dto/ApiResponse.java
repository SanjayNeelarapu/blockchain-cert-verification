package com.example.blockchain_cert_verification.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class ApiResponse {
    private String status;
    private String message;

    @JsonInclude(JsonInclude.Include.NON_NULL) // <-- hides "data" if null
    private Object data;
    private LocalDateTime timestamp;


    public ApiResponse(String status, String message, Object data) {
        this.status = status;
        this.message = message;
        this.data = data;
        timestamp = LocalDateTime.now();
    }

    public ApiResponse(String status, String message){
        this.status = status;
        this.message = message;
        timestamp = LocalDateTime.now();
    }

}