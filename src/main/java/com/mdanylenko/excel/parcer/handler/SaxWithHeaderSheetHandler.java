package com.mdanylenko.excel.parcer.handler;

import com.mdanylenko.excel.annotation.Column;
import com.mdanylenko.excel.converter.DateTypeConverter;
import com.mdanylenko.excel.converter.TypeConverter;
import com.mdanylenko.excel.exception.ErrorCode;
import com.mdanylenko.excel.exception.ParserException;
import com.mdanylenko.excel.exception.TypeCastException;
import com.mdanylenko.excel.model.ColumnDescription;
import com.mdanylenko.excel.model.SheetDescription;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.model.SharedStringsTable;
import org.apache.poi.xssf.usermodel.XSSFRichTextString;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.*;
import java.util.concurrent.BlockingQueue;

import static com.mdanylenko.excel.exception.ErrorCode.REQURED_ERROR;
import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;

public class SaxWithHeaderSheetHandler extends BaseHandler {

    private List<Throwable> exceptionsHandler;

    private String columnName = "";
    private Set<Field> reguiredFields = new HashSet<>();
    private Map<Field, ColumnDescription> defaultFields = new HashMap<>();
    private int rowNumber;
    private boolean isEmpty = true;
    private Map<String, ColumnDescription> columnMap;
    private Object row;

    private SharedStringsTable sst;
    private StringBuffer cellContent = new StringBuffer();
    private boolean nextIsValue;

    private BlockingQueue blockingQueue;
    private SheetDescription sheetDescription;

    public SaxWithHeaderSheetHandler(SharedStringsTable sst) {
        this.sst = sst;
        this.columnMap = new HashMap<>();

    }

    public void startElement(String uri, String localName, String name, Attributes attributes) throws SAXException {
        if(name.equals("row")){
            String rowStringNumber = attributes.getValue("r");
            rowNumber = Integer.parseInt(rowStringNumber);
            isEmpty = true;
            if(rowNumber != 1){
                try {
                    if( isInner( sheetDescription.getType() ) ){
                        row = createInnerInstance(sheetDescription.getType());
                    }else{
                        row = sheetDescription.getType().newInstance();
                    }

                } catch (InvocationTargetException | NoSuchMethodException | ClassNotFoundException | IllegalAccessException | InstantiationException e) {
                   exceptionsHandler.add(e);
                    e.printStackTrace();
                }
            }
        }

        if ("inlineStr".equals(name) || "v".equals(name)) {
            nextIsValue = true;
            // Clear contents cache
            cellContent.setLength(0);
        }

        // c => cell
        if(name.equals("c")) {
            // Print the cell reference
            String cellNumber = attributes.getValue("r");
            columnName = cellNumber.replaceAll("\\d", "");

            setType(attributes);
        }
        // Clear contents cache
        cellContent.setLength(0);
    }

    public void endElement(String uri, String localName, String name) throws SAXException {
        if(name.equals("row")){
            if(rowNumber != 1 && !isEmpty){
                try {
                    for(Field field : reguiredFields){
                            if(isNull(field.get(row))){
                                Column column = field.getDeclaredAnnotation(Column.class);
                                exceptionsHandler.add(new ParserException(String.format(REQURED_ERROR, column.columnName(), rowNumber)));
                                return;
                            }
                    }

                    for(Map.Entry<Field, ColumnDescription> entry : defaultFields.entrySet()){
                        Field field = entry.getKey();
                        ColumnDescription desc = entry.getValue();

                        if(isNull(field.get(row))){
                            try {
                                TypeConverter converter = desc.getConverter();
                                if( converter instanceof DateTypeConverter){
                                    DateTypeConverter dateConverter = (DateTypeConverter) converter;
                                    Column column = field.getAnnotation(Column.class);
                                    field.set(row, dateConverter.convert(desc.getDefaultValue(), column.format()));
                                }else{
                                    field.set(row, converter.convert(desc.getDefaultValue()));
                                }
                            } catch (TypeCastException e) {
                                exceptionsHandler.add(new TypeCastException(String.format(ErrorCode.CAST_ERROR, desc.getConverter(), field, cellContent), e));
                            }
                        }
                    }

                    blockingQueue.put(row);
                } catch (IllegalAccessException | InterruptedException e) {
                    exceptionsHandler.add(e);
                }
            }
        }

        // v => contents of a cell
        // Output after we've seen the string contents
        if(name.equals("v") || name.equals("t")) {//&& !StringUtil.isEmpty(cellContent)
            isEmpty = false;

            String value = getTypeData(cellContent.toString(), sst);

            if(rowNumber == 1) {
                ColumnDescription columnDescription = sheetDescription.getColumnMap().get(value);
                if(isNull(columnDescription)){
                    return;
                }

                if(columnDescription.isRequired()){
                    if( !reguiredFields.contains(columnDescription.getField()) ){
                        reguiredFields.add(columnDescription.getField());
                    }
                }

                if(nonNull(columnDescription.getDefaultValue())){
                    defaultFields.put(columnDescription.getField(), columnDescription);
                }

                columnMap.put(columnName, columnDescription);
                return;
            }

            ColumnDescription description = columnMap.get(columnName);
            try {
                if(nonNull(description)){
                    Field field = description.getField();
                    TypeConverter converter = description.getConverter();
                    try {
                        if( converter instanceof DateTypeConverter){
                            DateTypeConverter dateConverter = (DateTypeConverter) converter;
                            Column column = field.getAnnotation(Column.class);
                            field.set(row, dateConverter.convert(value, column.format()));
                        }else{
                            field.set(row, converter.convert(value));
                        }

                    } catch (TypeCastException e) {
                        exceptionsHandler.add(new TypeCastException(String.format(ErrorCode.CAST_ERROR, converter, field, value ), e));
                    }
                }


            }  catch (IllegalAccessException e) {
                exceptionsHandler.add(e);
            }
        }
    }

    public void characters(char[] ch, int start, int length) throws SAXException {
        if (nextIsValue)
            cellContent.append( ch, start, length );
    }

    public void setBlockingQueue(BlockingQueue blockingQueue) {
        this.blockingQueue = blockingQueue;
    }

    public void setSheetDescription(SheetDescription sheetDescription) {
        this.sheetDescription = sheetDescription;
    }

    public void setExceptionsHandler(List<Throwable> exceptionsHandler) {
        this.exceptionsHandler = exceptionsHandler;
    }
}
