package com.mdanylenko.excel.context;

import com.mdanylenko.excel.annotation.Sheet;
import com.mdanylenko.excel.exception.ConfigException;
import com.mdanylenko.excel.exception.PrepareContextException;
import com.mdanylenko.excel.model.SheetDescription;
import com.mdanylenko.excel.parcer.LazySaxExcelParser;

import java.io.File;
import java.util.Set;

import static com.mdanylenko.excel.exception.ErrorCode.CONTEXT_CLASS_DEFINE;

public class ExcelContext {

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

    public LazySaxExcelParser getParserSax(String path) throws ConfigException {
        return new LazySaxExcelParser(path);
    }

    public LazySaxExcelParser getParserSax(File file) throws ConfigException {
        return new LazySaxExcelParser(file);
    }

    public Set<Class<?>> getClasses() {
        return classes;
    }
}
