package com.mdanylenko.dto;

import com.mdanylenko.excel.annotation.Column;
import com.mdanylenko.excel.annotation.Sheet;
import com.mdanylenko.excel.converter.FloatConverter;
import com.mdanylenko.excel.converter.IntegerConverter;
import com.mdanylenko.excel.converter.SqlDateConverter;

import java.sql.Date;

@Sheet(
        sheetId = "1"
)
public class WithoutHeader {

    @Column(columnName = "A",
            converter = IntegerConverter.class)
    private Integer employeeId;

    @Column(columnName = "B")
    private String firstName;

    @Column(columnName = "C")
    private String lastName;

    @Column(columnName = "D")
    private String email;

    @Column(columnName = "E")
    private String phoneNumber;

    @Column(columnName = "F",
            converter = SqlDateConverter.class, format = "dd.MM.yy")
    private Date hireDate;

    @Column(columnName = "G")
    private String jobId;

    @Column(columnName = "H",
            converter = IntegerConverter.class)
    private Integer salary;

    @Column(columnName = "I",
            converter = FloatConverter.class)
    private Float commissionPct;

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
}
