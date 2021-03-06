package com.mdanylenko.excel.converter;

import com.mdanylenko.excel.exception.TypeCastException;
import com.mdanylenko.excel.util.StringUtil;

import static com.mdanylenko.excel.util.StringUtil.isEmpty;

/**
 * Created by IntelliJ IDEA.<br/>
 * User: Max Danylenko<br/>
 * Date: 01.12.2015<br/>
 * Time: 0:07<br/>
 * To change this template use File | Settings | File Templates.
 */
public class IntegerConverter implements TypeConverter<Integer> {

    @Override
    public Integer convert(String value) throws TypeCastException {
        if(isEmpty(value)){
            return null;
        }

        try{
            return Integer.parseInt(value);
        }catch (Throwable e){
            throw new TypeCastException(e.getMessage(), e);
        }
    }
}
