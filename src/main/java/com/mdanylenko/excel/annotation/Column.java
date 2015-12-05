package com.mdanylenko.excel.annotation;

import com.mdanylenko.excel.converter.StringConverter;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.LOCAL_VARIABLE;

/**
 * Created by IntelliJ IDEA.<br/>
 * User: Max Danylenko<br/>
 * Date: 27.11.2015<br/>
 * Time: 19:15<br/>
 * To change this template use File | Settings | File Templates.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(value = {FIELD, LOCAL_VARIABLE})
public  @interface Column {


    String columnName() default "";
    String columnIndex() default "";
    boolean required() default false;
    String defaultValue() default "";
    Class converter() default StringConverter.class;



}

