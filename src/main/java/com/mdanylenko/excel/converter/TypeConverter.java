package com.mdanylenko.excel.converter;

import com.mdanylenko.excel.exception.TypeCastException;

public interface TypeConverter<T> {

    T convert(String value) throws TypeCastException;

}
