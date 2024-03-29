package com.mdanylenko.dto;

import com.mdanylenko.excel.annotation.Column;
import com.mdanylenko.excel.annotation.Sheet;
import com.mdanylenko.excel.converter.IntegerConverter;


@Sheet(
        sheetName = "Data1",
        hasHeader = true
)
public class Annotated {

    @Column(columnName = "A", defaultValue = "max Test1")
    private String fieldA;

    @Column(columnName = "B",
            required = true,
            converter = IntegerConverter.class)
    private Integer fieldB;


    public String getFieldA() {
        return fieldA;
    }

    public void setFieldA(String fieldA) {
        this.fieldA = fieldA;
    }

    public Integer getFieldB() {
        return fieldB;
    }

    public void setFieldB(Integer fieldB) {
        this.fieldB = fieldB;
    }
}
