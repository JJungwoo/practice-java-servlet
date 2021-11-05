package com.practice.website.account.exception;

public class NotFoundEmailException extends Exception {
    public NotFoundEmailException() {
    }

    public NotFoundEmailException(String message) {
        super(message);
    }
}
