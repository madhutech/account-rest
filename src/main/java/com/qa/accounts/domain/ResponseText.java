package com.qa.accounts.domain;

public class ResponseText {

    private String message;

    public ResponseText(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
