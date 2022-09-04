package com.mdanylenko.excel.converter;

import com.mdanylenko.excel.exception.TypeCastException;
import com.mdanylenko.excel.util.StringUtil;

import static com.mdanylenko.excel.util.StringUtil.isEmpty;

public class IntegerConverter implements TypeConverter<Integer> {

    @Override
    public Integer convert(String value) throws TypeCastException {
        if (isEmpty(value)) {
            return null;
        }

        try {
            return Integer.parseInt(value);
        } catch (Throwable e) {
            throw new TypeCastException(e.getMessage(), e);
        }
    }
}
