package com.mdanylenko.parse;

import com.mdanylenko.dto.JobRequiredNegative;
import com.mdanylenko.dto.JobRequiredPositive;
import com.mdanylenko.excel.context.ExcelContext;
import com.mdanylenko.excel.parcer.LazySaxExcelParser;
import junit.framework.Assert;
import org.junit.Test;

import java.io.File;
import java.util.concurrent.BlockingQueue;

/**
 * Created by IntelliJ IDEA.<br/>
 * User: Max Danylenko<br/>
 * Date: 10.12.2015<br/>
 * Time: 21:59<br/>
 * To change this template use File | Settings | File Templates.
 */
public class ColumnTest extends Assert {


    @Test
    public void requiredFieldsPositive()  {
        try{
            //Get file from resources folder
            ClassLoader classLoader = getClass().getClassLoader();
            File file = new File(classLoader.getResource("required.xlsx").getFile());

            ExcelContext context = ExcelContext.prepareContext("com.mdanylenko.dto");
            LazySaxExcelParser parser = context.getParserSax(file);
            BlockingQueue<JobRequiredPositive> queue = parser.selectSheet(JobRequiredPositive.class, 10_000);
            parser.parseSheet();

            assertTrue(parser.getExceptionsHandler().size() == 0);
        }catch (Throwable e){
            e.printStackTrace();
            assertNull(e);
        }
    }

    @Test
    public void requiredFieldsNegative()  {
        try{
            //Get file from resources folder
            ClassLoader classLoader = getClass().getClassLoader();
            File file = new File(classLoader.getResource("required.xlsx").getFile());

            ExcelContext context = ExcelContext.prepareContext("com.mdanylenko.dto");
            LazySaxExcelParser parser = context.getParserSax(file);
            BlockingQueue<JobRequiredNegative> queue = parser.selectSheet(JobRequiredNegative.class, 10_000);
            parser.parseSheet();

            assertTrue(parser.getExceptionsHandler().size() == 3);
        }catch (Throwable e){
            e.printStackTrace();
            assertNull(e);
        }
    }

}