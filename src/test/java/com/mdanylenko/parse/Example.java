package com.mdanylenko.parse;

import com.mdanylenko.dto.example.Employee;
import com.mdanylenko.excel.context.ExcelContext;
import com.mdanylenko.excel.parcer.LazySaxExcelParser;
import org.junit.Test;

import java.io.File;
import java.util.concurrent.BlockingQueue;

/**
 * Created by IntelliJ IDEA.<br/>
 * User: mdanylenko<br/>
 * Date: 10.12.2015<br/>
 * Time: 16:52<br/>
 * To change this template use File | Settings | File Templates.
 */

public class Example {


    @Test
    public void parseEmployees() throws Exception {
        //Get file from resources folder
        ClassLoader classLoader = getClass().getClassLoader();
        File file = new File(classLoader.getResource("example.xlsx").getFile());

        ExcelContext context = ExcelContext.prepareContext("com.mdanylenko.dto.example");
        LazySaxExcelParser parser = context.getParserSax(file);
        BlockingQueue<Employee> queue = parser.selectSheet(Employee.class, 10_000);
        parser.parseSheet();

        if( parser.getExceptionsHandler().size() > 0){
            System.out.println("ERROR");
        }
        long duration = /*(endTime - startTime) / */1_000_000;


    }
}
