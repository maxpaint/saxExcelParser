package com.mdanylenko.parse;

import com.mdanylenko.BaseTest;
import com.mdanylenko.excel.annotation.Column;
import com.mdanylenko.excel.annotation.Sheet;
import com.mdanylenko.excel.converter.DoubleConverter;
import com.mdanylenko.excel.exception.ConfigException;
import com.mdanylenko.excel.exception.PrepareContextException;
import com.mdanylenko.excel.parcer.LazySaxExcelParser;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.concurrent.BlockingQueue;

/**
 * Created by IntelliJ IDEA.<br/>
 * User: Max Danylenko<br/>
 * Date: 22.12.2015<br/>
 * Time: 21:25<br/>
 * To change this template use File | Settings | File Templates.
 */
public class DataTest extends BaseTest {

    private File excelFile;

    private Map<String, Object[]> data  = getData();


    @Before
    public void createSheet() throws IOException {
        excelFile = createFile("temp.xlsx");

        XSSFWorkbook workbook = new XSSFWorkbook();
        XSSFSheet sheet = workbook.createSheet("Sample sheet");

        Set<String> keyset = data.keySet();
        int rownum = 0;
        for (String key : keyset) {
            Row row = sheet.createRow(rownum++);
            Object[] objArr = data.get(key);
            int cellnum = 0;
            for (Object obj : objArr) {
                Cell cell = row.createCell(cellnum++);

                if (obj instanceof Date) {
                    cell.setCellValue((Date) obj);
                } else if (obj instanceof Boolean) {
                    cell.setCellValue((Boolean) obj);
                } else if (obj instanceof String) {
                    cell.setCellValue((String) obj);
                } else if (obj instanceof Double) {
                    cell.setCellValue((Double) obj);
                }
            }
        }

        FileOutputStream out = new FileOutputStream(excelFile);
        workbook.write(out);
        out.close();
        System.out.println("Excel written successfully..");
    }

    @Test
    public void test() throws ConfigException, PrepareContextException {
        ClassLoader classLoader = getClass().getClassLoader();
        File file = new File(classLoader.getResource("temp.xlsx").getFile());

        long startTime = System.nanoTime();

        LazySaxExcelParser parser = new LazySaxExcelParser(file);
        BlockingQueue<TestDTO> queue = parser.selectSheet(TestDTO.class, 10_000);
        parser.parseSheet();

        while (!parser.isProcessFinished().get()) {

        }

        executiveTime("withoutHeader", startTime, System.nanoTime());

        assertTrue(parser.getExceptionsHandler().size() == 0);
        List<TestDTO> result = new ArrayList<>();
        queue.drainTo(result);
        assertEquals(3, result.size());

        for (int i = 2; i < 5; i++) {
            TestDTO dto = result.get(i-2);
            Object[] objects = data.get(String.valueOf(i) );
            assertEquals(objects[0], dto.getNo());
            assertEquals(objects[1], dto.getName());
            assertEquals(objects[2], dto.getSalary());
        }

    }

    @After
    public void deleteFile(){
        if( !excelFile.delete() ){
            excelFile.deleteOnExit();
        }
    }

    private File createFile(String name) throws IOException {
        ClassLoader loader = getClass().getClassLoader();
        File file = new File(loader.getResource("example.xlsx").getFile());
        File folder = file.getParentFile();
        Path filePath = null;

        if (folder.isDirectory()) {
            filePath = Paths.get(folder.getAbsolutePath(), name);
            if (Files.exists(filePath)) {
                Files.delete(filePath);
            }
            Files.createFile(filePath);
        }

        return filePath.toFile();
    }

    private Map<String, Object[]> getData(){
        Map<String, Object[]> data = new HashMap<String, Object[]>();
        data.put("1", new Object[]{"Emp No.", "Name", "Salary"});
        data.put("2", new Object[]{1d, "John", 1500000d});
        data.put("3", new Object[]{2d, "Sam", 800000d});
        data.put("4", new Object[]{3d, "Dean", 700000d});

        return data;
    }

    @Sheet(
            sheetName = "Sample sheet",
            hasHeader = true
    )
    public class TestDTO {

        @Column(columnName = "Emp No.",
                converter = DoubleConverter.class)
        private Double no;

        @Column(columnName = "Name")
        private String name;

        @Column(columnName = "Salary",
                converter = DoubleConverter.class)
        private Double salary;

        public Double getNo() {
            return no;
        }

        public void setNo(Double no) {
            this.no = no;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public Double getSalary() {
            return salary;
        }

        public void setSalary(Double salary) {
            this.salary = salary;
        }
    }
}
