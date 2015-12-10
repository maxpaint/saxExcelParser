package com.mdanylenko.dto;

import com.mdanylenko.excel.annotation.Column;
import com.mdanylenko.excel.annotation.Sheet;
import com.mdanylenko.excel.converter.IntegerConverter;

/**
 * Created by IntelliJ IDEA.<br/>
 * User: Max Danylenko<br/>
 * Date: 10.12.2015<br/>
 * Time: 22:15<br/>
 * To change this template use File | Settings | File Templates.
 */
@Sheet(
        sheetId = "2",
        hasHeader = true
)
public class JobRequiredNegative {

    @Column(columnName = "JOB_ID",
            required = true)
    private String jobId;

    @Column(columnName = "JOB_TITLE")
    private String jobTitle;

    @Column(columnName = "MIN_SALARY",
            converter = IntegerConverter.class)
    private Integer minSalary;

    @Column(columnName = "MAX_SALARY",
            converter = IntegerConverter.class)
    private Integer maxSalary;


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
