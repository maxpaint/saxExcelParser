package com.mdanylenko.excel.parcer;

import com.mdanylenko.excel.annotation.Column;
import com.mdanylenko.excel.annotation.Sheet;
import com.mdanylenko.excel.context.ExcelContext;
import com.mdanylenko.excel.converter.TypeConverter;
import com.mdanylenko.excel.exception.ConfigException;
import com.mdanylenko.excel.exception.ErrorCode;
import com.mdanylenko.excel.exception.ParserException;
import com.mdanylenko.excel.exception.PrepareContextException;
import com.mdanylenko.excel.model.ColumnDescription;
import com.mdanylenko.excel.model.SheetDescription;
import com.mdanylenko.excel.parcer.handler.SaxSheetHandler;
import org.apache.poi.POIXMLDocument;
import org.apache.poi.openxml4j.exceptions.OpenXML4JException;
import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.openxml4j.opc.PackagePart;
import org.apache.poi.xssf.eventusermodel.XSSFReader;
import org.apache.poi.xssf.model.SharedStringsTable;
import org.xml.sax.Attributes;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.DefaultHandler;
import org.xml.sax.helpers.XMLReaderFactory;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.*;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.atomic.AtomicBoolean;

import static com.mdanylenko.excel.exception.ErrorCode.*;
import static com.mdanylenko.excel.exception.ErrorCode.CONTEXT_CLASS_MAPPING;
import static com.mdanylenko.excel.exception.ErrorCode.CONTEXT_CLASS_MAPPING_ERROR;
import static com.mdanylenko.excel.util.StringUtil.isEmpty;
import static java.util.Objects.isNull;

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
    private SheetDescription sheetDescription;
    private AtomicBoolean processFinished;

    private BlockingQueue blockingQueue;

    public LazySaxExcelParser(File file) throws ConfigException {
        try (InputStream inputStream = new BufferedInputStream(new FileInputStream(file))) {
            if(! POIXMLDocument.hasOOXMLHeader(inputStream)){
                throw new ConfigException(FILE_TYPE_ERROR);
            }
        } catch (IOException e) {
            throw new ConfigException(e.getMessage(), e);
        }

        this.file = file;
        this.processFinished = new AtomicBoolean(false);
        exceptionsHandler = new ArrayList<>();
    }

    public LazySaxExcelParser(String path) throws ConfigException {
        this(new File(path));
    }


    @Override
    public <T> BlockingQueue<T> selectSheet(Class<T> type, Integer fetchSize) throws PrepareContextException {
        BlockingQueue<T> blockingQueue = new ArrayBlockingQueue<>(fetchSize);
        this.blockingQueue = blockingQueue;

        /*Sheet sheet = type.getAnnotation(Sheet.class);*/
        sheetDescription = new SheetDescription();


            sheetDescription.setType(type);

            Sheet sheetDto = type.getDeclaredAnnotation(Sheet.class);

            String sheetId = sheetDto.sheetId();
            String sheetName = sheetDto.sheetName();

            if(isEmpty(sheetId) && isEmpty( sheetName )){
                throw new PrepareContextException(String.format(CONTEXT_SHEET_DEFINE, type));
            }

            if(!isEmpty(sheetId) && !isEmpty( sheetName )){
                throw new PrepareContextException(String.format(CONTEXT_WRONG_SHEET_DEFINE, type));
            }

            if(!isEmpty(sheetId)){
                sheetDescription.setSheetId(sheetId);
            }

            if(!isEmpty( sheetName )){
                sheetDescription.setSheetName(sheetName);
            }

            sheetDescription.setHasHeader(sheetDto.hasHeader());

            Field[] fields = type.getDeclaredFields();

            for (Field field : fields) {
                field.setAccessible(true);
                Column annotation = field.getDeclaredAnnotation(Column.class);
                String columnIndex = annotation.columnIndex();
                String columnName = annotation.columnName();
                boolean isRequired = annotation.required();
                String defaultValue = annotation.defaultValue();
                TypeConverter converter;

                if(TypeConverter.class.isAssignableFrom(annotation.converter())) {
                    try {
                        converter = (TypeConverter) annotation.converter().newInstance();

                    } catch (IllegalAccessException | InstantiationException e) {
                        throw new PrepareContextException(e.getMessage(), e);
                    }
                } else {
                    throw new PrepareContextException(String.format(CONTEXT_CONVERTER, field, type));
                }

                if(isEmpty(columnIndex) && isEmpty(columnName)){
                    throw new PrepareContextException(String.format(CONTEXT_CLASS_MAPPING_ERROR, field, type));
                }

                if(!isEmpty(columnIndex) && !isEmpty(columnName)){
                    throw new PrepareContextException(String.format(CONTEXT_CLASS_MAPPING, field, type));
                }


                ColumnDescription.Builder builder =  new ColumnDescription.Builder();
                builder.setColumnIndex(columnIndex)
                        .setColumnName(columnName)
                        .setIsRequired(isRequired)
                        .setDefaultValue(defaultValue)
                        .setConverter((converter))
                        .setField(field);


                sheetDescription.addColumn(builder.build());

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
        try{
            OPCPackage pkg = OPCPackage.open(file);
            ArrayList<PackagePart> parts = pkg.getParts();
            PackagePart workBook = null;
            for(PackagePart part: parts){
                if(part.getPartName().getName().endsWith("workbook.xml")){
                    workBook = part;
                }
            }
            return getSheetByName(findSheetNameById(workBook, id));
        } catch (OpenXML4JException e) {
            throw new ParserException(e.getMessage(), e);
        }
    }


    private List<SheetDesc> sheetDescs;

    private String findSheetNameById(PackagePart workBook, int id) throws ParserException {
        if( isNull(sheetDescs) ){
            SAXParserFactory factory = SAXParserFactory.newInstance();
            try {

                SAXParser saxParser = factory.newSAXParser();
                WorkBookHandler handler   = new WorkBookHandler();
                saxParser.parse(workBook.getInputStream(), handler);
                sheetDescs = handler.getSheets();

            } catch (Throwable err) {
                err.printStackTrace ();
            }
        }

        for (SheetDesc item: sheetDescs){
            if( item.getSheedId() == id){
                return item.getSheedName();
            }
        }

        throw new ParserException(String.format(ErrorCode.WRONG_SHEET_ID, id));
    }

    private class SheetDesc{

        private int sheedId;
        private String sheedRId;
        private String sheedName;

        public int getSheedId() {
            return sheedId;
        }

        public void setSheedId(int sheedId) {
            this.sheedId = sheedId;
        }

        public String getSheedRId() {
            return sheedRId;
        }

        public void setSheedRId(String sheedRId) {
            this.sheedRId = sheedRId;
        }

        public String getSheedName() {
            return sheedName;
        }

        public void setSheedName(String sheedName) {
            this.sheedName = sheedName;
        }
    }

    private class WorkBookHandler extends DefaultHandler
    {

        private List<SheetDesc> sheets = new ArrayList<>();
        private String content;
        private SheetDesc sheetDesc;

        public void startElement(String uri, String localName, String name, Attributes attributes) throws SAXException {

            if(name.equals("sheet")){
                sheetDesc = new SheetDesc();

                sheetDesc.setSheedId(Integer.parseInt(attributes.getValue("sheetId")));
                sheetDesc.setSheedName(attributes.getValue("name"));
                sheetDesc.setSheedRId(attributes.getValue("r:id") );

                sheets.add(sheetDesc);
            }
        }

        public void endElement(String uri, String localName, String name) throws SAXException {

        }

        public void characters(char[] ch, int start, int length) throws SAXException {
            content += new String(ch, start, length);
        }

        public List<SheetDesc> getSheets() {
            return sheets;
        }

        public void setSheets(List<SheetDesc> sheets) {
            this.sheets = sheets;
        }
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
