package org.example.exceptions;

public class NotFoundCurrencyException extends RuntimeException {
    public NotFoundCurrencyException(String s) {
        super(s);
    }
}
