package com.mdanylenko.excel.converter;

public class StringConverter implements TypeConverter<String> {

    @Override
    public String convert(String value) {
        return value;
    }
}
