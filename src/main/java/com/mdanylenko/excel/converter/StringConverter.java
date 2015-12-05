package com.mdanylenko.excel.converter;

/**
 * Created by IntelliJ IDEA.<br/>
 * User: Max Danylenko<br/>
 * Date: 27.11.2015<br/>
 * Time: 20:47<br/>
 * To change this template use File | Settings | File Templates.
 */
public class StringConverter implements TypeConverter<String> {

    @Override
    public String convert(String value) {
        return value;
    }
}
