package com.mdanylenko.parse;


import com.mdanylenko.dto.Employee;
import com.mdanylenko.dto.Job;
import com.mdanylenko.excel.parcer.LazySaxExcelParser;
import junit.framework.Assert;
import org.junit.Test;

import java.io.File;
import java.util.concurrent.BlockingQueue;

/**
 * Created by IntelliJ IDEA.<br/>
 * User:  Max Danylenko<br/>
 * Date: 10.12.2015<br/>
 * Time: 16:52<br/>
 * To change this template use File | Settings | File Templates.
 */

public class SheetTest extends Assert {


    @Test
    public void sheetName()  {
        try{
            //Get file from resources folder
            ClassLoader classLoader = getClass().getClassLoader();
            File file = new File(classLoader.getResource("example.xlsx").getFile());

            LazySaxExcelParser parser = new LazySaxExcelParser(file);
            BlockingQueue<Employee> queue = parser.selectSheet(Employee.class, 10_000);
            parser.parseSheet();

            assertTrue(parser.getExceptionsHandler().size() == 0);
        }catch (Throwable e){
            assertNull(e);
        }
    }

    @Test
    public void sheetId()  {
        try{
            //Get file from resources folder
            ClassLoader classLoader = getClass().getClassLoader();
            File file = new File(classLoader.getResource("example.xlsx").getFile());

            LazySaxExcelParser parser = new LazySaxExcelParser(file);
            BlockingQueue<Job> queue = parser.selectSheet(Job.class, 10_000);
            parser.parseSheet();

            assertTrue(parser.getExceptionsHandler().size() == 0);
        }catch (Throwable e){
            assertNull(e);
        }
    }
}
