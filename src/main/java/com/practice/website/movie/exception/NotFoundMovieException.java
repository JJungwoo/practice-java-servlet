package com.practice.website.movie.exception;

public class NotFoundMovieException extends Exception {
    public NotFoundMovieException() {
    }

    public NotFoundMovieException(String message) {
        super(message);
    }
}
