package com.mdanylenko.dto;

import com.mdanylenko.excel.annotation.Column;
import com.mdanylenko.excel.annotation.Sheet;

import java.sql.Date;

/**
 * Created by IntelliJ IDEA.<br/>
 * User:  Max Danylenko<br/>
 * Date: 10.12.2015<br/>
 * Time: 14:27<br/>
 * To change this template use File | Settings | File Templates.
 */
@Sheet(
        sheetId = "JOB_HISTORY",
        hasHeader = true
)
public class JobHistory {

    @Column(columnName = "EMPLOYEE_ID")
    private Integer employeeId;

    @Column(columnName = "START_DATE")
    private Date startDate;

    @Column(columnName = "END_DATE")
    private Date endDate;

    @Column(columnName = "JOB_ID")
    private String jobId;

    @Column(columnName = "DEPARTMENT_ID")
    private Integer departmentId;

    public Integer getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(Integer employeeId) {
        this.employeeId = employeeId;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public String getJobId() {
        return jobId;
    }

    public void setJobId(String jobId) {
        this.jobId = jobId;
    }

    public Integer getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(Integer departmentId) {
        this.departmentId = departmentId;
    }
}
