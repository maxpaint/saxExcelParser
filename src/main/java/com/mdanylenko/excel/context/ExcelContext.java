package com.mdanylenko.excel.context;

import com.mdanylenko.excel.annotation.Sheet;
import com.mdanylenko.excel.exception.ConfigException;
import com.mdanylenko.excel.exception.PrepareContextException;
import com.mdanylenko.excel.model.SheetDescription;
import com.mdanylenko.excel.parcer.LazySaxExcelParser;

import java.io.File;
import java.util.Set;

import static com.mdanylenko.excel.exception.ErrorCode.CONTEXT_CLASS_DEFINE;

/**
 * Created by IntelliJ IDEA.<br/>
 * User: Max Danylenko<br/>
 * Date: 27.11.2015<br/>
 * Time: 21:21<br/>
 * To change this template use File | Settings | File Templates.
 */
public class ExcelContext {

    /*private SheetDescription sheet;*/

    private  Set<Class<?>> classes;

    public static ExcelContext prepareContext(String contextPath) throws PrepareContextException {
        ExcelContext context = new ExcelContext();

        HelperClassLoader helperClassLoader = HelperClassLoader.loadResorce(contextPath);

        context.classes = helperClassLoader.getTypesAnnotatedWith(Sheet.class);

        if(context.classes.isEmpty()){
            throw new PrepareContextException(String.format(CONTEXT_CLASS_DEFINE, contextPath));
        }

        return context;
    }


    /*public void setSheet(SheetDescription sheet) {
        this.sheet = sheet;
    }

    public SheetDescription getSheet() {
        return sheet;
    }*/

    public LazySaxExcelParser getParserSax(String path) throws ConfigException {
        return new LazySaxExcelParser(path, this);
    }

    public LazySaxExcelParser getParserSax(File file) throws ConfigException {
        return new LazySaxExcelParser(file, this);
    }

    public Set<Class<?>> getClasses() {
        return classes;
    }
}
