package com.mdanylenko.dto;

import com.mdanylenko.excel.annotation.Column;
import com.mdanylenko.excel.annotation.Sheet;
import com.mdanylenko.excel.converter.FloatConverter;
import com.mdanylenko.excel.converter.IntegerConverter;
import com.mdanylenko.excel.converter.SqlDateConverter;

import java.sql.Date;

@Sheet(
        sheetName = "EMPLOYEES",
        hasHeader = true
)
public class Employee {

    @Column(columnName = "EMPLOYEE_ID",
            converter = IntegerConverter.class)
    private Integer employeeId;

    @Column(columnName = "FIRST_NAME")
    private String firstName;

    @Column(columnName = "LAST_NAME")
    private String lastName;

    @Column(columnName = "EMAIL")
    private String email;

    @Column(columnName = "PHONE_NUMBER")
    private String phoneNumber;

    @Column(columnName = "HIRE_DATE",
            converter = SqlDateConverter.class, format = "dd.MM.yy")
    private Date hireDate;

    @Column(columnName = "JOB_ID")
    private String jobId;

    @Column(columnName = "SALARY",
            converter = IntegerConverter.class)
    private Integer salary;

    @Column(columnName = "COMMISSION_PCT",
            converter = FloatConverter.class)
    private Float commissionPct;

    @Column(columnName = "MANAGER_ID",
            converter = IntegerConverter.class)
    private Integer managerId;

    @Column(columnName = "DEPARTMENT_ID",
            converter = IntegerConverter.class)
    private Integer departmentId;

    public Integer getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(Integer employeeId) {
        this.employeeId = employeeId;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public Date getHireDate() {
        return hireDate;
    }

    public void setHireDate(Date hireDate) {
        this.hireDate = hireDate;
    }

    public String getJobId() {
        return jobId;
    }

    public void setJobId(String jobId) {
        this.jobId = jobId;
    }

    public Integer getSalary() {
        return salary;
    }

    public void setSalary(Integer salary) {
        this.salary = salary;
    }

    public Float getCommissionPct() {
        return commissionPct;
    }

    public void setCommissionPct(Float commissionPct) {
        this.commissionPct = commissionPct;
    }

    public Integer getManagerId() {
        return managerId;
    }

    public void setManagerId(Integer managerId) {
        this.managerId = managerId;
    }

    public Integer getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(Integer departmentId) {
        this.departmentId = departmentId;
    }
}
