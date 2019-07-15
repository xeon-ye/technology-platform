package com.pcitc.base.stp.budget;

import java.util.ArrayList;
import java.util.List;

public class BudgetMoneyTotalExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public BudgetMoneyTotalExample() {
        oredCriteria = new ArrayList<Criteria>();
    }

    public void setOrderByClause(String orderByClause) {
        this.orderByClause = orderByClause;
    }

    public String getOrderByClause() {
        return orderByClause;
    }

    public void setDistinct(boolean distinct) {
        this.distinct = distinct;
    }

    public boolean isDistinct() {
        return distinct;
    }

    public List<Criteria> getOredCriteria() {
        return oredCriteria;
    }

    public void or(Criteria criteria) {
        oredCriteria.add(criteria);
    }

    public Criteria or() {
        Criteria criteria = createCriteriaInternal();
        oredCriteria.add(criteria);
        return criteria;
    }

    public Criteria createCriteria() {
        Criteria criteria = createCriteriaInternal();
        if (oredCriteria.size() == 0) {
            oredCriteria.add(criteria);
        }
        return criteria;
    }

    protected Criteria createCriteriaInternal() {
        Criteria criteria = new Criteria();
        return criteria;
    }

    public void clear() {
        oredCriteria.clear();
        orderByClause = null;
        distinct = false;
    }

    protected abstract static class GeneratedCriteria {
        protected List<Criterion> criteria;

        protected GeneratedCriteria() {
            super();
            criteria = new ArrayList<Criterion>();
        }

        public boolean isValid() {
            return criteria.size() > 0;
        }

        public List<Criterion> getAllCriteria() {
            return criteria;
        }

        public List<Criterion> getCriteria() {
            return criteria;
        }

        protected void addCriterion(String condition) {
            if (condition == null) {
                throw new RuntimeException("Value for condition cannot be null");
            }
            criteria.add(new Criterion(condition));
        }

        protected void addCriterion(String condition, Object value, String property) {
            if (value == null) {
                throw new RuntimeException("Value for " + property + " cannot be null");
            }
            criteria.add(new Criterion(condition, value));
        }

        protected void addCriterion(String condition, Object value1, Object value2, String property) {
            if (value1 == null || value2 == null) {
                throw new RuntimeException("Between values for " + property + " cannot be null");
            }
            criteria.add(new Criterion(condition, value1, value2));
        }

        public Criteria andDataIdIsNull() {
            addCriterion("data_id is null");
            return (Criteria) this;
        }

        public Criteria andDataIdIsNotNull() {
            addCriterion("data_id is not null");
            return (Criteria) this;
        }

        public Criteria andDataIdEqualTo(String value) {
            addCriterion("data_id =", value, "dataId");
            return (Criteria) this;
        }

        public Criteria andDataIdNotEqualTo(String value) {
            addCriterion("data_id <>", value, "dataId");
            return (Criteria) this;
        }

        public Criteria andDataIdGreaterThan(String value) {
            addCriterion("data_id >", value, "dataId");
            return (Criteria) this;
        }

        public Criteria andDataIdGreaterThanOrEqualTo(String value) {
            addCriterion("data_id >=", value, "dataId");
            return (Criteria) this;
        }

        public Criteria andDataIdLessThan(String value) {
            addCriterion("data_id <", value, "dataId");
            return (Criteria) this;
        }

        public Criteria andDataIdLessThanOrEqualTo(String value) {
            addCriterion("data_id <=", value, "dataId");
            return (Criteria) this;
        }

        public Criteria andDataIdLike(String value) {
            addCriterion("data_id like", value, "dataId");
            return (Criteria) this;
        }

        public Criteria andDataIdNotLike(String value) {
            addCriterion("data_id not like", value, "dataId");
            return (Criteria) this;
        }

        public Criteria andDataIdIn(List<String> values) {
            addCriterion("data_id in", values, "dataId");
            return (Criteria) this;
        }

        public Criteria andDataIdNotIn(List<String> values) {
            addCriterion("data_id not in", values, "dataId");
            return (Criteria) this;
        }

        public Criteria andDataIdBetween(String value1, String value2) {
            addCriterion("data_id between", value1, value2, "dataId");
            return (Criteria) this;
        }

        public Criteria andDataIdNotBetween(String value1, String value2) {
            addCriterion("data_id not between", value1, value2, "dataId");
            return (Criteria) this;
        }

        public Criteria andParentDataIdIsNull() {
            addCriterion("parent_data_id is null");
            return (Criteria) this;
        }

        public Criteria andParentDataIdIsNotNull() {
            addCriterion("parent_data_id is not null");
            return (Criteria) this;
        }

        public Criteria andParentDataIdEqualTo(String value) {
            addCriterion("parent_data_id =", value, "parentDataId");
            return (Criteria) this;
        }

        public Criteria andParentDataIdNotEqualTo(String value) {
            addCriterion("parent_data_id <>", value, "parentDataId");
            return (Criteria) this;
        }

        public Criteria andParentDataIdGreaterThan(String value) {
            addCriterion("parent_data_id >", value, "parentDataId");
            return (Criteria) this;
        }

        public Criteria andParentDataIdGreaterThanOrEqualTo(String value) {
            addCriterion("parent_data_id >=", value, "parentDataId");
            return (Criteria) this;
        }

        public Criteria andParentDataIdLessThan(String value) {
            addCriterion("parent_data_id <", value, "parentDataId");
            return (Criteria) this;
        }

        public Criteria andParentDataIdLessThanOrEqualTo(String value) {
            addCriterion("parent_data_id <=", value, "parentDataId");
            return (Criteria) this;
        }

        public Criteria andParentDataIdLike(String value) {
            addCriterion("parent_data_id like", value, "parentDataId");
            return (Criteria) this;
        }

        public Criteria andParentDataIdNotLike(String value) {
            addCriterion("parent_data_id not like", value, "parentDataId");
            return (Criteria) this;
        }

        public Criteria andParentDataIdIn(List<String> values) {
            addCriterion("parent_data_id in", values, "parentDataId");
            return (Criteria) this;
        }

        public Criteria andParentDataIdNotIn(List<String> values) {
            addCriterion("parent_data_id not in", values, "parentDataId");
            return (Criteria) this;
        }

        public Criteria andParentDataIdBetween(String value1, String value2) {
            addCriterion("parent_data_id between", value1, value2, "parentDataId");
            return (Criteria) this;
        }

        public Criteria andParentDataIdNotBetween(String value1, String value2) {
            addCriterion("parent_data_id not between", value1, value2, "parentDataId");
            return (Criteria) this;
        }

        public Criteria andNdIsNull() {
            addCriterion("nd is null");
            return (Criteria) this;
        }

        public Criteria andNdIsNotNull() {
            addCriterion("nd is not null");
            return (Criteria) this;
        }

        public Criteria andNdEqualTo(String value) {
            addCriterion("nd =", value, "nd");
            return (Criteria) this;
        }

        public Criteria andNdNotEqualTo(String value) {
            addCriterion("nd <>", value, "nd");
            return (Criteria) this;
        }

        public Criteria andNdGreaterThan(String value) {
            addCriterion("nd >", value, "nd");
            return (Criteria) this;
        }

        public Criteria andNdGreaterThanOrEqualTo(String value) {
            addCriterion("nd >=", value, "nd");
            return (Criteria) this;
        }

        public Criteria andNdLessThan(String value) {
            addCriterion("nd <", value, "nd");
            return (Criteria) this;
        }

        public Criteria andNdLessThanOrEqualTo(String value) {
            addCriterion("nd <=", value, "nd");
            return (Criteria) this;
        }

        public Criteria andNdLike(String value) {
            addCriterion("nd like", value, "nd");
            return (Criteria) this;
        }

        public Criteria andNdNotLike(String value) {
            addCriterion("nd not like", value, "nd");
            return (Criteria) this;
        }

        public Criteria andNdIn(List<String> values) {
            addCriterion("nd in", values, "nd");
            return (Criteria) this;
        }

        public Criteria andNdNotIn(List<String> values) {
            addCriterion("nd not in", values, "nd");
            return (Criteria) this;
        }

        public Criteria andNdBetween(String value1, String value2) {
            addCriterion("nd between", value1, value2, "nd");
            return (Criteria) this;
        }

        public Criteria andNdNotBetween(String value1, String value2) {
            addCriterion("nd not between", value1, value2, "nd");
            return (Criteria) this;
        }

        public Criteria andBudgetCodeIsNull() {
            addCriterion("budget_code is null");
            return (Criteria) this;
        }

        public Criteria andBudgetCodeIsNotNull() {
            addCriterion("budget_code is not null");
            return (Criteria) this;
        }

        public Criteria andBudgetCodeEqualTo(String value) {
            addCriterion("budget_code =", value, "budgetCode");
            return (Criteria) this;
        }

        public Criteria andBudgetCodeNotEqualTo(String value) {
            addCriterion("budget_code <>", value, "budgetCode");
            return (Criteria) this;
        }

        public Criteria andBudgetCodeGreaterThan(String value) {
            addCriterion("budget_code >", value, "budgetCode");
            return (Criteria) this;
        }

        public Criteria andBudgetCodeGreaterThanOrEqualTo(String value) {
            addCriterion("budget_code >=", value, "budgetCode");
            return (Criteria) this;
        }

        public Criteria andBudgetCodeLessThan(String value) {
            addCriterion("budget_code <", value, "budgetCode");
            return (Criteria) this;
        }

        public Criteria andBudgetCodeLessThanOrEqualTo(String value) {
            addCriterion("budget_code <=", value, "budgetCode");
            return (Criteria) this;
        }

        public Criteria andBudgetCodeLike(String value) {
            addCriterion("budget_code like", value, "budgetCode");
            return (Criteria) this;
        }

        public Criteria andBudgetCodeNotLike(String value) {
            addCriterion("budget_code not like", value, "budgetCode");
            return (Criteria) this;
        }

        public Criteria andBudgetCodeIn(List<String> values) {
            addCriterion("budget_code in", values, "budgetCode");
            return (Criteria) this;
        }

        public Criteria andBudgetCodeNotIn(List<String> values) {
            addCriterion("budget_code not in", values, "budgetCode");
            return (Criteria) this;
        }

        public Criteria andBudgetCodeBetween(String value1, String value2) {
            addCriterion("budget_code between", value1, value2, "budgetCode");
            return (Criteria) this;
        }

        public Criteria andBudgetCodeNotBetween(String value1, String value2) {
            addCriterion("budget_code not between", value1, value2, "budgetCode");
            return (Criteria) this;
        }

        public Criteria andUnitCodeIsNull() {
            addCriterion("unit_code is null");
            return (Criteria) this;
        }

        public Criteria andUnitCodeIsNotNull() {
            addCriterion("unit_code is not null");
            return (Criteria) this;
        }

        public Criteria andUnitCodeEqualTo(String value) {
            addCriterion("unit_code =", value, "unitCode");
            return (Criteria) this;
        }

        public Criteria andUnitCodeNotEqualTo(String value) {
            addCriterion("unit_code <>", value, "unitCode");
            return (Criteria) this;
        }

        public Criteria andUnitCodeGreaterThan(String value) {
            addCriterion("unit_code >", value, "unitCode");
            return (Criteria) this;
        }

        public Criteria andUnitCodeGreaterThanOrEqualTo(String value) {
            addCriterion("unit_code >=", value, "unitCode");
            return (Criteria) this;
        }

        public Criteria andUnitCodeLessThan(String value) {
            addCriterion("unit_code <", value, "unitCode");
            return (Criteria) this;
        }

        public Criteria andUnitCodeLessThanOrEqualTo(String value) {
            addCriterion("unit_code <=", value, "unitCode");
            return (Criteria) this;
        }

        public Criteria andUnitCodeLike(String value) {
            addCriterion("unit_code like", value, "unitCode");
            return (Criteria) this;
        }

        public Criteria andUnitCodeNotLike(String value) {
            addCriterion("unit_code not like", value, "unitCode");
            return (Criteria) this;
        }

        public Criteria andUnitCodeIn(List<String> values) {
            addCriterion("unit_code in", values, "unitCode");
            return (Criteria) this;
        }

        public Criteria andUnitCodeNotIn(List<String> values) {
            addCriterion("unit_code not in", values, "unitCode");
            return (Criteria) this;
        }

        public Criteria andUnitCodeBetween(String value1, String value2) {
            addCriterion("unit_code between", value1, value2, "unitCode");
            return (Criteria) this;
        }

        public Criteria andUnitCodeNotBetween(String value1, String value2) {
            addCriterion("unit_code not between", value1, value2, "unitCode");
            return (Criteria) this;
        }

        public Criteria andShowIndexIsNull() {
            addCriterion("show_index is null");
            return (Criteria) this;
        }

        public Criteria andShowIndexIsNotNull() {
            addCriterion("show_index is not null");
            return (Criteria) this;
        }

        public Criteria andShowIndexEqualTo(String value) {
            addCriterion("show_index =", value, "showIndex");
            return (Criteria) this;
        }

        public Criteria andShowIndexNotEqualTo(String value) {
            addCriterion("show_index <>", value, "showIndex");
            return (Criteria) this;
        }

        public Criteria andShowIndexGreaterThan(String value) {
            addCriterion("show_index >", value, "showIndex");
            return (Criteria) this;
        }

        public Criteria andShowIndexGreaterThanOrEqualTo(String value) {
            addCriterion("show_index >=", value, "showIndex");
            return (Criteria) this;
        }

        public Criteria andShowIndexLessThan(String value) {
            addCriterion("show_index <", value, "showIndex");
            return (Criteria) this;
        }

        public Criteria andShowIndexLessThanOrEqualTo(String value) {
            addCriterion("show_index <=", value, "showIndex");
            return (Criteria) this;
        }

        public Criteria andShowIndexLike(String value) {
            addCriterion("show_index like", value, "showIndex");
            return (Criteria) this;
        }

        public Criteria andShowIndexNotLike(String value) {
            addCriterion("show_index not like", value, "showIndex");
            return (Criteria) this;
        }

        public Criteria andShowIndexIn(List<String> values) {
            addCriterion("show_index in", values, "showIndex");
            return (Criteria) this;
        }

        public Criteria andShowIndexNotIn(List<String> values) {
            addCriterion("show_index not in", values, "showIndex");
            return (Criteria) this;
        }

        public Criteria andShowIndexBetween(String value1, String value2) {
            addCriterion("show_index between", value1, value2, "showIndex");
            return (Criteria) this;
        }

        public Criteria andShowIndexNotBetween(String value1, String value2) {
            addCriterion("show_index not between", value1, value2, "showIndex");
            return (Criteria) this;
        }

        public Criteria andUnitNameIsNull() {
            addCriterion("unit_name is null");
            return (Criteria) this;
        }

        public Criteria andUnitNameIsNotNull() {
            addCriterion("unit_name is not null");
            return (Criteria) this;
        }

        public Criteria andUnitNameEqualTo(String value) {
            addCriterion("unit_name =", value, "unitName");
            return (Criteria) this;
        }

        public Criteria andUnitNameNotEqualTo(String value) {
            addCriterion("unit_name <>", value, "unitName");
            return (Criteria) this;
        }

        public Criteria andUnitNameGreaterThan(String value) {
            addCriterion("unit_name >", value, "unitName");
            return (Criteria) this;
        }

        public Criteria andUnitNameGreaterThanOrEqualTo(String value) {
            addCriterion("unit_name >=", value, "unitName");
            return (Criteria) this;
        }

        public Criteria andUnitNameLessThan(String value) {
            addCriterion("unit_name <", value, "unitName");
            return (Criteria) this;
        }

        public Criteria andUnitNameLessThanOrEqualTo(String value) {
            addCriterion("unit_name <=", value, "unitName");
            return (Criteria) this;
        }

        public Criteria andUnitNameLike(String value) {
            addCriterion("unit_name like", value, "unitName");
            return (Criteria) this;
        }

        public Criteria andUnitNameNotLike(String value) {
            addCriterion("unit_name not like", value, "unitName");
            return (Criteria) this;
        }

        public Criteria andUnitNameIn(List<String> values) {
            addCriterion("unit_name in", values, "unitName");
            return (Criteria) this;
        }

        public Criteria andUnitNameNotIn(List<String> values) {
            addCriterion("unit_name not in", values, "unitName");
            return (Criteria) this;
        }

        public Criteria andUnitNameBetween(String value1, String value2) {
            addCriterion("unit_name between", value1, value2, "unitName");
            return (Criteria) this;
        }

        public Criteria andUnitNameNotBetween(String value1, String value2) {
            addCriterion("unit_name not between", value1, value2, "unitName");
            return (Criteria) this;
        }

        public Criteria andShowAliIsNull() {
            addCriterion("show_ali is null");
            return (Criteria) this;
        }

        public Criteria andShowAliIsNotNull() {
            addCriterion("show_ali is not null");
            return (Criteria) this;
        }

        public Criteria andShowAliEqualTo(String value) {
            addCriterion("show_ali =", value, "showAli");
            return (Criteria) this;
        }

        public Criteria andShowAliNotEqualTo(String value) {
            addCriterion("show_ali <>", value, "showAli");
            return (Criteria) this;
        }

        public Criteria andShowAliGreaterThan(String value) {
            addCriterion("show_ali >", value, "showAli");
            return (Criteria) this;
        }

        public Criteria andShowAliGreaterThanOrEqualTo(String value) {
            addCriterion("show_ali >=", value, "showAli");
            return (Criteria) this;
        }

        public Criteria andShowAliLessThan(String value) {
            addCriterion("show_ali <", value, "showAli");
            return (Criteria) this;
        }

        public Criteria andShowAliLessThanOrEqualTo(String value) {
            addCriterion("show_ali <=", value, "showAli");
            return (Criteria) this;
        }

        public Criteria andShowAliLike(String value) {
            addCriterion("show_ali like", value, "showAli");
            return (Criteria) this;
        }

        public Criteria andShowAliNotLike(String value) {
            addCriterion("show_ali not like", value, "showAli");
            return (Criteria) this;
        }

        public Criteria andShowAliIn(List<String> values) {
            addCriterion("show_ali in", values, "showAli");
            return (Criteria) this;
        }

        public Criteria andShowAliNotIn(List<String> values) {
            addCriterion("show_ali not in", values, "showAli");
            return (Criteria) this;
        }

        public Criteria andShowAliBetween(String value1, String value2) {
            addCriterion("show_ali between", value1, value2, "showAli");
            return (Criteria) this;
        }

        public Criteria andShowAliNotBetween(String value1, String value2) {
            addCriterion("show_ali not between", value1, value2, "showAli");
            return (Criteria) this;
        }

        public Criteria andZbxMoneyIsNull() {
            addCriterion("zbx_money is null");
            return (Criteria) this;
        }

        public Criteria andZbxMoneyIsNotNull() {
            addCriterion("zbx_money is not null");
            return (Criteria) this;
        }

        public Criteria andZbxMoneyEqualTo(String value) {
            addCriterion("zbx_money =", value, "zbxMoney");
            return (Criteria) this;
        }

        public Criteria andZbxMoneyNotEqualTo(String value) {
            addCriterion("zbx_money <>", value, "zbxMoney");
            return (Criteria) this;
        }

        public Criteria andZbxMoneyGreaterThan(String value) {
            addCriterion("zbx_money >", value, "zbxMoney");
            return (Criteria) this;
        }

        public Criteria andZbxMoneyGreaterThanOrEqualTo(String value) {
            addCriterion("zbx_money >=", value, "zbxMoney");
            return (Criteria) this;
        }

        public Criteria andZbxMoneyLessThan(String value) {
            addCriterion("zbx_money <", value, "zbxMoney");
            return (Criteria) this;
        }

        public Criteria andZbxMoneyLessThanOrEqualTo(String value) {
            addCriterion("zbx_money <=", value, "zbxMoney");
            return (Criteria) this;
        }

        public Criteria andZbxMoneyLike(String value) {
            addCriterion("zbx_money like", value, "zbxMoney");
            return (Criteria) this;
        }

        public Criteria andZbxMoneyNotLike(String value) {
            addCriterion("zbx_money not like", value, "zbxMoney");
            return (Criteria) this;
        }

        public Criteria andZbxMoneyIn(List<String> values) {
            addCriterion("zbx_money in", values, "zbxMoney");
            return (Criteria) this;
        }

        public Criteria andZbxMoneyNotIn(List<String> values) {
            addCriterion("zbx_money not in", values, "zbxMoney");
            return (Criteria) this;
        }

        public Criteria andZbxMoneyBetween(String value1, String value2) {
            addCriterion("zbx_money between", value1, value2, "zbxMoney");
            return (Criteria) this;
        }

        public Criteria andZbxMoneyNotBetween(String value1, String value2) {
            addCriterion("zbx_money not between", value1, value2, "zbxMoney");
            return (Criteria) this;
        }

        public Criteria andFyxMoneyIsNull() {
            addCriterion("fyx_money is null");
            return (Criteria) this;
        }

        public Criteria andFyxMoneyIsNotNull() {
            addCriterion("fyx_money is not null");
            return (Criteria) this;
        }

        public Criteria andFyxMoneyEqualTo(String value) {
            addCriterion("fyx_money =", value, "fyxMoney");
            return (Criteria) this;
        }

        public Criteria andFyxMoneyNotEqualTo(String value) {
            addCriterion("fyx_money <>", value, "fyxMoney");
            return (Criteria) this;
        }

        public Criteria andFyxMoneyGreaterThan(String value) {
            addCriterion("fyx_money >", value, "fyxMoney");
            return (Criteria) this;
        }

        public Criteria andFyxMoneyGreaterThanOrEqualTo(String value) {
            addCriterion("fyx_money >=", value, "fyxMoney");
            return (Criteria) this;
        }

        public Criteria andFyxMoneyLessThan(String value) {
            addCriterion("fyx_money <", value, "fyxMoney");
            return (Criteria) this;
        }

        public Criteria andFyxMoneyLessThanOrEqualTo(String value) {
            addCriterion("fyx_money <=", value, "fyxMoney");
            return (Criteria) this;
        }

        public Criteria andFyxMoneyLike(String value) {
            addCriterion("fyx_money like", value, "fyxMoney");
            return (Criteria) this;
        }

        public Criteria andFyxMoneyNotLike(String value) {
            addCriterion("fyx_money not like", value, "fyxMoney");
            return (Criteria) this;
        }

        public Criteria andFyxMoneyIn(List<String> values) {
            addCriterion("fyx_money in", values, "fyxMoney");
            return (Criteria) this;
        }

        public Criteria andFyxMoneyNotIn(List<String> values) {
            addCriterion("fyx_money not in", values, "fyxMoney");
            return (Criteria) this;
        }

        public Criteria andFyxMoneyBetween(String value1, String value2) {
            addCriterion("fyx_money between", value1, value2, "fyxMoney");
            return (Criteria) this;
        }

        public Criteria andFyxMoneyNotBetween(String value1, String value2) {
            addCriterion("fyx_money not between", value1, value2, "fyxMoney");
            return (Criteria) this;
        }

        public Criteria andMoneyLevelIsNull() {
            addCriterion("money_level is null");
            return (Criteria) this;
        }

        public Criteria andMoneyLevelIsNotNull() {
            addCriterion("money_level is not null");
            return (Criteria) this;
        }

        public Criteria andMoneyLevelEqualTo(String value) {
            addCriterion("money_level =", value, "moneyLevel");
            return (Criteria) this;
        }

        public Criteria andMoneyLevelNotEqualTo(String value) {
            addCriterion("money_level <>", value, "moneyLevel");
            return (Criteria) this;
        }

        public Criteria andMoneyLevelGreaterThan(String value) {
            addCriterion("money_level >", value, "moneyLevel");
            return (Criteria) this;
        }

        public Criteria andMoneyLevelGreaterThanOrEqualTo(String value) {
            addCriterion("money_level >=", value, "moneyLevel");
            return (Criteria) this;
        }

        public Criteria andMoneyLevelLessThan(String value) {
            addCriterion("money_level <", value, "moneyLevel");
            return (Criteria) this;
        }

        public Criteria andMoneyLevelLessThanOrEqualTo(String value) {
            addCriterion("money_level <=", value, "moneyLevel");
            return (Criteria) this;
        }

        public Criteria andMoneyLevelLike(String value) {
            addCriterion("money_level like", value, "moneyLevel");
            return (Criteria) this;
        }

        public Criteria andMoneyLevelNotLike(String value) {
            addCriterion("money_level not like", value, "moneyLevel");
            return (Criteria) this;
        }

        public Criteria andMoneyLevelIn(List<String> values) {
            addCriterion("money_level in", values, "moneyLevel");
            return (Criteria) this;
        }

        public Criteria andMoneyLevelNotIn(List<String> values) {
            addCriterion("money_level not in", values, "moneyLevel");
            return (Criteria) this;
        }

        public Criteria andMoneyLevelBetween(String value1, String value2) {
            addCriterion("money_level between", value1, value2, "moneyLevel");
            return (Criteria) this;
        }

        public Criteria andMoneyLevelNotBetween(String value1, String value2) {
            addCriterion("money_level not between", value1, value2, "moneyLevel");
            return (Criteria) this;
        }

        public Criteria andDetailsNameIsNull() {
            addCriterion("details_name is null");
            return (Criteria) this;
        }

        public Criteria andDetailsNameIsNotNull() {
            addCriterion("details_name is not null");
            return (Criteria) this;
        }

        public Criteria andDetailsNameEqualTo(String value) {
            addCriterion("details_name =", value, "detailsName");
            return (Criteria) this;
        }

        public Criteria andDetailsNameNotEqualTo(String value) {
            addCriterion("details_name <>", value, "detailsName");
            return (Criteria) this;
        }

        public Criteria andDetailsNameGreaterThan(String value) {
            addCriterion("details_name >", value, "detailsName");
            return (Criteria) this;
        }

        public Criteria andDetailsNameGreaterThanOrEqualTo(String value) {
            addCriterion("details_name >=", value, "detailsName");
            return (Criteria) this;
        }

        public Criteria andDetailsNameLessThan(String value) {
            addCriterion("details_name <", value, "detailsName");
            return (Criteria) this;
        }

        public Criteria andDetailsNameLessThanOrEqualTo(String value) {
            addCriterion("details_name <=", value, "detailsName");
            return (Criteria) this;
        }

        public Criteria andDetailsNameLike(String value) {
            addCriterion("details_name like", value, "detailsName");
            return (Criteria) this;
        }

        public Criteria andDetailsNameNotLike(String value) {
            addCriterion("details_name not like", value, "detailsName");
            return (Criteria) this;
        }

        public Criteria andDetailsNameIn(List<String> values) {
            addCriterion("details_name in", values, "detailsName");
            return (Criteria) this;
        }

        public Criteria andDetailsNameNotIn(List<String> values) {
            addCriterion("details_name not in", values, "detailsName");
            return (Criteria) this;
        }

        public Criteria andDetailsNameBetween(String value1, String value2) {
            addCriterion("details_name between", value1, value2, "detailsName");
            return (Criteria) this;
        }

        public Criteria andDetailsNameNotBetween(String value1, String value2) {
            addCriterion("details_name not between", value1, value2, "detailsName");
            return (Criteria) this;
        }

        public Criteria andIsYjyIsNull() {
            addCriterion("is_yjy is null");
            return (Criteria) this;
        }

        public Criteria andIsYjyIsNotNull() {
            addCriterion("is_yjy is not null");
            return (Criteria) this;
        }

        public Criteria andIsYjyEqualTo(String value) {
            addCriterion("is_yjy =", value, "isYjy");
            return (Criteria) this;
        }

        public Criteria andIsYjyNotEqualTo(String value) {
            addCriterion("is_yjy <>", value, "isYjy");
            return (Criteria) this;
        }

        public Criteria andIsYjyGreaterThan(String value) {
            addCriterion("is_yjy >", value, "isYjy");
            return (Criteria) this;
        }

        public Criteria andIsYjyGreaterThanOrEqualTo(String value) {
            addCriterion("is_yjy >=", value, "isYjy");
            return (Criteria) this;
        }

        public Criteria andIsYjyLessThan(String value) {
            addCriterion("is_yjy <", value, "isYjy");
            return (Criteria) this;
        }

        public Criteria andIsYjyLessThanOrEqualTo(String value) {
            addCriterion("is_yjy <=", value, "isYjy");
            return (Criteria) this;
        }

        public Criteria andIsYjyLike(String value) {
            addCriterion("is_yjy like", value, "isYjy");
            return (Criteria) this;
        }

        public Criteria andIsYjyNotLike(String value) {
            addCriterion("is_yjy not like", value, "isYjy");
            return (Criteria) this;
        }

        public Criteria andIsYjyIn(List<String> values) {
            addCriterion("is_yjy in", values, "isYjy");
            return (Criteria) this;
        }

        public Criteria andIsYjyNotIn(List<String> values) {
            addCriterion("is_yjy not in", values, "isYjy");
            return (Criteria) this;
        }

        public Criteria andIsYjyBetween(String value1, String value2) {
            addCriterion("is_yjy between", value1, value2, "isYjy");
            return (Criteria) this;
        }

        public Criteria andIsYjyNotBetween(String value1, String value2) {
            addCriterion("is_yjy not between", value1, value2, "isYjy");
            return (Criteria) this;
        }

        public Criteria andTypeNameIsNull() {
            addCriterion("type_name is null");
            return (Criteria) this;
        }

        public Criteria andTypeNameIsNotNull() {
            addCriterion("type_name is not null");
            return (Criteria) this;
        }

        public Criteria andTypeNameEqualTo(String value) {
            addCriterion("type_name =", value, "typeName");
            return (Criteria) this;
        }

        public Criteria andTypeNameNotEqualTo(String value) {
            addCriterion("type_name <>", value, "typeName");
            return (Criteria) this;
        }

        public Criteria andTypeNameGreaterThan(String value) {
            addCriterion("type_name >", value, "typeName");
            return (Criteria) this;
        }

        public Criteria andTypeNameGreaterThanOrEqualTo(String value) {
            addCriterion("type_name >=", value, "typeName");
            return (Criteria) this;
        }

        public Criteria andTypeNameLessThan(String value) {
            addCriterion("type_name <", value, "typeName");
            return (Criteria) this;
        }

        public Criteria andTypeNameLessThanOrEqualTo(String value) {
            addCriterion("type_name <=", value, "typeName");
            return (Criteria) this;
        }

        public Criteria andTypeNameLike(String value) {
            addCriterion("type_name like", value, "typeName");
            return (Criteria) this;
        }

        public Criteria andTypeNameNotLike(String value) {
            addCriterion("type_name not like", value, "typeName");
            return (Criteria) this;
        }

        public Criteria andTypeNameIn(List<String> values) {
            addCriterion("type_name in", values, "typeName");
            return (Criteria) this;
        }

        public Criteria andTypeNameNotIn(List<String> values) {
            addCriterion("type_name not in", values, "typeName");
            return (Criteria) this;
        }

        public Criteria andTypeNameBetween(String value1, String value2) {
            addCriterion("type_name between", value1, value2, "typeName");
            return (Criteria) this;
        }

        public Criteria andTypeNameNotBetween(String value1, String value2) {
            addCriterion("type_name not between", value1, value2, "typeName");
            return (Criteria) this;
        }

        public Criteria andSheetFlagIsNull() {
            addCriterion("sheet_flag is null");
            return (Criteria) this;
        }

        public Criteria andSheetFlagIsNotNull() {
            addCriterion("sheet_flag is not null");
            return (Criteria) this;
        }

        public Criteria andSheetFlagEqualTo(String value) {
            addCriterion("sheet_flag =", value, "sheetFlag");
            return (Criteria) this;
        }

        public Criteria andSheetFlagNotEqualTo(String value) {
            addCriterion("sheet_flag <>", value, "sheetFlag");
            return (Criteria) this;
        }

        public Criteria andSheetFlagGreaterThan(String value) {
            addCriterion("sheet_flag >", value, "sheetFlag");
            return (Criteria) this;
        }

        public Criteria andSheetFlagGreaterThanOrEqualTo(String value) {
            addCriterion("sheet_flag >=", value, "sheetFlag");
            return (Criteria) this;
        }

        public Criteria andSheetFlagLessThan(String value) {
            addCriterion("sheet_flag <", value, "sheetFlag");
            return (Criteria) this;
        }

        public Criteria andSheetFlagLessThanOrEqualTo(String value) {
            addCriterion("sheet_flag <=", value, "sheetFlag");
            return (Criteria) this;
        }

        public Criteria andSheetFlagLike(String value) {
            addCriterion("sheet_flag like", value, "sheetFlag");
            return (Criteria) this;
        }

        public Criteria andSheetFlagNotLike(String value) {
            addCriterion("sheet_flag not like", value, "sheetFlag");
            return (Criteria) this;
        }

        public Criteria andSheetFlagIn(List<String> values) {
            addCriterion("sheet_flag in", values, "sheetFlag");
            return (Criteria) this;
        }

        public Criteria andSheetFlagNotIn(List<String> values) {
            addCriterion("sheet_flag not in", values, "sheetFlag");
            return (Criteria) this;
        }

        public Criteria andSheetFlagBetween(String value1, String value2) {
            addCriterion("sheet_flag between", value1, value2, "sheetFlag");
            return (Criteria) this;
        }

        public Criteria andSheetFlagNotBetween(String value1, String value2) {
            addCriterion("sheet_flag not between", value1, value2, "sheetFlag");
            return (Criteria) this;
        }

        public Criteria andShowOrderIsNull() {
            addCriterion("show_order is null");
            return (Criteria) this;
        }

        public Criteria andShowOrderIsNotNull() {
            addCriterion("show_order is not null");
            return (Criteria) this;
        }

        public Criteria andShowOrderEqualTo(String value) {
            addCriterion("show_order =", value, "showOrder");
            return (Criteria) this;
        }

        public Criteria andShowOrderNotEqualTo(String value) {
            addCriterion("show_order <>", value, "showOrder");
            return (Criteria) this;
        }

        public Criteria andShowOrderGreaterThan(String value) {
            addCriterion("show_order >", value, "showOrder");
            return (Criteria) this;
        }

        public Criteria andShowOrderGreaterThanOrEqualTo(String value) {
            addCriterion("show_order >=", value, "showOrder");
            return (Criteria) this;
        }

        public Criteria andShowOrderLessThan(String value) {
            addCriterion("show_order <", value, "showOrder");
            return (Criteria) this;
        }

        public Criteria andShowOrderLessThanOrEqualTo(String value) {
            addCriterion("show_order <=", value, "showOrder");
            return (Criteria) this;
        }

        public Criteria andShowOrderLike(String value) {
            addCriterion("show_order like", value, "showOrder");
            return (Criteria) this;
        }

        public Criteria andShowOrderNotLike(String value) {
            addCriterion("show_order not like", value, "showOrder");
            return (Criteria) this;
        }

        public Criteria andShowOrderIn(List<String> values) {
            addCriterion("show_order in", values, "showOrder");
            return (Criteria) this;
        }

        public Criteria andShowOrderNotIn(List<String> values) {
            addCriterion("show_order not in", values, "showOrder");
            return (Criteria) this;
        }

        public Criteria andShowOrderBetween(String value1, String value2) {
            addCriterion("show_order between", value1, value2, "showOrder");
            return (Criteria) this;
        }

        public Criteria andShowOrderNotBetween(String value1, String value2) {
            addCriterion("show_order not between", value1, value2, "showOrder");
            return (Criteria) this;
        }

        public Criteria andNoIsNull() {
            addCriterion("no is null");
            return (Criteria) this;
        }

        public Criteria andNoIsNotNull() {
            addCriterion("no is not null");
            return (Criteria) this;
        }

        public Criteria andNoEqualTo(Integer value) {
            addCriterion("no =", value, "no");
            return (Criteria) this;
        }

        public Criteria andNoNotEqualTo(Integer value) {
            addCriterion("no <>", value, "no");
            return (Criteria) this;
        }

        public Criteria andNoGreaterThan(Integer value) {
            addCriterion("no >", value, "no");
            return (Criteria) this;
        }

        public Criteria andNoGreaterThanOrEqualTo(Integer value) {
            addCriterion("no >=", value, "no");
            return (Criteria) this;
        }

        public Criteria andNoLessThan(Integer value) {
            addCriterion("no <", value, "no");
            return (Criteria) this;
        }

        public Criteria andNoLessThanOrEqualTo(Integer value) {
            addCriterion("no <=", value, "no");
            return (Criteria) this;
        }

        public Criteria andNoIn(List<Integer> values) {
            addCriterion("no in", values, "no");
            return (Criteria) this;
        }

        public Criteria andNoNotIn(List<Integer> values) {
            addCriterion("no not in", values, "no");
            return (Criteria) this;
        }

        public Criteria andNoBetween(Integer value1, Integer value2) {
            addCriterion("no between", value1, value2, "no");
            return (Criteria) this;
        }

        public Criteria andNoNotBetween(Integer value1, Integer value2) {
            addCriterion("no not between", value1, value2, "no");
            return (Criteria) this;
        }

        public Criteria andLevelIsNull() {
            addCriterion("level is null");
            return (Criteria) this;
        }

        public Criteria andLevelIsNotNull() {
            addCriterion("level is not null");
            return (Criteria) this;
        }

        public Criteria andLevelEqualTo(Integer value) {
            addCriterion("level =", value, "level");
            return (Criteria) this;
        }

        public Criteria andLevelNotEqualTo(Integer value) {
            addCriterion("level <>", value, "level");
            return (Criteria) this;
        }

        public Criteria andLevelGreaterThan(Integer value) {
            addCriterion("level >", value, "level");
            return (Criteria) this;
        }

        public Criteria andLevelGreaterThanOrEqualTo(Integer value) {
            addCriterion("level >=", value, "level");
            return (Criteria) this;
        }

        public Criteria andLevelLessThan(Integer value) {
            addCriterion("level <", value, "level");
            return (Criteria) this;
        }

        public Criteria andLevelLessThanOrEqualTo(Integer value) {
            addCriterion("level <=", value, "level");
            return (Criteria) this;
        }

        public Criteria andLevelIn(List<Integer> values) {
            addCriterion("level in", values, "level");
            return (Criteria) this;
        }

        public Criteria andLevelNotIn(List<Integer> values) {
            addCriterion("level not in", values, "level");
            return (Criteria) this;
        }

        public Criteria andLevelBetween(Integer value1, Integer value2) {
            addCriterion("level between", value1, value2, "level");
            return (Criteria) this;
        }

        public Criteria andLevelNotBetween(Integer value1, Integer value2) {
            addCriterion("level not between", value1, value2, "level");
            return (Criteria) this;
        }

        public Criteria andBudgetTypeIsNull() {
            addCriterion("budget_type is null");
            return (Criteria) this;
        }

        public Criteria andBudgetTypeIsNotNull() {
            addCriterion("budget_type is not null");
            return (Criteria) this;
        }

        public Criteria andBudgetTypeEqualTo(Integer value) {
            addCriterion("budget_type =", value, "budgetType");
            return (Criteria) this;
        }

        public Criteria andBudgetTypeNotEqualTo(Integer value) {
            addCriterion("budget_type <>", value, "budgetType");
            return (Criteria) this;
        }

        public Criteria andBudgetTypeGreaterThan(Integer value) {
            addCriterion("budget_type >", value, "budgetType");
            return (Criteria) this;
        }

        public Criteria andBudgetTypeGreaterThanOrEqualTo(Integer value) {
            addCriterion("budget_type >=", value, "budgetType");
            return (Criteria) this;
        }

        public Criteria andBudgetTypeLessThan(Integer value) {
            addCriterion("budget_type <", value, "budgetType");
            return (Criteria) this;
        }

        public Criteria andBudgetTypeLessThanOrEqualTo(Integer value) {
            addCriterion("budget_type <=", value, "budgetType");
            return (Criteria) this;
        }

        public Criteria andBudgetTypeIn(List<Integer> values) {
            addCriterion("budget_type in", values, "budgetType");
            return (Criteria) this;
        }

        public Criteria andBudgetTypeNotIn(List<Integer> values) {
            addCriterion("budget_type not in", values, "budgetType");
            return (Criteria) this;
        }

        public Criteria andBudgetTypeBetween(Integer value1, Integer value2) {
            addCriterion("budget_type between", value1, value2, "budgetType");
            return (Criteria) this;
        }

        public Criteria andBudgetTypeNotBetween(Integer value1, Integer value2) {
            addCriterion("budget_type not between", value1, value2, "budgetType");
            return (Criteria) this;
        }

        public Criteria andBudgetInfoIdIsNull() {
            addCriterion("budget_info_id is null");
            return (Criteria) this;
        }

        public Criteria andBudgetInfoIdIsNotNull() {
            addCriterion("budget_info_id is not null");
            return (Criteria) this;
        }

        public Criteria andBudgetInfoIdEqualTo(String value) {
            addCriterion("budget_info_id =", value, "budgetInfoId");
            return (Criteria) this;
        }

        public Criteria andBudgetInfoIdNotEqualTo(String value) {
            addCriterion("budget_info_id <>", value, "budgetInfoId");
            return (Criteria) this;
        }

        public Criteria andBudgetInfoIdGreaterThan(String value) {
            addCriterion("budget_info_id >", value, "budgetInfoId");
            return (Criteria) this;
        }

        public Criteria andBudgetInfoIdGreaterThanOrEqualTo(String value) {
            addCriterion("budget_info_id >=", value, "budgetInfoId");
            return (Criteria) this;
        }

        public Criteria andBudgetInfoIdLessThan(String value) {
            addCriterion("budget_info_id <", value, "budgetInfoId");
            return (Criteria) this;
        }

        public Criteria andBudgetInfoIdLessThanOrEqualTo(String value) {
            addCriterion("budget_info_id <=", value, "budgetInfoId");
            return (Criteria) this;
        }

        public Criteria andBudgetInfoIdLike(String value) {
            addCriterion("budget_info_id like", value, "budgetInfoId");
            return (Criteria) this;
        }

        public Criteria andBudgetInfoIdNotLike(String value) {
            addCriterion("budget_info_id not like", value, "budgetInfoId");
            return (Criteria) this;
        }

        public Criteria andBudgetInfoIdIn(List<String> values) {
            addCriterion("budget_info_id in", values, "budgetInfoId");
            return (Criteria) this;
        }

        public Criteria andBudgetInfoIdNotIn(List<String> values) {
            addCriterion("budget_info_id not in", values, "budgetInfoId");
            return (Criteria) this;
        }

        public Criteria andBudgetInfoIdBetween(String value1, String value2) {
            addCriterion("budget_info_id between", value1, value2, "budgetInfoId");
            return (Criteria) this;
        }

        public Criteria andBudgetInfoIdNotBetween(String value1, String value2) {
            addCriterion("budget_info_id not between", value1, value2, "budgetInfoId");
            return (Criteria) this;
        }

        public Criteria andBak1IsNull() {
            addCriterion("bak1 is null");
            return (Criteria) this;
        }

        public Criteria andBak1IsNotNull() {
            addCriterion("bak1 is not null");
            return (Criteria) this;
        }

        public Criteria andBak1EqualTo(String value) {
            addCriterion("bak1 =", value, "bak1");
            return (Criteria) this;
        }

        public Criteria andBak1NotEqualTo(String value) {
            addCriterion("bak1 <>", value, "bak1");
            return (Criteria) this;
        }

        public Criteria andBak1GreaterThan(String value) {
            addCriterion("bak1 >", value, "bak1");
            return (Criteria) this;
        }

        public Criteria andBak1GreaterThanOrEqualTo(String value) {
            addCriterion("bak1 >=", value, "bak1");
            return (Criteria) this;
        }

        public Criteria andBak1LessThan(String value) {
            addCriterion("bak1 <", value, "bak1");
            return (Criteria) this;
        }

        public Criteria andBak1LessThanOrEqualTo(String value) {
            addCriterion("bak1 <=", value, "bak1");
            return (Criteria) this;
        }

        public Criteria andBak1Like(String value) {
            addCriterion("bak1 like", value, "bak1");
            return (Criteria) this;
        }

        public Criteria andBak1NotLike(String value) {
            addCriterion("bak1 not like", value, "bak1");
            return (Criteria) this;
        }

        public Criteria andBak1In(List<String> values) {
            addCriterion("bak1 in", values, "bak1");
            return (Criteria) this;
        }

        public Criteria andBak1NotIn(List<String> values) {
            addCriterion("bak1 not in", values, "bak1");
            return (Criteria) this;
        }

        public Criteria andBak1Between(String value1, String value2) {
            addCriterion("bak1 between", value1, value2, "bak1");
            return (Criteria) this;
        }

        public Criteria andBak1NotBetween(String value1, String value2) {
            addCriterion("bak1 not between", value1, value2, "bak1");
            return (Criteria) this;
        }

        public Criteria andBak2IsNull() {
            addCriterion("bak2 is null");
            return (Criteria) this;
        }

        public Criteria andBak2IsNotNull() {
            addCriterion("bak2 is not null");
            return (Criteria) this;
        }

        public Criteria andBak2EqualTo(String value) {
            addCriterion("bak2 =", value, "bak2");
            return (Criteria) this;
        }

        public Criteria andBak2NotEqualTo(String value) {
            addCriterion("bak2 <>", value, "bak2");
            return (Criteria) this;
        }

        public Criteria andBak2GreaterThan(String value) {
            addCriterion("bak2 >", value, "bak2");
            return (Criteria) this;
        }

        public Criteria andBak2GreaterThanOrEqualTo(String value) {
            addCriterion("bak2 >=", value, "bak2");
            return (Criteria) this;
        }

        public Criteria andBak2LessThan(String value) {
            addCriterion("bak2 <", value, "bak2");
            return (Criteria) this;
        }

        public Criteria andBak2LessThanOrEqualTo(String value) {
            addCriterion("bak2 <=", value, "bak2");
            return (Criteria) this;
        }

        public Criteria andBak2Like(String value) {
            addCriterion("bak2 like", value, "bak2");
            return (Criteria) this;
        }

        public Criteria andBak2NotLike(String value) {
            addCriterion("bak2 not like", value, "bak2");
            return (Criteria) this;
        }

        public Criteria andBak2In(List<String> values) {
            addCriterion("bak2 in", values, "bak2");
            return (Criteria) this;
        }

        public Criteria andBak2NotIn(List<String> values) {
            addCriterion("bak2 not in", values, "bak2");
            return (Criteria) this;
        }

        public Criteria andBak2Between(String value1, String value2) {
            addCriterion("bak2 between", value1, value2, "bak2");
            return (Criteria) this;
        }

        public Criteria andBak2NotBetween(String value1, String value2) {
            addCriterion("bak2 not between", value1, value2, "bak2");
            return (Criteria) this;
        }

        public Criteria andBak3IsNull() {
            addCriterion("bak3 is null");
            return (Criteria) this;
        }

        public Criteria andBak3IsNotNull() {
            addCriterion("bak3 is not null");
            return (Criteria) this;
        }

        public Criteria andBak3EqualTo(String value) {
            addCriterion("bak3 =", value, "bak3");
            return (Criteria) this;
        }

        public Criteria andBak3NotEqualTo(String value) {
            addCriterion("bak3 <>", value, "bak3");
            return (Criteria) this;
        }

        public Criteria andBak3GreaterThan(String value) {
            addCriterion("bak3 >", value, "bak3");
            return (Criteria) this;
        }

        public Criteria andBak3GreaterThanOrEqualTo(String value) {
            addCriterion("bak3 >=", value, "bak3");
            return (Criteria) this;
        }

        public Criteria andBak3LessThan(String value) {
            addCriterion("bak3 <", value, "bak3");
            return (Criteria) this;
        }

        public Criteria andBak3LessThanOrEqualTo(String value) {
            addCriterion("bak3 <=", value, "bak3");
            return (Criteria) this;
        }

        public Criteria andBak3Like(String value) {
            addCriterion("bak3 like", value, "bak3");
            return (Criteria) this;
        }

        public Criteria andBak3NotLike(String value) {
            addCriterion("bak3 not like", value, "bak3");
            return (Criteria) this;
        }

        public Criteria andBak3In(List<String> values) {
            addCriterion("bak3 in", values, "bak3");
            return (Criteria) this;
        }

        public Criteria andBak3NotIn(List<String> values) {
            addCriterion("bak3 not in", values, "bak3");
            return (Criteria) this;
        }

        public Criteria andBak3Between(String value1, String value2) {
            addCriterion("bak3 between", value1, value2, "bak3");
            return (Criteria) this;
        }

        public Criteria andBak3NotBetween(String value1, String value2) {
            addCriterion("bak3 not between", value1, value2, "bak3");
            return (Criteria) this;
        }

        public Criteria andBak4IsNull() {
            addCriterion("bak4 is null");
            return (Criteria) this;
        }

        public Criteria andBak4IsNotNull() {
            addCriterion("bak4 is not null");
            return (Criteria) this;
        }

        public Criteria andBak4EqualTo(String value) {
            addCriterion("bak4 =", value, "bak4");
            return (Criteria) this;
        }

        public Criteria andBak4NotEqualTo(String value) {
            addCriterion("bak4 <>", value, "bak4");
            return (Criteria) this;
        }

        public Criteria andBak4GreaterThan(String value) {
            addCriterion("bak4 >", value, "bak4");
            return (Criteria) this;
        }

        public Criteria andBak4GreaterThanOrEqualTo(String value) {
            addCriterion("bak4 >=", value, "bak4");
            return (Criteria) this;
        }

        public Criteria andBak4LessThan(String value) {
            addCriterion("bak4 <", value, "bak4");
            return (Criteria) this;
        }

        public Criteria andBak4LessThanOrEqualTo(String value) {
            addCriterion("bak4 <=", value, "bak4");
            return (Criteria) this;
        }

        public Criteria andBak4Like(String value) {
            addCriterion("bak4 like", value, "bak4");
            return (Criteria) this;
        }

        public Criteria andBak4NotLike(String value) {
            addCriterion("bak4 not like", value, "bak4");
            return (Criteria) this;
        }

        public Criteria andBak4In(List<String> values) {
            addCriterion("bak4 in", values, "bak4");
            return (Criteria) this;
        }

        public Criteria andBak4NotIn(List<String> values) {
            addCriterion("bak4 not in", values, "bak4");
            return (Criteria) this;
        }

        public Criteria andBak4Between(String value1, String value2) {
            addCriterion("bak4 between", value1, value2, "bak4");
            return (Criteria) this;
        }

        public Criteria andBak4NotBetween(String value1, String value2) {
            addCriterion("bak4 not between", value1, value2, "bak4");
            return (Criteria) this;
        }

        public Criteria andBak5IsNull() {
            addCriterion("bak5 is null");
            return (Criteria) this;
        }

        public Criteria andBak5IsNotNull() {
            addCriterion("bak5 is not null");
            return (Criteria) this;
        }

        public Criteria andBak5EqualTo(String value) {
            addCriterion("bak5 =", value, "bak5");
            return (Criteria) this;
        }

        public Criteria andBak5NotEqualTo(String value) {
            addCriterion("bak5 <>", value, "bak5");
            return (Criteria) this;
        }

        public Criteria andBak5GreaterThan(String value) {
            addCriterion("bak5 >", value, "bak5");
            return (Criteria) this;
        }

        public Criteria andBak5GreaterThanOrEqualTo(String value) {
            addCriterion("bak5 >=", value, "bak5");
            return (Criteria) this;
        }

        public Criteria andBak5LessThan(String value) {
            addCriterion("bak5 <", value, "bak5");
            return (Criteria) this;
        }

        public Criteria andBak5LessThanOrEqualTo(String value) {
            addCriterion("bak5 <=", value, "bak5");
            return (Criteria) this;
        }

        public Criteria andBak5Like(String value) {
            addCriterion("bak5 like", value, "bak5");
            return (Criteria) this;
        }

        public Criteria andBak5NotLike(String value) {
            addCriterion("bak5 not like", value, "bak5");
            return (Criteria) this;
        }

        public Criteria andBak5In(List<String> values) {
            addCriterion("bak5 in", values, "bak5");
            return (Criteria) this;
        }

        public Criteria andBak5NotIn(List<String> values) {
            addCriterion("bak5 not in", values, "bak5");
            return (Criteria) this;
        }

        public Criteria andBak5Between(String value1, String value2) {
            addCriterion("bak5 between", value1, value2, "bak5");
            return (Criteria) this;
        }

        public Criteria andBak5NotBetween(String value1, String value2) {
            addCriterion("bak5 not between", value1, value2, "bak5");
            return (Criteria) this;
        }
    }

    public static class Criteria extends GeneratedCriteria {

        protected Criteria() {
            super();
        }
    }

    public static class Criterion {
        private String condition;

        private Object value;

        private Object secondValue;

        private boolean noValue;

        private boolean singleValue;

        private boolean betweenValue;

        private boolean listValue;

        private String typeHandler;

        public String getCondition() {
            return condition;
        }

        public Object getValue() {
            return value;
        }

        public Object getSecondValue() {
            return secondValue;
        }

        public boolean isNoValue() {
            return noValue;
        }

        public boolean isSingleValue() {
            return singleValue;
        }

        public boolean isBetweenValue() {
            return betweenValue;
        }

        public boolean isListValue() {
            return listValue;
        }

        public String getTypeHandler() {
            return typeHandler;
        }

        protected Criterion(String condition) {
            super();
            this.condition = condition;
            this.typeHandler = null;
            this.noValue = true;
        }

        protected Criterion(String condition, Object value, String typeHandler) {
            super();
            this.condition = condition;
            this.value = value;
            this.typeHandler = typeHandler;
            if (value instanceof List<?>) {
                this.listValue = true;
            } else {
                this.singleValue = true;
            }
        }

        protected Criterion(String condition, Object value) {
            this(condition, value, null);
        }

        protected Criterion(String condition, Object value, Object secondValue, String typeHandler) {
            super();
            this.condition = condition;
            this.value = value;
            this.secondValue = secondValue;
            this.typeHandler = typeHandler;
            this.betweenValue = true;
        }

        protected Criterion(String condition, Object value, Object secondValue) {
            this(condition, value, secondValue, null);
        }
    }
}