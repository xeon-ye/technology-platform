package com.pcitc.base.achieve;

import com.pcitc.base.common.RecordModel;

import java.math.BigDecimal;

/**
 * 奖励表
 * @author ty
 */
public class AchieveReward extends RecordModel {

    /**
     * 数据主键
     */
    private String id;
    /**
     * 成果备案ID
     */
    private String achieveRecordId="";
    /**
     * 成果ID(非核心成果时为空)
     */
    private String achieveId;
    /**
     * 激励年份
     */
    private String rewardYear = "";
    /**
     * 转化收入
     */
    private String transIncome = "";
    /**
     * 核算依据
     */
    private String checkBasis = "";
    /**
     * 全周期净收入计算
     */
    private String incomeCalculation = "";
    /**
     * 激励方案
     */
    private String rewardRecord = "";
    /**
     * 激励额度
     */
    private BigDecimal rewardQuota = new BigDecimal("0");
    /**
     * 工资预算来源
     */
    private String budgetSources = "";
    /**
     * 激励人员名单
     */
    private String budgetPerson = "";
    /**
     * 激励总和
     */
    private BigDecimal budgetAllMoney = new BigDecimal("0");
    /**
     * 激励分配方案
     */
    private String assignPlan = "";
    /**
     * 净收入计算报告：材料
     */
    private String incomeReportDoc = "";
    /**
     * 激励方案：材料
     */
    private String assignPlanDoc = "";
    /**
     * 成果核算：材料
     */
    private String rewardAccountingDoc = "";
    /**
     * 团队成员
     */
    private String teamPerson = "";

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAchieveId() {
        return achieveId;
    }

    public void setAchieveId(String achieveId) {
        this.achieveId = achieveId;
    }

    public String getRewardYear() {
        return rewardYear;
    }

    public void setRewardYear(String rewardYear) {
        this.rewardYear = rewardYear;
    }

    public String getTransIncome() {
        return transIncome;
    }

    public void setTransIncome(String transIncome) {
        this.transIncome = transIncome;
    }

    public String getCheckBasis() {
        return checkBasis;
    }

    public void setCheckBasis(String checkBasis) {
        this.checkBasis = checkBasis;
    }

    public String getIncomeCalculation() {
        return incomeCalculation;
    }

    public void setIncomeCalculation(String incomeCalculation) {
        this.incomeCalculation = incomeCalculation;
    }

    public String getRewardRecord() {
        return rewardRecord;
    }

    public void setRewardRecord(String rewardRecord) {
        this.rewardRecord = rewardRecord;
    }

    public String getBudgetSources() {
        return budgetSources;
    }

    public void setBudgetSources(String budgetSources) {
        this.budgetSources = budgetSources;
    }

    public String getBudgetPerson() {
        return budgetPerson;
    }

    public void setBudgetPerson(String budgetPerson) {
        this.budgetPerson = budgetPerson;
    }

    public String getAssignPlan() {
        return assignPlan;
    }

    public void setAssignPlan(String assignPlan) {
        this.assignPlan = assignPlan;
    }

    public String getIncomeReportDoc() {
        return incomeReportDoc;
    }

    public void setIncomeReportDoc(String incomeReportDoc) {
        this.incomeReportDoc = incomeReportDoc;
    }

    public String getAssignPlanDoc() {
        return assignPlanDoc;
    }

    public void setAssignPlanDoc(String assignPlanDoc) {
        this.assignPlanDoc = assignPlanDoc;
    }

    public BigDecimal getBudgetAllMoney() {
        return budgetAllMoney;
    }

    public void setBudgetAllMoney(BigDecimal budgetAllMoney) {
        this.budgetAllMoney = budgetAllMoney;
    }

    public String getAchieveRecordId() {
        return achieveRecordId;
    }

    public void setAchieveRecordId(String achieveRecordId) {
        this.achieveRecordId = achieveRecordId;
    }

    public BigDecimal getRewardQuota() {
        return rewardQuota;
    }

    public void setRewardQuota(BigDecimal rewardQuota) {
        this.rewardQuota = rewardQuota;
    }

    public String getRewardAccountingDoc() {
        return rewardAccountingDoc;
    }

    public void setRewardAccountingDoc(String rewardAccountingDoc) {
        this.rewardAccountingDoc = rewardAccountingDoc;
    }

    public String getTeamPerson() {
        return teamPerson;
    }

    public void setTeamPerson(String teamPerson) {
        this.teamPerson = teamPerson;
    }
}
