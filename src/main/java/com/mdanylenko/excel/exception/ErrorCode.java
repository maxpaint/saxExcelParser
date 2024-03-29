package com.mdanylenko.excel.exception;

public class ErrorCode {

    /*
    * CONTEXT
    * */
    public static final String CONTEXT_CLASS_ARRAY_TYPE = "Can't find array class type";
    public static final String CONTEXT_CLASS_PATH = "Can't find class by this path %s";
    public static final String CONTEXT_CLASS_DEFINE = "Can't find annotation class by this path %s";
    public static final String CONTEXT_SHEET_DEFINE = "For class: %s you must define sheetName or sheetId";
    public static final String CONTEXT_WRONG_SHEET_DEFINE = "For class: %s you must define one of sheetName or sheetId";
    public static final String CONTEXT_CONVERTER = "For field: %s in class: %s yours converter must implements TypeConverter interface";
    public static final String CONTEXT_CLASS_MAPPING_ERROR = "For field: %s in class: %s you must define columnName or columnIndex";
    public static final String CONTEXT_CLASS_MAPPING = "For field: %s in class: %s you must define one of them: columnName or columnIndex";

    /*
    * CONFIG
    * */

    public static final String FILE_TYPE_ERROR =  "This file content wrong data. Try checked extension its must be .xlsx";
    public static final String REQURED_ERROR =  "PARSE ERROR, required field is null: %s at row number: %s";
    public static final String WRONG_SHEET_ID = "You define wrong sheet  is %s";


    /*
    * Parse
    * */

    public static final String CAST_ERROR =  "Incorrect type converter: %s for filed: %s that contain data: %s";

}
