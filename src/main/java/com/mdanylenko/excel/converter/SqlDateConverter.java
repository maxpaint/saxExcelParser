package com.mdanylenko.excel.converter;

import com.mdanylenko.excel.exception.TypeCastException;

import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Locale;

import static com.mdanylenko.excel.util.StringUtil.isEmpty;

/**
 * Created by IntelliJ IDEA.<br/>
 * User: mdanylenko<br/>
 * Date: 10.12.2015<br/>
 * Time: 16:05<br/>
 * To change this template use File | Settings | File Templates.
 */
public class SqlDateConverter implements DateConverter<Date> {

    private SimpleDateFormat defaultFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);

    @Override
    public Date convert(String value) throws TypeCastException {
        if(isEmpty(value))
            return  null;


        Date result = null;
        try {
            result = new Date( defaultFormat.parse(value).getTime() );
            return result;
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
            return new Date( dateFormat.parse(value).getTime() );
        } catch (ParseException e) {
            throw new TypeCastException(e.getMessage(), e);
        }
    }
}
