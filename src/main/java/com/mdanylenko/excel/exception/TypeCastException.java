package com.mdanylenko.excel.exception;

public class TypeCastException extends Throwable {

    public TypeCastException(String message) {
        super(message);
    }

    public TypeCastException(String message, Throwable cause) {
        super(message, cause);
    }
}
