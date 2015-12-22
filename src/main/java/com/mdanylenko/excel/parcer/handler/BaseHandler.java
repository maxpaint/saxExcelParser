package com.mdanylenko.excel.parcer.handler;

import com.mdanylenko.excel.model.XssfDataType;
import org.apache.poi.xssf.model.SharedStringsTable;
import org.apache.poi.xssf.usermodel.XSSFRichTextString;
import org.xml.sax.Attributes;
import org.xml.sax.helpers.DefaultHandler;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

/**
 * Created by IntelliJ IDEA.<br/>
 * User: Max Danylenko<br/>
 * Date: 21.12.2015<br/>
 * Time: 22:22<br/>
 * To change this template use File | Settings | File Templates.
 */
public class BaseHandler extends DefaultHandler {

    protected XssfDataType nextDataType;

    protected void setType(Attributes attributes){
        // Set up defaults.
        this.nextDataType = XssfDataType.NUMBER;
        String cellType = attributes.getValue("t");
        if ("b".equals(cellType))
            nextDataType = XssfDataType.BOOL;
        else if ("e".equals(cellType))
            nextDataType = XssfDataType.ERROR;
        else if ("inlineStr".equals(cellType))
            nextDataType = XssfDataType.INLINESTR;
        else if ("s".equals(cellType))
            nextDataType = XssfDataType.SSTINDEX;
        else if ("str".equals(cellType))
            nextDataType = XssfDataType.FORMULA;

    }

    protected String getTypeData(String cellContent, SharedStringsTable sst){

        switch (nextDataType) {

            case BOOL:
                char first = cellContent.charAt(0);
                cellContent = first == '0' ? "FALSE" : "TRUE";
                break;

            case ERROR:
                cellContent = "\"ERROR:" + cellContent.toString() + '"';
                break;

            case FORMULA:
                // A formula could result in a string value,
                // so always add double-quote characters.
                cellContent = '"' + cellContent + '"';
                break;

            case INLINESTR:
                XSSFRichTextString rtsi = new XSSFRichTextString(cellContent);
                cellContent = '"' + rtsi.toString() + '"';
                break;

            case SSTINDEX:
                try {
                    int idx = Integer.parseInt(cellContent);
                    XSSFRichTextString rtss = new XSSFRichTextString( sst.getEntryAt(idx) );
                    cellContent =  rtss.toString();
                } catch (NumberFormatException ex) {
                    System.out.println("Failed to parse SST index '" + cellContent + "': " + ex.toString());
                }
                break;

            case NUMBER:
                /*String n = value.toString();
                if (this.formatString != null)
                    thisStr = formatter.formatRawCellContents(Double
                                    .parseDouble(n), this.formatIndex,
                            this.formatString);
                else
                    thisStr = n;*/
                break;

            default:
                cellContent = "(TODO: Unexpected type: " + nextDataType + ")";
                break;
        }

        return cellContent;
    }

    public Boolean isInner(Class<?>  clazz){
        return clazz.toString().contains("$");
    }

    public Object createInnerInstance(Class<?> inner) throws IllegalAccessException, InvocationTargetException, InstantiationException, NoSuchMethodException, ClassNotFoundException {
        String baseName = inner.toString().replace("class ", "");
        baseName = baseName.substring(0, baseName.indexOf("$"));

        Class<?> enclosingClass = Class.forName(baseName);
        Object enclosingInstance = enclosingClass.newInstance();

        Constructor<?> ctor = inner.getDeclaredConstructor(enclosingClass);

        Object innerInstance = ctor.newInstance(enclosingInstance);

        return innerInstance;

    }
}
