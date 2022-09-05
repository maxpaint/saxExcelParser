package com.mdanylenko.excel.parcer.handler;

import com.mdanylenko.excel.model.SheetDescription;
import org.apache.poi.xssf.model.SharedStringsTable;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BlockingQueue;

public class ArraySaxHandler  extends BaseHandler {

    private List<Throwable> exceptionsHandler;
    private boolean isEmpty = true;
    private List row;

    private SharedStringsTable sst;
    private StringBuffer  cellContent = new StringBuffer();
    private boolean nextIsValue;

    private BlockingQueue blockingQueue;
    private SheetDescription sheetDescription;

    public ArraySaxHandler(SharedStringsTable sst) {
        this.sst = sst;
    }

    public void startElement(String uri, String localName, String name, Attributes attributes) {

        if(name.equals("row")){
            String rowStringNumber = attributes.getValue("r");
            isEmpty = true;
            row = new ArrayList<>();
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
            String columnName = cellNumber.replaceAll("\\d", "");
            // Figure out if the value is an index in the SST

            setType(attributes);

        }
        // Clear contents cache
        cellContent.setLength(0);
    }

    public void endElement(String uri, String localName, String name) {
        if(name.equals("row")){
            if(!isEmpty){
                try {
                    blockingQueue.put(row.toArray());
                } catch (InterruptedException e) {
                    exceptionsHandler.add(e);
                }
            }
        }

        // v => contents of a cell
        // Output after we've seen the string contents
        if(name.equals("v") || name.equals("t")) {//&& !StringUtil.isEmpty(cellContent)
            isEmpty = false;

            String value = getTypeData(cellContent.toString(), sst);

            row.add(value);

        }
    }

    public void characters(char[] ch, int start, int length) throws SAXException {
        if (nextIsValue)
            cellContent.append( ch, start, length );
    }

    public void setBlockingQueue(BlockingQueue blockingQueue) {
        this.blockingQueue = blockingQueue;
    }

    public void setExceptionsHandler(List<Throwable> exceptionsHandler) {
        this.exceptionsHandler = exceptionsHandler;
    }

    public void setSheetDescription(SheetDescription sheetDescription) {
        this.sheetDescription = sheetDescription;
    }
}