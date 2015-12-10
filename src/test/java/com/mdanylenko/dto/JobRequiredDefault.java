package com.mdanylenko.dto;

import com.mdanylenko.excel.annotation.Column;
import com.mdanylenko.excel.annotation.Sheet;
import com.mdanylenko.excel.converter.DateConverter;
import com.mdanylenko.excel.converter.IntegerConverter;
import com.mdanylenko.excel.converter.SqlDateConverter;

import java.sql.Date;

/**
 * Created by IntelliJ IDEA.<br/>
 * User: Max Danylenko<br/>
 * Date: 11.12.2015<br/>
 * Time: 0:17<br/>
 * To change this template use File | Settings | File Templates.
 */
@Sheet(
        sheetId = "3",
        hasHeader = true
)
public class JobRequiredDefault {

    @Column(columnName = "JOB_ID",
            defaultValue = "JOB_ID")
    private String jobId;

    @Column(columnName = "JOB_TITLE",
            defaultValue = "JOB_TITLE")
    private String jobTitle;

    @Column(columnName = "MIN_SALARY",
            converter = IntegerConverter.class,
            defaultValue = "1")
    private Integer minSalary;

    @Column(columnName = "MAX_SALARY",
            converter = IntegerConverter.class,
            defaultValue = "2")
    private Integer maxSalary;

    @Column(columnName = "END_DATE",
            converter = SqlDateConverter.class,
            defaultValue = "11.12.15",
            format = "dd.MM.yy")
    private Date endDate;

    @Column(columnName = "START_DATE",
            converter = DateConverter.class,
            defaultValue = "11.12.15",
            format = "dd.MM.yy")
    private java.util.Date startDate;


    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public java.util.Date getStartDate() {
        return startDate;
    }

    public void setStartDate(java.util.Date startDate) {
        this.startDate = startDate;
    }

    public String getJobId() {
        return jobId;
    }

    public void setJobId(String jobId) {
        this.jobId = jobId;
    }

    public String getJobTitle() {
        return jobTitle;
    }

    public void setJobTitle(String jobTitle) {
        this.jobTitle = jobTitle;
    }

    public Integer getMinSalary() {
        return minSalary;
    }

    public void setMinSalary(Integer minSalary) {
        this.minSalary = minSalary;
    }

    public Integer getMaxSalary() {
        return maxSalary;
    }

    public void setMaxSalary(Integer maxSalary) {
        this.maxSalary = maxSalary;
    }
}
