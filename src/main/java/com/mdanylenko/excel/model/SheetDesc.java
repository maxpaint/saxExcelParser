package com.mdanylenko.excel.model;

/**
 * Created by IntelliJ IDEA.<br/>
 * User: Max Danylenko<br/>
 * Date: 11.12.2015<br/>
 * Time: 22:27<br/>
 * To change this template use File | Settings | File Templates.
 */
public class SheetDesc {

    private int sheedId;
    private String sheedRId;
    private String sheedName;

    public int getSheedId() {
        return sheedId;
    }

    public void setSheedId(int sheedId) {
        this.sheedId = sheedId;
    }

    public String getSheedRId() {
        return sheedRId;
    }

    public void setSheedRId(String sheedRId) {
        this.sheedRId = sheedRId;
    }

    public String getSheedName() {
        return sheedName;
    }

    public void setSheedName(String sheedName) {
        this.sheedName = sheedName;
    }
}
