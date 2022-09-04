package com.mdanylenko.context;

import com.mdanylenko.excel.context.ExcelContext;
import com.mdanylenko.excel.exception.ErrorCode;
import com.mdanylenko.excel.exception.PrepareContextException;
import junit.framework.Assert;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class ContextTest {


    @Test
    public void notExistDirectory() {
        String errorMessage  = ErrorCode.CONTEXT_CLASS_PATH.replace("%s", "");
        String error = "";
        try {

            ExcelContext context = ExcelContext.prepareContext("com.mdanylenko.excel.example.dto.max");

        } catch (PrepareContextException e) {
            error = e.getMessage();
        }

        assertEquals(true, error.contains(errorMessage));
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

}
