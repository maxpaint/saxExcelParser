package com.mdanylenko.excel.converter;

import com.mdanylenko.excel.exception.TypeCastException;

import static com.mdanylenko.excel.util.StringUtil.isEmpty;

/**
 * Created by IntelliJ IDEA.<br/>
 * User: Max Danylenko<br/>
 * Date: 23.12.2015<br/>
 * Time: 0:15<br/>
 * To change this template use File | Settings | File Templates.
 */
public class DoubleConverter implements TypeConverter<Double> {

    @Override
    public Double convert(String value) throws TypeCastException {
        if(isEmpty(value))
            return null;

        try{
            return Double.parseDouble(value);
        }catch (Throwable e){
            throw new TypeCastException(e.getMessage(), e);
        }
    }
}
