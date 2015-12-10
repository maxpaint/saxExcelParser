package com.mdanylenko.excel.converter;


import com.mdanylenko.excel.exception.TypeCastException;

/**
 * Created by IntelliJ IDEA.<br/>
 * User: mdanylenko<br/>
 * Date: 10.12.2015<br/>
 * Time: 16:10<br/>
 * To change this template use File | Settings | File Templates.
 */
public interface DateConverter<T> extends TypeConverter<T> {


    T convert(String value, String format) throws TypeCastException;
}
