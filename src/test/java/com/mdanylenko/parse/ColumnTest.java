package com.mdanylenko.parse;

import com.mdanylenko.dto.JobRequiredDefault;
import com.mdanylenko.dto.JobRequiredNegative;
import com.mdanylenko.dto.JobRequiredPositive;
import com.mdanylenko.excel.parcer.LazySaxExcelParser;
import org.junit.Assert;
import org.junit.Test;

import java.io.File;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.BlockingQueue;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class ColumnTest {


    @Test
    public void requiredFieldsPositive()  {
        try{
            //Get file from resources folder
            ClassLoader classLoader = getClass().getClassLoader();
            File file = new File(classLoader.getResource("required.xlsx").getFile());

            LazySaxExcelParser parser =new LazySaxExcelParser(file);
            BlockingQueue<JobRequiredPositive> queue = parser.selectSheet(JobRequiredPositive.class, 10_000);
            parser.parseSheet();

            while( parser.isProcessFinished().get() ){
                assertEquals(0, parser.getExceptionsHandler().size());
            }

        }catch (Throwable e){
            e.printStackTrace();
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

            while( parser.isProcessFinished().get() ){
                assertEquals(3, parser.getExceptionsHandler().size());
            }

        }catch (Throwable e){
            e.printStackTrace();
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

            while( parser.isProcessFinished().get() ){
                List<JobRequiredDefault> result = new ArrayList<>();
                queue.drainTo(result);

                for (JobRequiredDefault item: result){
                    if( item.getJobId().equalsIgnoreCase("JOB_ID") ){
                        SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yy", Locale.ENGLISH);
                        assertEquals("JOB_TITLE", item.getJobTitle());
                        assertEquals(1, (int) item.getMinSalary());
                        assertEquals(item.getEndDate(), new Date(dateFormat.parse("11.12.15").getTime()));
                        assertEquals(item.getStartDate(), dateFormat.parse("11.12.15"));
                    }

                    if(item.getJobId().equalsIgnoreCase("HR_REP")){
                        SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yy", Locale.ENGLISH);
                        assertEquals("JOB_TITLE", item.getJobTitle());
                        assertEquals(1, (int) item.getMinSalary());
                        assertEquals(2, (int) item.getMaxSalary());
                        assertEquals(item.getEndDate(), new Date(dateFormat.parse("11.12.15").getTime()));
                        assertEquals(item.getStartDate(), dateFormat.parse("11.12.15"));
                    }
                }
            }

        }catch (Throwable e){
            e.printStackTrace();
        }
    }

}