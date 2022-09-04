package com.mdanylenko.parse;


import com.mdanylenko.dto.Employee;
import com.mdanylenko.dto.Job;
import com.mdanylenko.excel.parcer.LazySaxExcelParser;
import org.junit.Assert;
import org.junit.Test;

import java.io.File;
import java.util.concurrent.BlockingQueue;

import static junit.framework.TestCase.assertTrue;


public class SheetTest {


    @Test
    public void sheetName()  {
        try{
            //Get file from resources folder
            ClassLoader classLoader = getClass().getClassLoader();
            File file = new File(classLoader.getResource("example.xlsx").getFile());

            LazySaxExcelParser parser = new LazySaxExcelParser(file);
            BlockingQueue<Employee> queue = parser.selectSheet(Employee.class, 10_000);
            parser.parseSheet();

            while( parser.isProcessFinished().get() ) {
                assertTrue(parser.getExceptionsHandler().size() == 0);
            }
        }catch (Throwable e){
            e.printStackTrace();
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

            while( parser.isProcessFinished().get() ) {
                assertTrue(parser.getExceptionsHandler().size() == 0);
            }
        }catch (Throwable e){
            e.printStackTrace();
        }
    }
}
