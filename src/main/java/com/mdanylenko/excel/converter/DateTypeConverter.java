package com.mdanylenko.excel.converter;


import com.mdanylenko.excel.exception.TypeCastException;


public interface DateTypeConverter<T> extends TypeConverter<T> {


    T convert(String value, String format) throws TypeCastException;
}
