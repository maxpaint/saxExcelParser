package com.mdanylenko.excel.converter;

import com.mdanylenko.excel.exception.TypeCastException;

import static com.mdanylenko.excel.util.StringUtil.isEmpty;
import static java.util.Objects.isNull;

/**
 * Created by IntelliJ IDEA.<br/>
 * User: mdanylenko<br/>
 * Date: 10.12.2015<br/>
 * Time: 16:03<br/>
 * To change this template use File | Settings | File Templates.
 */
public class FloatConverter implements TypeConverter<Float> {

    @Override
    public Float convert(String value) throws TypeCastException {
        if(isEmpty(value))
            return null;

        try{
            return Float.parseFloat(value);
        }catch (Throwable e){
            throw new TypeCastException(e.getMessage(), e);
        }
    }
}
