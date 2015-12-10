package com.mdanylenko.excel.converter;

import com.mdanylenko.excel.exception.TypeCastException;

/**
 * Created by IntelliJ IDEA.<br/>
 * User: Max Danylenko<br/>
 * Date: 27.11.2015<br/>
 * Time: 20:42<br/>
 * To change this template use File | Settings | File Templates.
 */
public interface TypeConverter<T> {

    T convert(String value) throws TypeCastException;

}
