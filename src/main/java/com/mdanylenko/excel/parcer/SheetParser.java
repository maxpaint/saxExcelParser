package com.mdanylenko.excel.parcer;

import com.mdanylenko.excel.exception.ConfigException;
import com.mdanylenko.excel.exception.ParserException;

import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Created by IntelliJ IDEA.<br/>
 * User: Max Danylenko<br/>
 * Date: 29.11.2015<br/>
 * Time: 20:36<br/>
 * To change this template use File | Settings | File Templates.
 */
public interface SheetParser {

    <T> BlockingQueue<T> selectSheet(Class<T> type, Integer fetchSize);
    void parseSheet() throws ParserException;
    List<Throwable> getExceptionsHandler();
    AtomicBoolean isProcessFinished();
}
