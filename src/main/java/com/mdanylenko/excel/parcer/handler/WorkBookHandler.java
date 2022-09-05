package com.mdanylenko.excel.parcer.handler;

import com.mdanylenko.excel.model.SheetDesc;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import java.util.ArrayList;
import java.util.List;

public class WorkBookHandler extends DefaultHandler {

    private List<SheetDesc> sheets = new ArrayList<>();

    public void startElement(String uri, String localName, String name, Attributes attributes) {

        if (name.equals("sheet")) {
            SheetDesc sheetDesc = new SheetDesc();

            sheetDesc.setSheedId(Integer.parseInt(attributes.getValue("sheetId")));
            sheetDesc.setSheedName(attributes.getValue("name"));
            sheetDesc.setSheedRId(attributes.getValue("r:id"));

            sheets.add(sheetDesc);
        }
    }

    public List<SheetDesc> getSheets() {
        return sheets;
    }
}