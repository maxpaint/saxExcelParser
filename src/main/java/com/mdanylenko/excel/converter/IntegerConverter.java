package com.mdanylenko.excel.converter;

import com.mdanylenko.excel.util.StringUtil;

/**
 * Created by IntelliJ IDEA.<br/>
 * User: Max Danylenko<br/>
 * Date: 01.12.2015<br/>
 * Time: 0:07<br/>
 * To change this template use File | Settings | File Templates.
 */
public class IntegerConverter implements TypeConverter<Integer> {

    @Override
    public Integer convert(String value) {
        if(StringUtil.isEmpty(value)){
            return null;
        }

        return Integer.parseInt(value);
    }
}
