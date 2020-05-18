package com.pcitc.base.achieve;

import com.pcitc.base.common.RecordModel;

/**
 * @author ty
 */
public class AchieveMaintain extends RecordModel {
    /**
     * 数据主键
     */
    private String id;
    /**
     * 获奖年份
     */
    private String year="";
    /**
     * 获奖类型
     */
    private String type="";
    /**
     * 获奖类型文本
     */
    private String typeText="";
    /**
     * 成果奖项类型
     */
    private String awardsType="";
    /**
     * 成果奖项文本
     */
    private String awardsTypeText="";
    /**
     * 奖项数量
     */
    private String awardsNumber="";


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getTypeText() {
        return typeText;
    }

    public void setTypeText(String typeText) {
        this.typeText = typeText;
    }

    public String getAwardsType() {
        return awardsType;
    }

    public void setAwardsType(String awardsType) {
        this.awardsType = awardsType;
    }

    public String getAwardsTypeText() {
        return awardsTypeText;
    }

    public void setAwardsTypeText(String awardsTypeText) {
        this.awardsTypeText = awardsTypeText;
    }

    public String getAwardsNumber() {
        return awardsNumber;
    }

    public void setAwardsNumber(String awardsNumber) {
        this.awardsNumber = awardsNumber;
    }
}
