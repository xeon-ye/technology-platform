package com.pcitc.base.computersoftware;

import com.pcitc.base.common.RecordModel;

import java.io.Serializable;
import java.util.Date;

/**
 * computer_software
 *
 * @author
 */
public class ComputerSoftware extends RecordModel implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键id
     */
    private String id;

    /**
     * 单位名称
     */
    private String unitName="";

    /**
     * 登记号
     */
    private String registerNumber="";

    /**
     * 软件名称
     */
    private String softwareName="";

    /**
     * 软件介绍
     */
    private String  softwareIntroduce;

    /**
     * 著作权人
     */
    private String copyrightOwner="";

    /**
     * 版本号
     */
    private String versionNumber="";

    /**
     * 登记日期
     */
    private Date recordDate=new Date();

    /**
     * 开发完成日期
     */
    private Date developFinishDate=new Date();

    /**
     * 软件简介
     */
    private String softwareIntro="";

    /**
     * 录入人
     */
    private String entryPeople="";

    /**
     * 录入时间
     */
    private Date entryTime=new Date();

    /**
     * 备注
     */
    private String notes="";

    /**
     * 附件上传
     */
    private String files="";


    private  String unitNameText= "";

    /**
     * 技术领域Text
     */
    private String technicalFieldText="";
    /**
     * 技术领域值
     */
    private String technicalFieldValue="";

    /**
     * 创建单元id
     */
    private String createUnitId="";

    /**
     * 创建单元名称
     */
    private String createUnitName="";

    /**
     * 技术领域
     */
    private  String technicalField;

    /**
     * 密级等级Text
     */
    private  String secretLevelText="";


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUnitName() {
        return unitName;
    }

    public void setUnitName(String unitName) {
        this.unitName = unitName;
    }

    public String getRegisterNumber() {
        return registerNumber;
    }

    public void setRegisterNumber(String registerNumber) {
        this.registerNumber = registerNumber;
    }

    public String getSoftwareName() {
        return softwareName;
    }

    public void setSoftwareName(String softwareName) {
        this.softwareName = softwareName;
    }

    public String getCopyrightOwner() {
        return copyrightOwner;
    }

    public void setCopyrightOwner(String copyrightOwner) {
        this.copyrightOwner = copyrightOwner;
    }

    public String getVersionNumber() {
        return versionNumber;
    }

    public void setVersionNumber(String versionNumber) {
        this.versionNumber = versionNumber;
    }

    public Date getRecordDate() {
        return recordDate;
    }

    public void setRecordDate(Date recordDate) {
        this.recordDate = recordDate;
    }

    public Date getDevelopFinishDate() {
        return developFinishDate;
    }

    public void setDevelopFinishDate(Date developFinishDate) {
        this.developFinishDate = developFinishDate;
    }

    public String getSoftwareIntro() {
        return softwareIntro;
    }

    public void setSoftwareIntro(String softwareIntro) {
        this.softwareIntro = softwareIntro;
    }

    public String getEntryPeople() {
        return entryPeople;
    }

    public void setEntryPeople(String entryPeople) {
        this.entryPeople = entryPeople;
    }

    public Date getEntryTime() {
        return entryTime;
    }

    public void setEntryTime(Date entryTime) {
        this.entryTime = entryTime;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }


    public String getSoftwareIntroduce() {
        return softwareIntroduce;
    }

    public void setSoftwareIntroduce(String softwareIntroduce) {
        this.softwareIntroduce = softwareIntroduce;
    }

    public String getUnitNameText() {
        return unitNameText;
    }

    public void setUnitNameText(String unitNameText) {
        this.unitNameText = unitNameText;
    }

    public String getFiles() {
        return files;
    }

    public void setFiles(String files) {
        this.files = files;
    }


    public String getTechnicalFieldText() {
        return technicalFieldText;
    }

    public void setTechnicalFieldText(String technicalFieldText) {
        this.technicalFieldText = technicalFieldText;
    }

    public String getTechnicalFieldValue() {
        return technicalFieldValue;
    }

    public void setTechnicalFieldValue(String technicalFieldValue) {
        this.technicalFieldValue = technicalFieldValue;

    }

    public String getTechnicalField() {
        return technicalField;
    }

    public void setTechnicalField(String technicalField) {
        this.technicalField = technicalField;
    }

    public String getCreateUnitId() {
        return createUnitId;
    }

    public void setCreateUnitId(String createUnitId) {
        this.createUnitId = createUnitId;
    }

    public String getCreateUnitName() {
        return createUnitName;
    }

    public void setCreateUnitName(String createUnitName) {
        this.createUnitName = createUnitName;
    }

    public String getSecretLevelText() {
        return secretLevelText;
    }

    public void setSecretLevelText(String secretLevelText) {
        this.secretLevelText = secretLevelText;
    }


}