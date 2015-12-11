package com.mdanylenko.parse;

import com.mdanylenko.dto.JobRequiredDefault;
import com.mdanylenko.dto.JobRequiredNegative;
import com.mdanylenko.dto.JobRequiredPositive;
import com.mdanylenko.excel.parcer.LazySaxExcelParser;
import junit.framework.Assert;
import org.junit.Test;

import java.io.File;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
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

            LazySaxExcelParser parser =new LazySaxExcelParser(file);
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

            LazySaxExcelParser parser = new LazySaxExcelParser(file);
            BlockingQueue<JobRequiredNegative> queue = parser.selectSheet(JobRequiredNegative.class, 10_000);
            parser.parseSheet();

            assertTrue(parser.getExceptionsHandler().size() == 3);
        }catch (Throwable e){
            e.printStackTrace();
            assertNull(e);
        }
    }

    @Test
    public void defaultValue()  {
        try{
            //Get file from resources folder
            ClassLoader classLoader = getClass().getClassLoader();
            File file = new File(classLoader.getResource("required.xlsx").getFile());


            LazySaxExcelParser parser = new LazySaxExcelParser(file);
            BlockingQueue<JobRequiredDefault> queue = parser.selectSheet(JobRequiredDefault.class, 10_000);
            parser.parseSheet();

            List<JobRequiredDefault> result = new ArrayList<>();
            queue.drainTo(result);

            for (JobRequiredDefault item: result){
                if( item.getJobId().equalsIgnoreCase("JOB_ID") ){
                    SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yy", Locale.ENGLISH);
                    assertTrue( item.getJobTitle().equals("JOB_TITLE") );
                    assertTrue( item.getMinSalary().equals(1) );
                    assertTrue( item.getEndDate().equals(new Date( dateFormat.parse("11.12.15").getTime() )) );
                    assertTrue( item.getStartDate().equals( dateFormat.parse("11.12.15") ) );
                }

                if(item.getJobId().equalsIgnoreCase("HR_REP")){
                    SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yy", Locale.ENGLISH);
                    assertTrue( item.getJobTitle().equals("JOB_TITLE") );
                    assertTrue( item.getMinSalary().equals(1) );
                    assertTrue( item.getMaxSalary().equals(2) );
                    assertTrue( item.getEndDate().equals(new Date( dateFormat.parse("11.12.15").getTime() )) );
                    assertTrue( item.getStartDate().equals( dateFormat.parse("11.12.15") ) );
                }
            }

        }catch (Throwable e){
            e.printStackTrace();
            assertNull(e);
        }
    }

}