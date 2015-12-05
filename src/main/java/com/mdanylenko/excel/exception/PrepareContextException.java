package com.mdanylenko.excel.exception;

/**
 * Created by IntelliJ IDEA.<br/>
 * User: Max Danylenko<br/>
 * Date: 29.11.2015<br/>
 * Time: 16:23<br/>
 * To change this template use File | Settings | File Templates.
 */
public class PrepareContextException extends Exception {

    public PrepareContextException(String message) {
        super(message);
    }

    public PrepareContextException(String message, Throwable cause) {
        super(message, cause);
    }
}
