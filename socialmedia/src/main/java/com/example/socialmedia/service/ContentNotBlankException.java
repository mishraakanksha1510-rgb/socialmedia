package com.example.socialmedia.service;

public class ContentNotBlankException extends RuntimeException {

    public ContentNotBlankException(String message) {
        super(message);
    }
}