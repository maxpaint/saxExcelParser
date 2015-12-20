package com.mdanylenko.excel.parcer.handler;

import com.mdanylenko.excel.annotation.Column;
import com.mdanylenko.excel.converter.DateTypeConverter;
import com.mdanylenko.excel.converter.TypeConverter;
import com.mdanylenko.excel.exception.ErrorCode;
import com.mdanylenko.excel.exception.ParserException;
import com.mdanylenko.excel.exception.TypeCastException;
import com.mdanylenko.excel.model.ColumnDescription;
import com.mdanylenko.excel.model.SheetDescription;
import org.apache.poi.xssf.model.SharedStringsTable;
import org.apache.poi.xssf.usermodel.XSSFRichTextString;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import java.lang.reflect.Field;
import java.util.*;
import java.util.concurrent.BlockingQueue;

import static com.mdanylenko.excel.exception.ErrorCode.REQURED_ERROR;
import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;

/**
 * Created by IntelliJ IDEA.<br/>
 * User: Max Danylenko<br/>
 * Date: 19.12.2015<br/>
 * Time: 13:44<br/>
 * To change this template use File | Settings | File Templates.
 */
public class SaxSheetHandler extends DefaultHandler {

private List<Throwable> exceptionsHandler;

private String columnName = "";
private Set<Field> reguiredFields = new HashSet<>();
private Map<Field, ColumnDescription> defaultFields = new HashMap<>();
private int rowNumber;
private boolean isEmpty = true;
private Map<String, ColumnDescription> columnMap;
private Object row;

private SharedStringsTable sst;
private String cellContent;
private boolean nextIsValue;

private BlockingQueue blockingQueue;
private SheetDescription sheetDescription;

        public SaxSheetHandler(SharedStringsTable sst) {
            this.sst = sst;
            this.columnMap = new HashMap<>();

        }

        public void startElement(String uri, String localName, String name, Attributes attributes) throws SAXException {

            if(name.equals("row")){
                String rowStringNumber = attributes.getValue("r");
                rowNumber = Integer.parseInt(rowStringNumber);
                isEmpty = true;
                try {
                    row = sheetDescription.getType().newInstance();
                } catch (IllegalAccessException | InstantiationException e) {
                    exceptionsHandler.add(e);
                }
            }

            // c => cell
            if(name.equals("c")) {
                // Print the cell reference
                String cellNumber = attributes.getValue("r");
                columnName = cellNumber.replaceAll("\\d", "");
                // Figure out if the value is an index in the SST
                String cellType = attributes.getValue("t");

                nextIsValue = nonNull(cellType) && cellType.equals("s") ;//|| cellType.equals("n")
            }
            // Clear contents cache
            cellContent = "";
        }

        public void endElement(String uri, String localName, String name) throws SAXException {
            // Process the last contents as required.
            // Do now, as characters() may be called more than once
            if(nextIsValue) {
                int idx = Integer.parseInt(cellContent);
                cellContent = new XSSFRichTextString(sst.getEntryAt(idx)).toString();
                nextIsValue = false;
            }

            if(name.equals("row")){
                if( !isEmpty ){
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
            if(name.equals("v") ) {//&& !StringUtil.isEmpty(cellContent)
                isEmpty = false;
                // start new row,  starts at column position A

                ColumnDescription description = columnMap.get(columnName);
                try {
                    if(nonNull(description)){
                        Field field = description.getField();
                        TypeConverter converter = description.getConverter();
                        try {
                            if( converter instanceof DateTypeConverter){
                                DateTypeConverter dateConverter = (DateTypeConverter) converter;
                                Column column = field.getAnnotation(Column.class);
                                field.set(row, dateConverter.convert(cellContent, column.format()));
                            }else{
                                field.set(row, converter.convert(cellContent));
                            }

                        } catch (TypeCastException e) {
                            exceptionsHandler.add(new TypeCastException(String.format(ErrorCode.CAST_ERROR, converter, field, cellContent ), e));
                        }
                    }


                }  catch (IllegalAccessException e) {
                    exceptionsHandler.add(e);
                }
            }
        }

        public void characters(char[] ch, int start, int length) throws SAXException {
            cellContent += new String(ch, start, length);
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