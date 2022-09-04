package com.mdanylenko.excel.converter;

import com.mdanylenko.excel.exception.TypeCastException;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import static com.mdanylenko.excel.util.StringUtil.isEmpty;

public class DateConverter implements DateTypeConverter<Date> {

    private SimpleDateFormat defaultFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);

    @Override
    public Date convert(String value) throws TypeCastException {
        if(isEmpty(value))
            return  null;


        try {
           return defaultFormat.parse(value);
        } catch (ParseException e) {
            throw new TypeCastException(e.getMessage(), e);
        }
    }

    @Override
    public Date convert(String value, String format) throws TypeCastException {
        if(isEmpty(value))
            return  null;

        SimpleDateFormat dateFormat = new SimpleDateFormat(format, Locale.ENGLISH);
        try {
            return dateFormat.parse(value);
        } catch (ParseException e) {
            throw new TypeCastException(e.getMessage(), e);
        }
    }
}
