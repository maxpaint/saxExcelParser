package com.mdanylenko.excel.example.dto;

import com.mdanylenko.excel.annotation.Sheet;
import com.mdanylenko.excel.annotation.Column;
import com.mdanylenko.excel.converter.IntegerConverter;

/**
 * Created by IntelliJ IDEA.<br/>
 * User: Max Danylenko<br/>
 * Date: 27.11.2015<br/>
 * Time: 19:10<br/>
 * To change this template use File | Settings | File Templates.
 */

@Sheet(
    /*sheetName = "Data Remediation",*/
    sheetId = "1",
    hasHeader = true
)
public class DataRemediationDto {

    @Column(columnName = "PILLAR", defaultValue = "max Test1")
    private String pillar;

    @Column(columnName = "GROUPID",
            required = true,
            converter = IntegerConverter.class)
    private Integer groupid;

    @Column(columnName = "GROUP_NAME", defaultValue = "max Test2")
    private String groupName;

    @Column(columnName = "ORGID", converter = IntegerConverter.class)
    private Integer orgid;

    @Column(columnName = "CP_NAME", defaultValue = "max Test3")
    private String cpName;

    @Column(columnName = "BREACH_NUMBER")
    private String breachNumber;

    @Column(columnName = "CREDIT_AREA")
    private String creditArea;

    @Column(columnName = "CREDIT_TEAM")
    private String creditTeam;

    @Column(columnName = "CREDIT_REGION")
    private String creditRegion;

    @Column(columnName = "KCP")
    private String kcp;

    @Column(columnName = "PCO")
    private String pco;

    @Column(columnName = "REQUIREMENTS")
    private String requirements;

    @Column(columnName = "TTL_POPULATION_CRITERIA")
    private String ttlPopulationCriteria;

    @Column(columnName = "BREACH_CRITERIA")
    private String breachCriteria;


    public String getPillar() {
        return pillar;
    }

    public void setPillar(String pillar) {
        this.pillar = pillar;
    }

    public Integer getGroupid() {
        return groupid;
    }

    public void setGroupid(Integer groupid) {
        this.groupid = groupid;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public Integer getOrgid() {
        return orgid;
    }

    public void setOrgid(Integer orgid) {
        this.orgid = orgid;
    }

    public String getCpName() {
        return cpName;
    }

    public void setCpName(String cpName) {
        this.cpName = cpName;
    }

    public String getBreachNumber() {
        return breachNumber;
    }

    public void setBreachNumber(String breachNumber) {
        this.breachNumber = breachNumber;
    }

    public String getCreditArea() {
        return creditArea;
    }

    public void setCreditArea(String creditArea) {
        this.creditArea = creditArea;
    }

    public String getCreditTeam() {
        return creditTeam;
    }

    public void setCreditTeam(String creditTeam) {
        this.creditTeam = creditTeam;
    }

    public String getCreditRegion() {
        return creditRegion;
    }

    public void setCreditRegion(String creditRegion) {
        this.creditRegion = creditRegion;
    }

    public String getKcp() {
        return kcp;
    }

    public void setKcp(String kcp) {
        this.kcp = kcp;
    }

    public String getPco() {
        return pco;
    }

    public void setPco(String pco) {
        this.pco = pco;
    }

    public String getRequirements() {
        return requirements;
    }

    public void setRequirements(String requirements) {
        this.requirements = requirements;
    }

    public String getTtlPopulationCriteria() {
        return ttlPopulationCriteria;
    }

    public void setTtlPopulationCriteria(String ttlPopulationCriteria) {
        this.ttlPopulationCriteria = ttlPopulationCriteria;
    }

    public String getBreachCriteria() {
        return breachCriteria;
    }

    public void setBreachCriteria(String breachCriteria) {
        this.breachCriteria = breachCriteria;
    }
}
