package com.pcitc.base.stp.equipment;

import java.util.Date;

public class SrePurchase {
    private String id;

    private String purchaseName;

    private String proposerName;

    private String parentUnitPathNames;

    private String departName;

    private String departCode;

    private Integer stage;

    private Integer state;

    private Date createDate;

    private String proposerId;

    private String equipmentId;

    private Integer ischeck;

    private String projectId;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPurchaseName() {
        return purchaseName;
    }

    public void setPurchaseName(String purchaseName) {
        this.purchaseName = purchaseName;
    }

    public String getProposerName() {
        return proposerName;
    }

    public void setProposerName(String proposerName) {
        this.proposerName = proposerName;
    }

    public String getParentUnitPathNames() {
        return parentUnitPathNames;
    }

    public void setParentUnitPathNames(String parentUnitPathNames) {
        this.parentUnitPathNames = parentUnitPathNames;
    }

    public String getDepartName() {
        return departName;
    }

    public void setDepartName(String departName) {
        this.departName = departName;
    }

    public String getDepartCode() {
        return departCode;
    }

    public void setDepartCode(String departCode) {
        this.departCode = departCode;
    }

    public Integer getStage() {
        return stage;
    }

    public void setStage(Integer stage) {
        this.stage = stage;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public String getProposerId() {
        return proposerId;
    }

    public void setProposerId(String proposerId) {
        this.proposerId = proposerId;
    }

    public String getEquipmentId() {
        return equipmentId;
    }

    public void setEquipmentId(String equipmentId) {
        this.equipmentId = equipmentId;
    }

    public Integer getIscheck() {
        return ischeck;
    }

    public void setIscheck(Integer ischeck) {
        this.ischeck = ischeck;
    }

    public String getProjectId() {
        return projectId;
    }

    public void setProjectId(String projectId) {
        this.projectId = projectId;
    }
}