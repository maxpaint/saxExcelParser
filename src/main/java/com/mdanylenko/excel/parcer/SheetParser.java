package com.mdanylenko.excel.parcer;

import com.mdanylenko.excel.exception.PrepareContextException;

import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.atomic.AtomicBoolean;

public interface SheetParser {

    <T> BlockingQueue<T> selectSheet(Class<T> type, String sheet, boolean isHeader, Integer fetchSize) throws PrepareContextException;
    <T> BlockingQueue<T> selectSheet(Class<T> type, Integer fetchSize) throws PrepareContextException;
    void parseSheet();
    List<Throwable> getExceptionsHandler();
    AtomicBoolean isProcessFinished();
}
