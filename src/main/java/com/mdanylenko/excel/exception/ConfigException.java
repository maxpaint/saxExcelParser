package com.mdanylenko.excel.exception;

/**
 * Created by IntelliJ IDEA.<br/>
 * User: Max Danylenko<br/>
 * Date: 05.12.2015<br/>
 * Time: 1:15<br/>
 * To change this template use File | Settings | File Templates.
 */
public class ConfigException  extends Exception {

    public ConfigException(String message, Throwable cause) {
        super(message, cause);
    }

    public ConfigException(String message) {
        super(message);
    }
}
