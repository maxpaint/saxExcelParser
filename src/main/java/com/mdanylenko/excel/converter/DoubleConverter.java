package com.mdanylenko.excel.converter;

import com.mdanylenko.excel.exception.TypeCastException;

import static com.mdanylenko.excel.util.StringUtil.isEmpty;

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
