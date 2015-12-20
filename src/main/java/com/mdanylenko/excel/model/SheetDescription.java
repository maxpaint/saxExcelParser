package com.mdanylenko.excel.model;

import java.util.*;

/**
 * Created by IntelliJ IDEA.<br/>
 * User: Max Danylenko<br/>
 * Date: 29.11.2015<br/>
 * Time: 17:47<br/>
 * To change this template use File | Settings | File Templates.
 */
public class SheetDescription {

    private Class<?> type;

    private String sheetName;

    private String sheetId;

    private Boolean header;

    private Map<String, ColumnDescription> columnMap;

    private List<ColumnDescription> columns;

    public String getSheetName() {
        return sheetName;
    }

    public void setSheetName(String sheetName) {
        this.sheetName = sheetName;
    }

    public String getSheetId() {
        return sheetId;
    }

    public void setSheetId(String sheetId) {
        this.sheetId = sheetId;
    }

    public Boolean hasHeader() {
        return header;
    }

    public void setHeader(Boolean header) {
        this.header = header;
    }

    public Class<?> getType() {
        return type;
    }

    public void setType(Class<?> type) {
        this.type = type;
    }

    public void addColumn(ColumnDescription column) {
        if(Objects.isNull(columns)){
            columns = new ArrayList<>();
        }

        if(Objects.isNull(columnMap)){
            columnMap = new HashMap<>();
        }

        columnMap.put(column.getColumnName(), column);
        columns.add(column);
    }

    public Map<String, ColumnDescription> getColumnMap() {
        return columnMap;
    }

    public List<ColumnDescription> getColumns() {
        return columns;
    }
}
