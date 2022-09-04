package com.mdanylenko.excel.parcer.handler;

import com.mdanylenko.excel.model.SheetDesc;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import java.util.ArrayList;
import java.util.List;

public class WorkBookHandler extends DefaultHandler {

    private List<SheetDesc> sheets = new ArrayList<>();
    private String content;
    private SheetDesc sheetDesc;

    public void startElement(String uri, String localName, String name, Attributes attributes) throws SAXException {

        if (name.equals("sheet")) {
            sheetDesc = new SheetDesc();

            sheetDesc.setSheedId(Integer.parseInt(attributes.getValue("sheetId")));
            sheetDesc.setSheedName(attributes.getValue("name"));
            sheetDesc.setSheedRId(attributes.getValue("r:id"));

            sheets.add(sheetDesc);
        }
    }

    public void endElement(String uri, String localName, String name) throws SAXException {

    }

    public void characters(char[] ch, int start, int length) throws SAXException {
        content += new String(ch, start, length);
    }

    public List<SheetDesc> getSheets() {
        return sheets;
    }

    public void setSheets(List<SheetDesc> sheets) {
        this.sheets = sheets;
    }
}