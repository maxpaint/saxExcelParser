package com.mdanylenko;

import com.mdanylenko.excel.context.ExcelContext;
import com.mdanylenko.excel.example.dto.DataRemediationDto;
import com.mdanylenko.excel.exception.ConfigException;
import com.mdanylenko.excel.exception.PrepareContextException;
import com.mdanylenko.excel.exception.ParserException;
import com.mdanylenko.excel.parcer.SheetParser;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;


public class App 
{

    public static void main( String[] args ) throws PrepareContextException, ParserException, ConfigException {
        ExcelContext context = ExcelContext.prepareContext("com.mdanylenko.excel.example.dto");
        SheetParser parser = context.getParserSax("C:\\Users\\maxpaint\\Downloads\\testAnnotations.xlsx");//Main_Data_25-Nov-15_13-11-27 (1) //testAnnotations //

        BlockingQueue<DataRemediationDto> futureResult = parser.selectSheet(DataRemediationDto.class, 100_000);

        long startTime = System.nanoTime();
        parser.parseSheet();
        long endTime = System.nanoTime();

        if( parser.getExceptionsHandler().size() > 0){
            System.out.println("ERROR");
        }

        long duration = (endTime - startTime) / 1_000_000;
        System.out.println("Time execution: " + String.format("%02d min, %02d sec, %03d milliseconds",
                TimeUnit.MILLISECONDS.toMinutes(duration),
                TimeUnit.MILLISECONDS.toSeconds(duration) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(duration)),
                duration - TimeUnit.MINUTES.toMillis(TimeUnit.MILLISECONDS.toMinutes(duration))
                        - TimeUnit.SECONDS.toMillis(TimeUnit.MILLISECONDS.toSeconds(duration) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(duration)))
        ));
        
    }
}
