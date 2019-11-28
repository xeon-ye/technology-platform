package com.pcitc.base.scientificplan;

import com.pcitc.base.common.RecordModel;

import java.io.Serializable;
import java.util.Date;

/**
 * 科技进展动态实体
 */
public class ScienceEvolveDynamic extends RecordModel implements Serializable {

    private static final long serialVersionUID = 1L;

    private String id; //id
    private String name = ""; //名称
    private String authenticateUtil = "";//申报单位
    private String researchField = "";//研究领域
    private Date releaseTime = new Date();//发布时间
    private String authenticateUitlText; //文档
    private Date annual = new Date();//年度/月度

    private  String accessory; //附件


    private  String nameTxt ="";

    public ScienceEvolveDynamic() {
    }

    public ScienceEvolveDynamic(String id, String name, String authenticateUtil, String researchField, Date releaseTime,
                                String authenticateUitlText, Date annual, String accessory) {
        this.id = id;
        this.name = name;
        this.authenticateUtil = authenticateUtil;
        this.researchField = researchField;
        this.releaseTime = releaseTime;
        this.authenticateUitlText = authenticateUitlText;
        this.annual = annual;
        this.accessory = accessory;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAuthenticateUtil() {
        return authenticateUtil;
    }

    public void setAuthenticateUtil(String authenticateUtil) {
        this.authenticateUtil = authenticateUtil;
    }

    public String getResearchField() {
        return researchField;
    }

    public void setResearchField(String researchField) {
        this.researchField = researchField;
    }

    public Date getReleaseTime() {
        return releaseTime;
    }

    public void setReleaseTime(Date releaseTime) {
        this.releaseTime = releaseTime;
    }

    public String getAuthenticateUitlText() {
        return authenticateUitlText;
    }

    public void setAuthenticateUitlText(String authenticateUitlText) {
        this.authenticateUitlText = authenticateUitlText;
    }


    public Date getAnnual() {
        return annual;
    }

    public void setAnnual(Date annual) {
        this.annual = annual;
    }


    public String getAccessory() {
        return accessory;
    }

    public void setAccessory(String accessory) {
        this.accessory = accessory;
    }


    public String getNameTxt() {
        return nameTxt;
    }

    public void setNameTxt(String nameTxt) {
        this.nameTxt = nameTxt;
    }
}
