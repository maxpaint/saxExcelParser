package com.mdanylenko.excel.exception;

/**
 * Created by IntelliJ IDEA.<br/>
 * User: Max Danylenko<br/>
 * Date: 30.11.2015<br/>
 * Time: 19:26<br/>
 * To change this template use File | Settings | File Templates.
 */
public class ParserException extends Exception {

    public ParserException(String message, Throwable cause) {
        super(message, cause);
    }

    public ParserException(String message) {
        super(message);
    }
}
