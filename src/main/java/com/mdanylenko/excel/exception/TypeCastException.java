package com.mdanylenko.excel.exception;

/**
 * Created by IntelliJ IDEA.<br/>
 * User: mdanylenko<br/>
 * Date: 10.12.2015<br/>
 * Time: 16:30<br/>
 * To change this template use File | Settings | File Templates.
 */
public class TypeCastException extends Throwable {

    public TypeCastException(String message) {
        super(message);
    }

    public TypeCastException(String message, Throwable cause) {
        super(message, cause);
    }
}
