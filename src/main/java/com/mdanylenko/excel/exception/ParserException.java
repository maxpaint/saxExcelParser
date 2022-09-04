package com.mdanylenko.excel.exception;

public class ParserException extends Exception {

    public ParserException(String message, Throwable cause) {
        super(message, cause);
    }

    public ParserException(String message) {
        super(message);
    }
}
