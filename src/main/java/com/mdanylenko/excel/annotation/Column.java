package com.mdanylenko.excel.annotation;

import com.mdanylenko.excel.converter.StringConverter;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.LOCAL_VARIABLE;

@Retention(RetentionPolicy.RUNTIME)
@Target(value = {FIELD, LOCAL_VARIABLE})
public  @interface Column {


    String columnName() default "";
    String columnIndex() default "";
    boolean required() default false;
    String defaultValue() default "";
    String format() default "";
    Class converter() default StringConverter.class;



}

