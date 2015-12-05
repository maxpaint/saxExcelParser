package com.mdanylenko.excel.util;

/**
 * Created by IntelliJ IDEA.<br/>
 * User: Max Danylenko<br/>
 * Date: 29.11.2015<br/>
 * Time: 18:31<br/>
 * To change this template use File | Settings | File Templates.
 */
public class StringUtil {

    public static boolean isEmpty(String value){
        return !(value != null && ! value.trim().isEmpty());

    }
}
