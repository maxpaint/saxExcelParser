package com.mdanylenko.context;

import com.mdanylenko.excel.context.ExcelContext;
import com.mdanylenko.excel.exception.ErrorCode;
import com.mdanylenko.excel.exception.PrepareContextException;
import junit.framework.Assert;
import org.junit.Test;

/**
 * Created by IntelliJ IDEA.<br/>
 * User: Max Danylenko<br/>
 * Date: 05.12.2015<br/>
 * Time: 13:41<br/>
 * To change this template use File | Settings | File Templates.
 */

public class ContextTest  extends Assert {


    @Test
    public void notExistDirectory() {
        String errorMessage  = ErrorCode.CONTEXT_CLASS_PATH.replace("%s", "");
        String error = "";
        try {

            ExcelContext context = ExcelContext.prepareContext("com.mdanylenko.excel.example.dto.max");

        } catch (PrepareContextException e) {
            error = e.getMessage();
        }

        assertTrue(error.contains(errorMessage));
    }

    @Test
    public void emptyDirectory() {
        String errorMessage  = ErrorCode.CONTEXT_CLASS_DEFINE.replace("%s", "");
        String error = "";
        try {

            ExcelContext context = ExcelContext.prepareContext("com.mdanylenko.context");

        } catch (PrepareContextException e) {
            error = e.getMessage();
        }

        assertTrue(error.contains(errorMessage));
    }

    @Test
    public void positiveAnnotation(){
        String error = "";
        try {
            ExcelContext context = ExcelContext.prepareContext("com.mdanylenko.dto");
        } catch (PrepareContextException e) {
            error = e.getMessage();
        }

        assertEquals("", error);
    }

}
