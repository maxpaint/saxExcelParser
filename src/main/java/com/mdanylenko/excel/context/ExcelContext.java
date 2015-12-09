package com.mdanylenko.excel.context;

import com.mdanylenko.excel.annotation.Column;
import com.mdanylenko.excel.annotation.Sheet;
import com.mdanylenko.excel.converter.TypeConverter;
import com.mdanylenko.excel.exception.ConfigException;
import com.mdanylenko.excel.exception.PrepareContextException;
import com.mdanylenko.excel.model.ColumnDescription;
import com.mdanylenko.excel.model.SheetDescription;
import com.mdanylenko.excel.parcer.LazySaxExcelParser;

import java.io.File;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Set;

import static com.mdanylenko.excel.exception.ErrorCode.*;
import static com.mdanylenko.excel.util.StringUtil.isEmpty;

/**
 * Created by IntelliJ IDEA.<br/>
 * User: Max Danylenko<br/>
 * Date: 27.11.2015<br/>
 * Time: 21:21<br/>
 * To change this template use File | Settings | File Templates.
 */
public class ExcelContext {

    private List<SheetDescription> sheets;

    public static ExcelContext prepareContext(String contextPath) throws PrepareContextException {
        ExcelContext context = new ExcelContext();

        HelperClassLoader helperClassLoader = HelperClassLoader.loadResorce(contextPath);

        Set<Class<?>> classes = helperClassLoader.getTypesAnnotatedWith(Sheet.class);

        if(classes.isEmpty()){
            throw new PrepareContextException(String.format(CONTEXT_CLASS_DEFINE, contextPath));
        }

        for(Class<?> claz: classes){
            SheetDescription sheetDescription = new SheetDescription();
            sheetDescription.setType(claz);

            Sheet sheetDto = claz.getDeclaredAnnotation(Sheet.class);

            String sheetId = sheetDto.sheetId();
            String sheetName = sheetDto.sheetName();

            if(isEmpty(sheetId) && isEmpty( sheetName )){
                throw new PrepareContextException(String.format(CONTEXT_SHEET_DEFINE, claz));
            }

            if(!isEmpty(sheetId) && !isEmpty( sheetName )){
                throw new PrepareContextException(String.format(CONTEXT_WRONG_SHEET_DEFINE, claz));
            }

            if(!isEmpty(sheetId)){
                sheetDescription.setSheetId(sheetId);
            }

            if(!isEmpty( sheetName )){
                sheetDescription.setSheetName(sheetName);
            }

            sheetDescription.setHasHeader(sheetDto.hasHeader());

            Field[] fields = claz.getDeclaredFields();

            for (Field field : fields){
                field.setAccessible(true);
                Column annotation = field.getDeclaredAnnotation(Column.class);
                String columnIndex = annotation.columnIndex();
                String columnName = annotation.columnName();
                boolean isRequired = annotation.required();
                String defaultValue = annotation.defaultValue();
                TypeConverter converter;

                if(TypeConverter.class.isAssignableFrom(annotation.converter())) {
                    try {
                        converter = (TypeConverter) annotation.converter().newInstance();

                    } catch (IllegalAccessException | InstantiationException e) {
                        throw new PrepareContextException(e.getMessage(), e);
                    }
                } else {
                    throw new PrepareContextException(String.format(CONTEXT_CONVERTER, field, claz));
                }

                if(isEmpty(columnIndex) && isEmpty(columnName)){
                    throw new PrepareContextException(String.format(CONTEXT_CLASS_MAPPING_ERROR, field, claz));
                }

                if(!isEmpty(columnIndex) && !isEmpty(columnName)){
                    throw new PrepareContextException(String.format(CONTEXT_CLASS_MAPPING, field, claz));
                }


                sheetDescription.addColumn(new ColumnDescription.Builder()
                        .setColumnIndex(columnIndex)
                        .setColumnName(columnName)
                        .setIsRequired(isRequired)
                        .setDefaultValue(defaultValue)
                        .setConverter((converter))
                        .setField(field)
                        .build());
            }


            context.addSheet(sheetDescription);
        }

        return context;
    }


    private void addSheet(SheetDescription sheet) {
        if(Objects.isNull(sheets)){
            sheets = new ArrayList<>();
        }
        sheets.add(sheet);
    }

    public List<SheetDescription> getSheets() {
        return sheets;
    }

    public LazySaxExcelParser getParserSax(String path) throws ConfigException {
        return new LazySaxExcelParser(path, this);

    }

    public LazySaxExcelParser getParserSax(File file) throws ConfigException {
        return new LazySaxExcelParser(file, this);

    }
}
