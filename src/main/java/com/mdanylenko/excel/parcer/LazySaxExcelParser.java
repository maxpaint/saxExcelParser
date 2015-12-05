package com.mdanylenko.excel.parcer;

import com.mdanylenko.excel.annotation.Sheet;
import com.mdanylenko.excel.context.ExcelContext;
import com.mdanylenko.excel.exception.ConfigException;
import com.mdanylenko.excel.exception.ParserException;
import com.mdanylenko.excel.model.SheetDescription;
import com.mdanylenko.excel.parcer.handler.SaxSheetHandler;
import org.apache.poi.POIXMLDocument;
import org.apache.poi.openxml4j.exceptions.OpenXML4JException;
import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.xssf.eventusermodel.XSSFReader;
import org.apache.poi.xssf.model.SharedStringsTable;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.XMLReaderFactory;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.atomic.AtomicBoolean;

import static com.mdanylenko.excel.exception.ErrorCode.FILE_TYPE_ERROR;
import static com.mdanylenko.excel.util.StringUtil.isEmpty;
import static java.util.Objects.nonNull;

/**
 * Created by IntelliJ IDEA.<br/>
 * User: Max Danylenko<br/>
 * Date: 26.11.2015<br/>
 * Time: 16:07<br/>
 * To change this template use File | Settings | File Templates.
 */
public class LazySaxExcelParser implements SheetParser {

    private List<Throwable> exceptionsHandler;

    private File file;
    private ExcelContext context;
    private SheetDescription sheetDescription;
    private AtomicBoolean processFinished;

    private BlockingQueue blockingQueue;


    public LazySaxExcelParser(File file, ExcelContext context) throws ConfigException {
        try (InputStream inputStream = new BufferedInputStream(new FileInputStream(file))) {
            if(! POIXMLDocument.hasOOXMLHeader(inputStream)){
                throw new ConfigException(FILE_TYPE_ERROR);
            }
        } catch (IOException e) {
            throw new ConfigException(e.getMessage(), e);
        }

        this.file = file;
        this.context = context;
        this.processFinished = new AtomicBoolean(false);
        exceptionsHandler = new ArrayList<>();
    }

    public LazySaxExcelParser(String path, ExcelContext context) throws ConfigException {
        this(new File(path), context);
    }


    @Override
    public <T> BlockingQueue<T> selectSheet(Class<T> type, Integer fetchSize) {
        BlockingQueue<T> blockingQueue = new ArrayBlockingQueue<>(fetchSize);
        this.blockingQueue = blockingQueue;

        Sheet sheet = type.getAnnotation(Sheet.class);

        String name = sheet.sheetName();
        if(!isEmpty(name)){
            for (SheetDescription item : context.getSheets()){
                if( item.getSheetName().equalsIgnoreCase(name) ){
                    sheetDescription = item;
                }
            }

        }else{
            String id = sheet.sheetId();

            for (SheetDescription item : context.getSheets()){
                if( item.getSheetId().equalsIgnoreCase(id) ){
                    sheetDescription = item;
                }
            }
        }

        return blockingQueue;

    }

    @Override
    public void parseSheet() throws ParserException {
        String name = sheetDescription.getSheetName();

        try {
            InputStream sheet = isEmpty(name) ? getSheetById(Integer.parseInt(sheetDescription.getSheetId())) : getSheetByName(name);
            OPCPackage pkg = OPCPackage.open(file);
            XSSFReader r = new XSSFReader(pkg);
            SharedStringsTable sst = r.getSharedStringsTable();
            XMLReader parser = getSaxSheetParser(sst);
            InputSource sheetSource = new InputSource(sheet);
            parser.parse(sheetSource);
            System.out.println("test");
        }catch (OpenXML4JException | SAXException | IOException e){
            throw  new ParserException(e.getMessage(), e);
        }
    }

    @Override
    public List<Throwable> getExceptionsHandler() {
        return exceptionsHandler;
    }

    @Override
    public AtomicBoolean isProcessFinished() {
        return processFinished;
    }

    private InputStream getSheetByName(String name) throws ParserException {
        InputStream sheet = null;
        try {
            OPCPackage pkg = OPCPackage.open(file);
            XSSFReader r = new XSSFReader(pkg);
            XSSFReader.SheetIterator iter = (XSSFReader.SheetIterator) r.getSheetsData();
            while (iter.hasNext()) {
                InputStream stream = iter.next();
                String sheetName = iter.getSheetName();
                if(sheetName.equalsIgnoreCase(name)){
                    sheet = stream;
                }
            }
        }catch (OpenXML4JException | IOException e){
            throw  new ParserException(e.getMessage(), e);
        }

        return sheet;
    }

    private InputStream getSheetById(int id) throws ParserException {
        InputStream sheet;

        try{
            OPCPackage pkg = OPCPackage.open(file);
            XSSFReader r = new XSSFReader( pkg );
            sheet = r.getSheet("rId" + ( id + 1));

        } catch (OpenXML4JException | IOException e) {
            throw new ParserException(e.getMessage(), e);
        }

        return sheet;
    }

    private XMLReader getSaxSheetParser(SharedStringsTable sst) throws SAXException {
        XMLReader parser = XMLReaderFactory.createXMLReader("org.apache.xerces.parsers.SAXParser");

        SaxSheetHandler handler = new SaxSheetHandler(sst);

        handler.setBlockingQueue(blockingQueue);
        handler.setSheetDescription(sheetDescription);
        handler.setExceptionsHandler(exceptionsHandler);

        parser.setContentHandler(handler);
        return parser;
    }
}
