package com.qa.accounts.exception;

import java.time.LocalDateTime;

 class ValidationError {

    private final LocalDateTime timestamp;
    private final String errorMessage;

     ValidationError(LocalDateTime timestamp, String errorMessage) {
        this.timestamp = timestamp;
        this.errorMessage = errorMessage;
    }

     public LocalDateTime getTimestamp() {
         return timestamp;
     }

     public String getErrorMessage() {
         return errorMessage;
     }
 }

