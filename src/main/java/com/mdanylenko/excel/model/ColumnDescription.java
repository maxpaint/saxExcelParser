package com.mdanylenko.excel.model;

import com.mdanylenko.excel.converter.TypeConverter;

import java.lang.reflect.Field;

/**
 * Created by IntelliJ IDEA.<br/>
 * User: Max Danylenko<br/>
 * Date: 29.11.2015<br/>
 * Time: 19:05<br/>
 * To change this template use File | Settings | File Templates.
 */
public class ColumnDescription {

    private Field field;
    private String columnIndex;
    private String columnName;
    private boolean isRequired;
    private String defaultValue;
    private TypeConverter converter;

    private ColumnDescription(Field field, String columnIndex, String columnName, boolean isRequired, String defaultValue, TypeConverter converter) {
        this.field = field;
        this.columnIndex = columnIndex;
        this.columnName = columnName;
        this.isRequired = isRequired;
        this.defaultValue = defaultValue;
        this.converter = converter;
    }

    public Field getField() {
        return field;
    }

    public void setField(Field field) {
        this.field = field;
    }

    public String getColumnIndex() {
        return columnIndex;
    }

    public void setColumnIndex(String columnIndex) {
        this.columnIndex = columnIndex;
    }

    public String getColumnName() {
        return columnName;
    }

    public void setColumnName(String columnName) {
        this.columnName = columnName;
    }

    public boolean isRequired() {
        return isRequired;
    }

    public void setIsRequired(boolean isRequired) {
        this.isRequired = isRequired;
    }

    public String getDefaultValue() {
        return defaultValue;
    }

    public void setDefaultValue(String defaultValue) {
        this.defaultValue = defaultValue;
    }

    public TypeConverter getConverter() {
        return converter;
    }

    public void setConverter(TypeConverter converter) {
        this.converter = converter;
    }

    public static class Builder{

        private Field field;
        private String columnIndex;
        private String columnName;
        private boolean isRequired;
        private String defaultValue;
        private TypeConverter converter;

        public Builder setField(Field field) {
            this.field = field;
            return this;
        }

        public Builder setColumnIndex(String columnIndex) {
            this.columnIndex = columnIndex;
            return this;
        }

        public Builder setColumnName(String columnName) {
            this.columnName = columnName;
            return this;
        }

        public Builder setIsRequired(boolean isRequired) {
            this.isRequired = isRequired;
            return this;
        }

        public Builder setDefaultValue(String defaultValue) {
            this.defaultValue = defaultValue;
            return this;
        }

        public Builder setConverter(TypeConverter converter) {
            this.converter = converter;
            return this;
        }

        public ColumnDescription build(){
            return new ColumnDescription(field, columnIndex, columnName, isRequired, defaultValue, converter);
        }
    }


}
