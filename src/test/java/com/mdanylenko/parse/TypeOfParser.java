package com.mdanylenko.parse;

import com.mdanylenko.BaseTest;
import com.mdanylenko.dto.WithoutHeader;
import com.mdanylenko.dto.WithoutHeaderData;
import com.mdanylenko.excel.exception.ConfigException;
import com.mdanylenko.excel.exception.PrepareContextException;
import com.mdanylenko.excel.parcer.LazySaxExcelParser;
import org.junit.Test;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BlockingQueue;

/**
 * Created by IntelliJ IDEA.<br/>
 * User: Max Danylenko<br/>
 * Date: 19.12.2015<br/>
 * Time: 13:50<br/>
 * To change this template use File | Settings | File Templates.
 */
public class TypeOfParser extends BaseTest {

    @Test
    public void withoutHeader() throws InterruptedException, ConfigException, PrepareContextException {
        ClassLoader classLoader = getClass().getClassLoader();
        File file = new File(classLoader.getResource("withoutHeader.xlsx").getFile());

        long startTime = System.nanoTime();

        LazySaxExcelParser parser = new LazySaxExcelParser(file);
        BlockingQueue<WithoutHeader> queue = parser.selectSheet(WithoutHeader.class, 10_000);
        parser.parseSheet();

        while (!parser.isProcessFinished().get()) {

        }

        executiveTime("withoutHeader", startTime, System.nanoTime());

        assertTrue(parser.getExceptionsHandler().size() == 0);
        List<WithoutHeader> result = new ArrayList<>();
        queue.drainTo(result);
        assertEquals(107, result.size());


    }

    @Test
    public void withoutHeaderSheetName() throws ConfigException, PrepareContextException {
        ClassLoader classLoader = getClass().getClassLoader();
        File file = new File(classLoader.getResource("withoutHeader.xlsx").getFile());

        long startTime = System.nanoTime();

        LazySaxExcelParser parser = new LazySaxExcelParser(file);
        BlockingQueue<WithoutHeaderData> queue = parser.selectSheet(WithoutHeaderData.class, 10_000);
        parser.parseSheet();

        while (!parser.isProcessFinished().get()) {

        }

        executiveTime("withoutHeaderSheetName", startTime, System.nanoTime());

        assertEquals(0, parser.getExceptionsHandler().size());
        List<WithoutHeaderData> result = new ArrayList<>();
        queue.drainTo(result);
        assertEquals(107, result.size());

    }

    @Test
    public void withoutHeaderArray() throws ConfigException, PrepareContextException {
        ClassLoader classLoader = getClass().getClassLoader();
        File file = new File(classLoader.getResource("withoutHeader.xlsx").getFile());

        long startTime = System.nanoTime();

        LazySaxExcelParser parser = new LazySaxExcelParser(file);
        BlockingQueue<String[]> queue = parser.selectSheet(String[].class, "1", false, 10_000);
        parser.parseSheet();

        while (!parser.isProcessFinished().get()) {
        }

        executiveTime("withoutHeaderArray", startTime, System.nanoTime());

        assertTrue(parser.getExceptionsHandler().size() == 0);
        List<String[]> result = new ArrayList<>();
        queue.drainTo(result);
        assertEquals(107, result.size());


    }
}
