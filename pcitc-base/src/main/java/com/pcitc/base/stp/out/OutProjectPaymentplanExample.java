package com.pcitc.base.stp.out;

import java.util.ArrayList;
import java.util.List;

public class OutProjectPaymentplanExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    private Integer limit;

    private Long offset;

    public OutProjectPaymentplanExample() {
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

    public void setLimit(Integer limit) {
        this.limit = limit;
    }

    public Integer getLimit() {
        return limit;
    }

    public void setOffset(Long offset) {
        this.offset = offset;
    }

    public Long getOffset() {
        return offset;
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

        public Criteria andXmidIsNull() {
            addCriterion("xmid is null");
            return (Criteria) this;
        }

        public Criteria andXmidIsNotNull() {
            addCriterion("xmid is not null");
            return (Criteria) this;
        }

        public Criteria andXmidEqualTo(String value) {
            addCriterion("xmid =", value, "xmid");
            return (Criteria) this;
        }

        public Criteria andXmidNotEqualTo(String value) {
            addCriterion("xmid <>", value, "xmid");
            return (Criteria) this;
        }

        public Criteria andXmidGreaterThan(String value) {
            addCriterion("xmid >", value, "xmid");
            return (Criteria) this;
        }

        public Criteria andXmidGreaterThanOrEqualTo(String value) {
            addCriterion("xmid >=", value, "xmid");
            return (Criteria) this;
        }

        public Criteria andXmidLessThan(String value) {
            addCriterion("xmid <", value, "xmid");
            return (Criteria) this;
        }

        public Criteria andXmidLessThanOrEqualTo(String value) {
            addCriterion("xmid <=", value, "xmid");
            return (Criteria) this;
        }

        public Criteria andXmidLike(String value) {
            addCriterion("xmid like", value, "xmid");
            return (Criteria) this;
        }

        public Criteria andXmidNotLike(String value) {
            addCriterion("xmid not like", value, "xmid");
            return (Criteria) this;
        }

        public Criteria andXmidIn(List<String> values) {
            addCriterion("xmid in", values, "xmid");
            return (Criteria) this;
        }

        public Criteria andXmidNotIn(List<String> values) {
            addCriterion("xmid not in", values, "xmid");
            return (Criteria) this;
        }

        public Criteria andXmidBetween(String value1, String value2) {
            addCriterion("xmid between", value1, value2, "xmid");
            return (Criteria) this;
        }

        public Criteria andXmidNotBetween(String value1, String value2) {
            addCriterion("xmid not between", value1, value2, "xmid");
            return (Criteria) this;
        }

        public Criteria andXmmcIsNull() {
            addCriterion("xmmc is null");
            return (Criteria) this;
        }

        public Criteria andXmmcIsNotNull() {
            addCriterion("xmmc is not null");
            return (Criteria) this;
        }

        public Criteria andXmmcEqualTo(String value) {
            addCriterion("xmmc =", value, "xmmc");
            return (Criteria) this;
        }

        public Criteria andXmmcNotEqualTo(String value) {
            addCriterion("xmmc <>", value, "xmmc");
            return (Criteria) this;
        }

        public Criteria andXmmcGreaterThan(String value) {
            addCriterion("xmmc >", value, "xmmc");
            return (Criteria) this;
        }

        public Criteria andXmmcGreaterThanOrEqualTo(String value) {
            addCriterion("xmmc >=", value, "xmmc");
            return (Criteria) this;
        }

        public Criteria andXmmcLessThan(String value) {
            addCriterion("xmmc <", value, "xmmc");
            return (Criteria) this;
        }

        public Criteria andXmmcLessThanOrEqualTo(String value) {
            addCriterion("xmmc <=", value, "xmmc");
            return (Criteria) this;
        }

        public Criteria andXmmcLike(String value) {
            addCriterion("xmmc like", value, "xmmc");
            return (Criteria) this;
        }

        public Criteria andXmmcNotLike(String value) {
            addCriterion("xmmc not like", value, "xmmc");
            return (Criteria) this;
        }

        public Criteria andXmmcIn(List<String> values) {
            addCriterion("xmmc in", values, "xmmc");
            return (Criteria) this;
        }

        public Criteria andXmmcNotIn(List<String> values) {
            addCriterion("xmmc not in", values, "xmmc");
            return (Criteria) this;
        }

        public Criteria andXmmcBetween(String value1, String value2) {
            addCriterion("xmmc between", value1, value2, "xmmc");
            return (Criteria) this;
        }

        public Criteria andXmmcNotBetween(String value1, String value2) {
            addCriterion("xmmc not between", value1, value2, "xmmc");
            return (Criteria) this;
        }

        public Criteria andHthIsNull() {
            addCriterion("hth is null");
            return (Criteria) this;
        }

        public Criteria andHthIsNotNull() {
            addCriterion("hth is not null");
            return (Criteria) this;
        }

        public Criteria andHthEqualTo(String value) {
            addCriterion("hth =", value, "hth");
            return (Criteria) this;
        }

        public Criteria andHthNotEqualTo(String value) {
            addCriterion("hth <>", value, "hth");
            return (Criteria) this;
        }

        public Criteria andHthGreaterThan(String value) {
            addCriterion("hth >", value, "hth");
            return (Criteria) this;
        }

        public Criteria andHthGreaterThanOrEqualTo(String value) {
            addCriterion("hth >=", value, "hth");
            return (Criteria) this;
        }

        public Criteria andHthLessThan(String value) {
            addCriterion("hth <", value, "hth");
            return (Criteria) this;
        }

        public Criteria andHthLessThanOrEqualTo(String value) {
            addCriterion("hth <=", value, "hth");
            return (Criteria) this;
        }

        public Criteria andHthLike(String value) {
            addCriterion("hth like", value, "hth");
            return (Criteria) this;
        }

        public Criteria andHthNotLike(String value) {
            addCriterion("hth not like", value, "hth");
            return (Criteria) this;
        }

        public Criteria andHthIn(List<String> values) {
            addCriterion("hth in", values, "hth");
            return (Criteria) this;
        }

        public Criteria andHthNotIn(List<String> values) {
            addCriterion("hth not in", values, "hth");
            return (Criteria) this;
        }

        public Criteria andHthBetween(String value1, String value2) {
            addCriterion("hth between", value1, value2, "hth");
            return (Criteria) this;
        }

        public Criteria andHthNotBetween(String value1, String value2) {
            addCriterion("hth not between", value1, value2, "hth");
            return (Criteria) this;
        }

        public Criteria andYsndIsNull() {
            addCriterion("ysnd is null");
            return (Criteria) this;
        }

        public Criteria andYsndIsNotNull() {
            addCriterion("ysnd is not null");
            return (Criteria) this;
        }

        public Criteria andYsndEqualTo(String value) {
            addCriterion("ysnd =", value, "ysnd");
            return (Criteria) this;
        }

        public Criteria andYsndNotEqualTo(String value) {
            addCriterion("ysnd <>", value, "ysnd");
            return (Criteria) this;
        }

        public Criteria andYsndGreaterThan(String value) {
            addCriterion("ysnd >", value, "ysnd");
            return (Criteria) this;
        }

        public Criteria andYsndGreaterThanOrEqualTo(String value) {
            addCriterion("ysnd >=", value, "ysnd");
            return (Criteria) this;
        }

        public Criteria andYsndLessThan(String value) {
            addCriterion("ysnd <", value, "ysnd");
            return (Criteria) this;
        }

        public Criteria andYsndLessThanOrEqualTo(String value) {
            addCriterion("ysnd <=", value, "ysnd");
            return (Criteria) this;
        }

        public Criteria andYsndLike(String value) {
            addCriterion("ysnd like", value, "ysnd");
            return (Criteria) this;
        }

        public Criteria andYsndNotLike(String value) {
            addCriterion("ysnd not like", value, "ysnd");
            return (Criteria) this;
        }

        public Criteria andYsndIn(List<String> values) {
            addCriterion("ysnd in", values, "ysnd");
            return (Criteria) this;
        }

        public Criteria andYsndNotIn(List<String> values) {
            addCriterion("ysnd not in", values, "ysnd");
            return (Criteria) this;
        }

        public Criteria andYsndBetween(String value1, String value2) {
            addCriterion("ysnd between", value1, value2, "ysnd");
            return (Criteria) this;
        }

        public Criteria andYsndNotBetween(String value1, String value2) {
            addCriterion("ysnd not between", value1, value2, "ysnd");
            return (Criteria) this;
        }

        public Criteria andYsjeIsNull() {
            addCriterion("ysje is null");
            return (Criteria) this;
        }

        public Criteria andYsjeIsNotNull() {
            addCriterion("ysje is not null");
            return (Criteria) this;
        }

        public Criteria andYsjeEqualTo(String value) {
            addCriterion("ysje =", value, "ysje");
            return (Criteria) this;
        }

        public Criteria andYsjeNotEqualTo(String value) {
            addCriterion("ysje <>", value, "ysje");
            return (Criteria) this;
        }

        public Criteria andYsjeGreaterThan(String value) {
            addCriterion("ysje >", value, "ysje");
            return (Criteria) this;
        }

        public Criteria andYsjeGreaterThanOrEqualTo(String value) {
            addCriterion("ysje >=", value, "ysje");
            return (Criteria) this;
        }

        public Criteria andYsjeLessThan(String value) {
            addCriterion("ysje <", value, "ysje");
            return (Criteria) this;
        }

        public Criteria andYsjeLessThanOrEqualTo(String value) {
            addCriterion("ysje <=", value, "ysje");
            return (Criteria) this;
        }

        public Criteria andYsjeLike(String value) {
            addCriterion("ysje like", value, "ysje");
            return (Criteria) this;
        }

        public Criteria andYsjeNotLike(String value) {
            addCriterion("ysje not like", value, "ysje");
            return (Criteria) this;
        }

        public Criteria andYsjeIn(List<String> values) {
            addCriterion("ysje in", values, "ysje");
            return (Criteria) this;
        }

        public Criteria andYsjeNotIn(List<String> values) {
            addCriterion("ysje not in", values, "ysje");
            return (Criteria) this;
        }

        public Criteria andYsjeBetween(String value1, String value2) {
            addCriterion("ysje between", value1, value2, "ysje");
            return (Criteria) this;
        }

        public Criteria andYsjeNotBetween(String value1, String value2) {
            addCriterion("ysje not between", value1, value2, "ysje");
            return (Criteria) this;
        }

        public Criteria andYszbxjeIsNull() {
            addCriterion("yszbxje is null");
            return (Criteria) this;
        }

        public Criteria andYszbxjeIsNotNull() {
            addCriterion("yszbxje is not null");
            return (Criteria) this;
        }

        public Criteria andYszbxjeEqualTo(String value) {
            addCriterion("yszbxje =", value, "yszbxje");
            return (Criteria) this;
        }

        public Criteria andYszbxjeNotEqualTo(String value) {
            addCriterion("yszbxje <>", value, "yszbxje");
            return (Criteria) this;
        }

        public Criteria andYszbxjeGreaterThan(String value) {
            addCriterion("yszbxje >", value, "yszbxje");
            return (Criteria) this;
        }

        public Criteria andYszbxjeGreaterThanOrEqualTo(String value) {
            addCriterion("yszbxje >=", value, "yszbxje");
            return (Criteria) this;
        }

        public Criteria andYszbxjeLessThan(String value) {
            addCriterion("yszbxje <", value, "yszbxje");
            return (Criteria) this;
        }

        public Criteria andYszbxjeLessThanOrEqualTo(String value) {
            addCriterion("yszbxje <=", value, "yszbxje");
            return (Criteria) this;
        }

        public Criteria andYszbxjeLike(String value) {
            addCriterion("yszbxje like", value, "yszbxje");
            return (Criteria) this;
        }

        public Criteria andYszbxjeNotLike(String value) {
            addCriterion("yszbxje not like", value, "yszbxje");
            return (Criteria) this;
        }

        public Criteria andYszbxjeIn(List<String> values) {
            addCriterion("yszbxje in", values, "yszbxje");
            return (Criteria) this;
        }

        public Criteria andYszbxjeNotIn(List<String> values) {
            addCriterion("yszbxje not in", values, "yszbxje");
            return (Criteria) this;
        }

        public Criteria andYszbxjeBetween(String value1, String value2) {
            addCriterion("yszbxje between", value1, value2, "yszbxje");
            return (Criteria) this;
        }

        public Criteria andYszbxjeNotBetween(String value1, String value2) {
            addCriterion("yszbxje not between", value1, value2, "yszbxje");
            return (Criteria) this;
        }

        public Criteria andYsfyxjeIsNull() {
            addCriterion("ysfyxje is null");
            return (Criteria) this;
        }

        public Criteria andYsfyxjeIsNotNull() {
            addCriterion("ysfyxje is not null");
            return (Criteria) this;
        }

        public Criteria andYsfyxjeEqualTo(String value) {
            addCriterion("ysfyxje =", value, "ysfyxje");
            return (Criteria) this;
        }

        public Criteria andYsfyxjeNotEqualTo(String value) {
            addCriterion("ysfyxje <>", value, "ysfyxje");
            return (Criteria) this;
        }

        public Criteria andYsfyxjeGreaterThan(String value) {
            addCriterion("ysfyxje >", value, "ysfyxje");
            return (Criteria) this;
        }

        public Criteria andYsfyxjeGreaterThanOrEqualTo(String value) {
            addCriterion("ysfyxje >=", value, "ysfyxje");
            return (Criteria) this;
        }

        public Criteria andYsfyxjeLessThan(String value) {
            addCriterion("ysfyxje <", value, "ysfyxje");
            return (Criteria) this;
        }

        public Criteria andYsfyxjeLessThanOrEqualTo(String value) {
            addCriterion("ysfyxje <=", value, "ysfyxje");
            return (Criteria) this;
        }

        public Criteria andYsfyxjeLike(String value) {
            addCriterion("ysfyxje like", value, "ysfyxje");
            return (Criteria) this;
        }

        public Criteria andYsfyxjeNotLike(String value) {
            addCriterion("ysfyxje not like", value, "ysfyxje");
            return (Criteria) this;
        }

        public Criteria andYsfyxjeIn(List<String> values) {
            addCriterion("ysfyxje in", values, "ysfyxje");
            return (Criteria) this;
        }

        public Criteria andYsfyxjeNotIn(List<String> values) {
            addCriterion("ysfyxje not in", values, "ysfyxje");
            return (Criteria) this;
        }

        public Criteria andYsfyxjeBetween(String value1, String value2) {
            addCriterion("ysfyxje between", value1, value2, "ysfyxje");
            return (Criteria) this;
        }

        public Criteria andYsfyxjeNotBetween(String value1, String value2) {
            addCriterion("ysfyxje not between", value1, value2, "ysfyxje");
            return (Criteria) this;
        }

        public Criteria andFzdwIsNull() {
            addCriterion("fzdw is null");
            return (Criteria) this;
        }

        public Criteria andFzdwIsNotNull() {
            addCriterion("fzdw is not null");
            return (Criteria) this;
        }

        public Criteria andFzdwEqualTo(String value) {
            addCriterion("fzdw =", value, "fzdw");
            return (Criteria) this;
        }

        public Criteria andFzdwNotEqualTo(String value) {
            addCriterion("fzdw <>", value, "fzdw");
            return (Criteria) this;
        }

        public Criteria andFzdwGreaterThan(String value) {
            addCriterion("fzdw >", value, "fzdw");
            return (Criteria) this;
        }

        public Criteria andFzdwGreaterThanOrEqualTo(String value) {
            addCriterion("fzdw >=", value, "fzdw");
            return (Criteria) this;
        }

        public Criteria andFzdwLessThan(String value) {
            addCriterion("fzdw <", value, "fzdw");
            return (Criteria) this;
        }

        public Criteria andFzdwLessThanOrEqualTo(String value) {
            addCriterion("fzdw <=", value, "fzdw");
            return (Criteria) this;
        }

        public Criteria andFzdwLike(String value) {
            addCriterion("fzdw like", value, "fzdw");
            return (Criteria) this;
        }

        public Criteria andFzdwNotLike(String value) {
            addCriterion("fzdw not like", value, "fzdw");
            return (Criteria) this;
        }

        public Criteria andFzdwIn(List<String> values) {
            addCriterion("fzdw in", values, "fzdw");
            return (Criteria) this;
        }

        public Criteria andFzdwNotIn(List<String> values) {
            addCriterion("fzdw not in", values, "fzdw");
            return (Criteria) this;
        }

        public Criteria andFzdwBetween(String value1, String value2) {
            addCriterion("fzdw between", value1, value2, "fzdw");
            return (Criteria) this;
        }

        public Criteria andFzdwNotBetween(String value1, String value2) {
            addCriterion("fzdw not between", value1, value2, "fzdw");
            return (Criteria) this;
        }

        public Criteria andFzdwbmIsNull() {
            addCriterion("fzdwbm is null");
            return (Criteria) this;
        }

        public Criteria andFzdwbmIsNotNull() {
            addCriterion("fzdwbm is not null");
            return (Criteria) this;
        }

        public Criteria andFzdwbmEqualTo(String value) {
            addCriterion("fzdwbm =", value, "fzdwbm");
            return (Criteria) this;
        }

        public Criteria andFzdwbmNotEqualTo(String value) {
            addCriterion("fzdwbm <>", value, "fzdwbm");
            return (Criteria) this;
        }

        public Criteria andFzdwbmGreaterThan(String value) {
            addCriterion("fzdwbm >", value, "fzdwbm");
            return (Criteria) this;
        }

        public Criteria andFzdwbmGreaterThanOrEqualTo(String value) {
            addCriterion("fzdwbm >=", value, "fzdwbm");
            return (Criteria) this;
        }

        public Criteria andFzdwbmLessThan(String value) {
            addCriterion("fzdwbm <", value, "fzdwbm");
            return (Criteria) this;
        }

        public Criteria andFzdwbmLessThanOrEqualTo(String value) {
            addCriterion("fzdwbm <=", value, "fzdwbm");
            return (Criteria) this;
        }

        public Criteria andFzdwbmLike(String value) {
            addCriterion("fzdwbm like", value, "fzdwbm");
            return (Criteria) this;
        }

        public Criteria andFzdwbmNotLike(String value) {
            addCriterion("fzdwbm not like", value, "fzdwbm");
            return (Criteria) this;
        }

        public Criteria andFzdwbmIn(List<String> values) {
            addCriterion("fzdwbm in", values, "fzdwbm");
            return (Criteria) this;
        }

        public Criteria andFzdwbmNotIn(List<String> values) {
            addCriterion("fzdwbm not in", values, "fzdwbm");
            return (Criteria) this;
        }

        public Criteria andFzdwbmBetween(String value1, String value2) {
            addCriterion("fzdwbm between", value1, value2, "fzdwbm");
            return (Criteria) this;
        }

        public Criteria andFzdwbmNotBetween(String value1, String value2) {
            addCriterion("fzdwbm not between", value1, value2, "fzdwbm");
            return (Criteria) this;
        }

        public Criteria andInvoiceNeedIsNull() {
            addCriterion("invoice_need is null");
            return (Criteria) this;
        }

        public Criteria andInvoiceNeedIsNotNull() {
            addCriterion("invoice_need is not null");
            return (Criteria) this;
        }

        public Criteria andInvoiceNeedEqualTo(Integer value) {
            addCriterion("invoice_need =", value, "invoiceNeed");
            return (Criteria) this;
        }

        public Criteria andInvoiceNeedNotEqualTo(Integer value) {
            addCriterion("invoice_need <>", value, "invoiceNeed");
            return (Criteria) this;
        }

        public Criteria andInvoiceNeedGreaterThan(Integer value) {
            addCriterion("invoice_need >", value, "invoiceNeed");
            return (Criteria) this;
        }

        public Criteria andInvoiceNeedGreaterThanOrEqualTo(Integer value) {
            addCriterion("invoice_need >=", value, "invoiceNeed");
            return (Criteria) this;
        }

        public Criteria andInvoiceNeedLessThan(Integer value) {
            addCriterion("invoice_need <", value, "invoiceNeed");
            return (Criteria) this;
        }

        public Criteria andInvoiceNeedLessThanOrEqualTo(Integer value) {
            addCriterion("invoice_need <=", value, "invoiceNeed");
            return (Criteria) this;
        }

        public Criteria andInvoiceNeedIn(List<Integer> values) {
            addCriterion("invoice_need in", values, "invoiceNeed");
            return (Criteria) this;
        }

        public Criteria andInvoiceNeedNotIn(List<Integer> values) {
            addCriterion("invoice_need not in", values, "invoiceNeed");
            return (Criteria) this;
        }

        public Criteria andInvoiceNeedBetween(Integer value1, Integer value2) {
            addCriterion("invoice_need between", value1, value2, "invoiceNeed");
            return (Criteria) this;
        }

        public Criteria andInvoiceNeedNotBetween(Integer value1, Integer value2) {
            addCriterion("invoice_need not between", value1, value2, "invoiceNeed");
            return (Criteria) this;
        }

        public Criteria andInvoiceCompanyNameIsNull() {
            addCriterion("invoice_company_name is null");
            return (Criteria) this;
        }

        public Criteria andInvoiceCompanyNameIsNotNull() {
            addCriterion("invoice_company_name is not null");
            return (Criteria) this;
        }

        public Criteria andInvoiceCompanyNameEqualTo(String value) {
            addCriterion("invoice_company_name =", value, "invoiceCompanyName");
            return (Criteria) this;
        }

        public Criteria andInvoiceCompanyNameNotEqualTo(String value) {
            addCriterion("invoice_company_name <>", value, "invoiceCompanyName");
            return (Criteria) this;
        }

        public Criteria andInvoiceCompanyNameGreaterThan(String value) {
            addCriterion("invoice_company_name >", value, "invoiceCompanyName");
            return (Criteria) this;
        }

        public Criteria andInvoiceCompanyNameGreaterThanOrEqualTo(String value) {
            addCriterion("invoice_company_name >=", value, "invoiceCompanyName");
            return (Criteria) this;
        }

        public Criteria andInvoiceCompanyNameLessThan(String value) {
            addCriterion("invoice_company_name <", value, "invoiceCompanyName");
            return (Criteria) this;
        }

        public Criteria andInvoiceCompanyNameLessThanOrEqualTo(String value) {
            addCriterion("invoice_company_name <=", value, "invoiceCompanyName");
            return (Criteria) this;
        }

        public Criteria andInvoiceCompanyNameLike(String value) {
            addCriterion("invoice_company_name like", value, "invoiceCompanyName");
            return (Criteria) this;
        }

        public Criteria andInvoiceCompanyNameNotLike(String value) {
            addCriterion("invoice_company_name not like", value, "invoiceCompanyName");
            return (Criteria) this;
        }

        public Criteria andInvoiceCompanyNameIn(List<String> values) {
            addCriterion("invoice_company_name in", values, "invoiceCompanyName");
            return (Criteria) this;
        }

        public Criteria andInvoiceCompanyNameNotIn(List<String> values) {
            addCriterion("invoice_company_name not in", values, "invoiceCompanyName");
            return (Criteria) this;
        }

        public Criteria andInvoiceCompanyNameBetween(String value1, String value2) {
            addCriterion("invoice_company_name between", value1, value2, "invoiceCompanyName");
            return (Criteria) this;
        }

        public Criteria andInvoiceCompanyNameNotBetween(String value1, String value2) {
            addCriterion("invoice_company_name not between", value1, value2, "invoiceCompanyName");
            return (Criteria) this;
        }

        public Criteria andInvoiceCompanyCodeIsNull() {
            addCriterion("invoice_company_code is null");
            return (Criteria) this;
        }

        public Criteria andInvoiceCompanyCodeIsNotNull() {
            addCriterion("invoice_company_code is not null");
            return (Criteria) this;
        }

        public Criteria andInvoiceCompanyCodeEqualTo(String value) {
            addCriterion("invoice_company_code =", value, "invoiceCompanyCode");
            return (Criteria) this;
        }

        public Criteria andInvoiceCompanyCodeNotEqualTo(String value) {
            addCriterion("invoice_company_code <>", value, "invoiceCompanyCode");
            return (Criteria) this;
        }

        public Criteria andInvoiceCompanyCodeGreaterThan(String value) {
            addCriterion("invoice_company_code >", value, "invoiceCompanyCode");
            return (Criteria) this;
        }

        public Criteria andInvoiceCompanyCodeGreaterThanOrEqualTo(String value) {
            addCriterion("invoice_company_code >=", value, "invoiceCompanyCode");
            return (Criteria) this;
        }

        public Criteria andInvoiceCompanyCodeLessThan(String value) {
            addCriterion("invoice_company_code <", value, "invoiceCompanyCode");
            return (Criteria) this;
        }

        public Criteria andInvoiceCompanyCodeLessThanOrEqualTo(String value) {
            addCriterion("invoice_company_code <=", value, "invoiceCompanyCode");
            return (Criteria) this;
        }

        public Criteria andInvoiceCompanyCodeLike(String value) {
            addCriterion("invoice_company_code like", value, "invoiceCompanyCode");
            return (Criteria) this;
        }

        public Criteria andInvoiceCompanyCodeNotLike(String value) {
            addCriterion("invoice_company_code not like", value, "invoiceCompanyCode");
            return (Criteria) this;
        }

        public Criteria andInvoiceCompanyCodeIn(List<String> values) {
            addCriterion("invoice_company_code in", values, "invoiceCompanyCode");
            return (Criteria) this;
        }

        public Criteria andInvoiceCompanyCodeNotIn(List<String> values) {
            addCriterion("invoice_company_code not in", values, "invoiceCompanyCode");
            return (Criteria) this;
        }

        public Criteria andInvoiceCompanyCodeBetween(String value1, String value2) {
            addCriterion("invoice_company_code between", value1, value2, "invoiceCompanyCode");
            return (Criteria) this;
        }

        public Criteria andInvoiceCompanyCodeNotBetween(String value1, String value2) {
            addCriterion("invoice_company_code not between", value1, value2, "invoiceCompanyCode");
            return (Criteria) this;
        }

        public Criteria andInvoiceBankNameIsNull() {
            addCriterion("invoice_bank_name is null");
            return (Criteria) this;
        }

        public Criteria andInvoiceBankNameIsNotNull() {
            addCriterion("invoice_bank_name is not null");
            return (Criteria) this;
        }

        public Criteria andInvoiceBankNameEqualTo(String value) {
            addCriterion("invoice_bank_name =", value, "invoiceBankName");
            return (Criteria) this;
        }

        public Criteria andInvoiceBankNameNotEqualTo(String value) {
            addCriterion("invoice_bank_name <>", value, "invoiceBankName");
            return (Criteria) this;
        }

        public Criteria andInvoiceBankNameGreaterThan(String value) {
            addCriterion("invoice_bank_name >", value, "invoiceBankName");
            return (Criteria) this;
        }

        public Criteria andInvoiceBankNameGreaterThanOrEqualTo(String value) {
            addCriterion("invoice_bank_name >=", value, "invoiceBankName");
            return (Criteria) this;
        }

        public Criteria andInvoiceBankNameLessThan(String value) {
            addCriterion("invoice_bank_name <", value, "invoiceBankName");
            return (Criteria) this;
        }

        public Criteria andInvoiceBankNameLessThanOrEqualTo(String value) {
            addCriterion("invoice_bank_name <=", value, "invoiceBankName");
            return (Criteria) this;
        }

        public Criteria andInvoiceBankNameLike(String value) {
            addCriterion("invoice_bank_name like", value, "invoiceBankName");
            return (Criteria) this;
        }

        public Criteria andInvoiceBankNameNotLike(String value) {
            addCriterion("invoice_bank_name not like", value, "invoiceBankName");
            return (Criteria) this;
        }

        public Criteria andInvoiceBankNameIn(List<String> values) {
            addCriterion("invoice_bank_name in", values, "invoiceBankName");
            return (Criteria) this;
        }

        public Criteria andInvoiceBankNameNotIn(List<String> values) {
            addCriterion("invoice_bank_name not in", values, "invoiceBankName");
            return (Criteria) this;
        }

        public Criteria andInvoiceBankNameBetween(String value1, String value2) {
            addCriterion("invoice_bank_name between", value1, value2, "invoiceBankName");
            return (Criteria) this;
        }

        public Criteria andInvoiceBankNameNotBetween(String value1, String value2) {
            addCriterion("invoice_bank_name not between", value1, value2, "invoiceBankName");
            return (Criteria) this;
        }

        public Criteria andInvoiceBankNoIsNull() {
            addCriterion("invoice_bank_no is null");
            return (Criteria) this;
        }

        public Criteria andInvoiceBankNoIsNotNull() {
            addCriterion("invoice_bank_no is not null");
            return (Criteria) this;
        }

        public Criteria andInvoiceBankNoEqualTo(String value) {
            addCriterion("invoice_bank_no =", value, "invoiceBankNo");
            return (Criteria) this;
        }

        public Criteria andInvoiceBankNoNotEqualTo(String value) {
            addCriterion("invoice_bank_no <>", value, "invoiceBankNo");
            return (Criteria) this;
        }

        public Criteria andInvoiceBankNoGreaterThan(String value) {
            addCriterion("invoice_bank_no >", value, "invoiceBankNo");
            return (Criteria) this;
        }

        public Criteria andInvoiceBankNoGreaterThanOrEqualTo(String value) {
            addCriterion("invoice_bank_no >=", value, "invoiceBankNo");
            return (Criteria) this;
        }

        public Criteria andInvoiceBankNoLessThan(String value) {
            addCriterion("invoice_bank_no <", value, "invoiceBankNo");
            return (Criteria) this;
        }

        public Criteria andInvoiceBankNoLessThanOrEqualTo(String value) {
            addCriterion("invoice_bank_no <=", value, "invoiceBankNo");
            return (Criteria) this;
        }

        public Criteria andInvoiceBankNoLike(String value) {
            addCriterion("invoice_bank_no like", value, "invoiceBankNo");
            return (Criteria) this;
        }

        public Criteria andInvoiceBankNoNotLike(String value) {
            addCriterion("invoice_bank_no not like", value, "invoiceBankNo");
            return (Criteria) this;
        }

        public Criteria andInvoiceBankNoIn(List<String> values) {
            addCriterion("invoice_bank_no in", values, "invoiceBankNo");
            return (Criteria) this;
        }

        public Criteria andInvoiceBankNoNotIn(List<String> values) {
            addCriterion("invoice_bank_no not in", values, "invoiceBankNo");
            return (Criteria) this;
        }

        public Criteria andInvoiceBankNoBetween(String value1, String value2) {
            addCriterion("invoice_bank_no between", value1, value2, "invoiceBankNo");
            return (Criteria) this;
        }

        public Criteria andInvoiceBankNoNotBetween(String value1, String value2) {
            addCriterion("invoice_bank_no not between", value1, value2, "invoiceBankNo");
            return (Criteria) this;
        }

        public Criteria andInvoiceContactNameIsNull() {
            addCriterion("invoice_contact_name is null");
            return (Criteria) this;
        }

        public Criteria andInvoiceContactNameIsNotNull() {
            addCriterion("invoice_contact_name is not null");
            return (Criteria) this;
        }

        public Criteria andInvoiceContactNameEqualTo(String value) {
            addCriterion("invoice_contact_name =", value, "invoiceContactName");
            return (Criteria) this;
        }

        public Criteria andInvoiceContactNameNotEqualTo(String value) {
            addCriterion("invoice_contact_name <>", value, "invoiceContactName");
            return (Criteria) this;
        }

        public Criteria andInvoiceContactNameGreaterThan(String value) {
            addCriterion("invoice_contact_name >", value, "invoiceContactName");
            return (Criteria) this;
        }

        public Criteria andInvoiceContactNameGreaterThanOrEqualTo(String value) {
            addCriterion("invoice_contact_name >=", value, "invoiceContactName");
            return (Criteria) this;
        }

        public Criteria andInvoiceContactNameLessThan(String value) {
            addCriterion("invoice_contact_name <", value, "invoiceContactName");
            return (Criteria) this;
        }

        public Criteria andInvoiceContactNameLessThanOrEqualTo(String value) {
            addCriterion("invoice_contact_name <=", value, "invoiceContactName");
            return (Criteria) this;
        }

        public Criteria andInvoiceContactNameLike(String value) {
            addCriterion("invoice_contact_name like", value, "invoiceContactName");
            return (Criteria) this;
        }

        public Criteria andInvoiceContactNameNotLike(String value) {
            addCriterion("invoice_contact_name not like", value, "invoiceContactName");
            return (Criteria) this;
        }

        public Criteria andInvoiceContactNameIn(List<String> values) {
            addCriterion("invoice_contact_name in", values, "invoiceContactName");
            return (Criteria) this;
        }

        public Criteria andInvoiceContactNameNotIn(List<String> values) {
            addCriterion("invoice_contact_name not in", values, "invoiceContactName");
            return (Criteria) this;
        }

        public Criteria andInvoiceContactNameBetween(String value1, String value2) {
            addCriterion("invoice_contact_name between", value1, value2, "invoiceContactName");
            return (Criteria) this;
        }

        public Criteria andInvoiceContactNameNotBetween(String value1, String value2) {
            addCriterion("invoice_contact_name not between", value1, value2, "invoiceContactName");
            return (Criteria) this;
        }

        public Criteria andInvoiceContactPhoneIsNull() {
            addCriterion("invoice_contact_phone is null");
            return (Criteria) this;
        }

        public Criteria andInvoiceContactPhoneIsNotNull() {
            addCriterion("invoice_contact_phone is not null");
            return (Criteria) this;
        }

        public Criteria andInvoiceContactPhoneEqualTo(String value) {
            addCriterion("invoice_contact_phone =", value, "invoiceContactPhone");
            return (Criteria) this;
        }

        public Criteria andInvoiceContactPhoneNotEqualTo(String value) {
            addCriterion("invoice_contact_phone <>", value, "invoiceContactPhone");
            return (Criteria) this;
        }

        public Criteria andInvoiceContactPhoneGreaterThan(String value) {
            addCriterion("invoice_contact_phone >", value, "invoiceContactPhone");
            return (Criteria) this;
        }

        public Criteria andInvoiceContactPhoneGreaterThanOrEqualTo(String value) {
            addCriterion("invoice_contact_phone >=", value, "invoiceContactPhone");
            return (Criteria) this;
        }

        public Criteria andInvoiceContactPhoneLessThan(String value) {
            addCriterion("invoice_contact_phone <", value, "invoiceContactPhone");
            return (Criteria) this;
        }

        public Criteria andInvoiceContactPhoneLessThanOrEqualTo(String value) {
            addCriterion("invoice_contact_phone <=", value, "invoiceContactPhone");
            return (Criteria) this;
        }

        public Criteria andInvoiceContactPhoneLike(String value) {
            addCriterion("invoice_contact_phone like", value, "invoiceContactPhone");
            return (Criteria) this;
        }

        public Criteria andInvoiceContactPhoneNotLike(String value) {
            addCriterion("invoice_contact_phone not like", value, "invoiceContactPhone");
            return (Criteria) this;
        }

        public Criteria andInvoiceContactPhoneIn(List<String> values) {
            addCriterion("invoice_contact_phone in", values, "invoiceContactPhone");
            return (Criteria) this;
        }

        public Criteria andInvoiceContactPhoneNotIn(List<String> values) {
            addCriterion("invoice_contact_phone not in", values, "invoiceContactPhone");
            return (Criteria) this;
        }

        public Criteria andInvoiceContactPhoneBetween(String value1, String value2) {
            addCriterion("invoice_contact_phone between", value1, value2, "invoiceContactPhone");
            return (Criteria) this;
        }

        public Criteria andInvoiceContactPhoneNotBetween(String value1, String value2) {
            addCriterion("invoice_contact_phone not between", value1, value2, "invoiceContactPhone");
            return (Criteria) this;
        }

        public Criteria andInvoiceContactEmailIsNull() {
            addCriterion("invoice_contact_email is null");
            return (Criteria) this;
        }

        public Criteria andInvoiceContactEmailIsNotNull() {
            addCriterion("invoice_contact_email is not null");
            return (Criteria) this;
        }

        public Criteria andInvoiceContactEmailEqualTo(String value) {
            addCriterion("invoice_contact_email =", value, "invoiceContactEmail");
            return (Criteria) this;
        }

        public Criteria andInvoiceContactEmailNotEqualTo(String value) {
            addCriterion("invoice_contact_email <>", value, "invoiceContactEmail");
            return (Criteria) this;
        }

        public Criteria andInvoiceContactEmailGreaterThan(String value) {
            addCriterion("invoice_contact_email >", value, "invoiceContactEmail");
            return (Criteria) this;
        }

        public Criteria andInvoiceContactEmailGreaterThanOrEqualTo(String value) {
            addCriterion("invoice_contact_email >=", value, "invoiceContactEmail");
            return (Criteria) this;
        }

        public Criteria andInvoiceContactEmailLessThan(String value) {
            addCriterion("invoice_contact_email <", value, "invoiceContactEmail");
            return (Criteria) this;
        }

        public Criteria andInvoiceContactEmailLessThanOrEqualTo(String value) {
            addCriterion("invoice_contact_email <=", value, "invoiceContactEmail");
            return (Criteria) this;
        }

        public Criteria andInvoiceContactEmailLike(String value) {
            addCriterion("invoice_contact_email like", value, "invoiceContactEmail");
            return (Criteria) this;
        }

        public Criteria andInvoiceContactEmailNotLike(String value) {
            addCriterion("invoice_contact_email not like", value, "invoiceContactEmail");
            return (Criteria) this;
        }

        public Criteria andInvoiceContactEmailIn(List<String> values) {
            addCriterion("invoice_contact_email in", values, "invoiceContactEmail");
            return (Criteria) this;
        }

        public Criteria andInvoiceContactEmailNotIn(List<String> values) {
            addCriterion("invoice_contact_email not in", values, "invoiceContactEmail");
            return (Criteria) this;
        }

        public Criteria andInvoiceContactEmailBetween(String value1, String value2) {
            addCriterion("invoice_contact_email between", value1, value2, "invoiceContactEmail");
            return (Criteria) this;
        }

        public Criteria andInvoiceContactEmailNotBetween(String value1, String value2) {
            addCriterion("invoice_contact_email not between", value1, value2, "invoiceContactEmail");
            return (Criteria) this;
        }

        public Criteria andPayStatusIsNull() {
            addCriterion("pay_status is null");
            return (Criteria) this;
        }

        public Criteria andPayStatusIsNotNull() {
            addCriterion("pay_status is not null");
            return (Criteria) this;
        }

        public Criteria andPayStatusEqualTo(Integer value) {
            addCriterion("pay_status =", value, "payStatus");
            return (Criteria) this;
        }

        public Criteria andPayStatusNotEqualTo(Integer value) {
            addCriterion("pay_status <>", value, "payStatus");
            return (Criteria) this;
        }

        public Criteria andPayStatusGreaterThan(Integer value) {
            addCriterion("pay_status >", value, "payStatus");
            return (Criteria) this;
        }

        public Criteria andPayStatusGreaterThanOrEqualTo(Integer value) {
            addCriterion("pay_status >=", value, "payStatus");
            return (Criteria) this;
        }

        public Criteria andPayStatusLessThan(Integer value) {
            addCriterion("pay_status <", value, "payStatus");
            return (Criteria) this;
        }

        public Criteria andPayStatusLessThanOrEqualTo(Integer value) {
            addCriterion("pay_status <=", value, "payStatus");
            return (Criteria) this;
        }

        public Criteria andPayStatusIn(List<Integer> values) {
            addCriterion("pay_status in", values, "payStatus");
            return (Criteria) this;
        }

        public Criteria andPayStatusNotIn(List<Integer> values) {
            addCriterion("pay_status not in", values, "payStatus");
            return (Criteria) this;
        }

        public Criteria andPayStatusBetween(Integer value1, Integer value2) {
            addCriterion("pay_status between", value1, value2, "payStatus");
            return (Criteria) this;
        }

        public Criteria andPayStatusNotBetween(Integer value1, Integer value2) {
            addCriterion("pay_status not between", value1, value2, "payStatus");
            return (Criteria) this;
        }

        public Criteria andPayFeeIsNull() {
            addCriterion("pay_fee is null");
            return (Criteria) this;
        }

        public Criteria andPayFeeIsNotNull() {
            addCriterion("pay_fee is not null");
            return (Criteria) this;
        }

        public Criteria andPayFeeEqualTo(Double value) {
            addCriterion("pay_fee =", value, "payFee");
            return (Criteria) this;
        }

        public Criteria andPayFeeNotEqualTo(Double value) {
            addCriterion("pay_fee <>", value, "payFee");
            return (Criteria) this;
        }

        public Criteria andPayFeeGreaterThan(Double value) {
            addCriterion("pay_fee >", value, "payFee");
            return (Criteria) this;
        }

        public Criteria andPayFeeGreaterThanOrEqualTo(Double value) {
            addCriterion("pay_fee >=", value, "payFee");
            return (Criteria) this;
        }

        public Criteria andPayFeeLessThan(Double value) {
            addCriterion("pay_fee <", value, "payFee");
            return (Criteria) this;
        }

        public Criteria andPayFeeLessThanOrEqualTo(Double value) {
            addCriterion("pay_fee <=", value, "payFee");
            return (Criteria) this;
        }

        public Criteria andPayFeeIn(List<Double> values) {
            addCriterion("pay_fee in", values, "payFee");
            return (Criteria) this;
        }

        public Criteria andPayFeeNotIn(List<Double> values) {
            addCriterion("pay_fee not in", values, "payFee");
            return (Criteria) this;
        }

        public Criteria andPayFeeBetween(Double value1, Double value2) {
            addCriterion("pay_fee between", value1, value2, "payFee");
            return (Criteria) this;
        }

        public Criteria andPayFeeNotBetween(Double value1, Double value2) {
            addCriterion("pay_fee not between", value1, value2, "payFee");
            return (Criteria) this;
        }

        public Criteria andPayNoIsNull() {
            addCriterion("pay_no is null");
            return (Criteria) this;
        }

        public Criteria andPayNoIsNotNull() {
            addCriterion("pay_no is not null");
            return (Criteria) this;
        }

        public Criteria andPayNoEqualTo(String value) {
            addCriterion("pay_no =", value, "payNo");
            return (Criteria) this;
        }

        public Criteria andPayNoNotEqualTo(String value) {
            addCriterion("pay_no <>", value, "payNo");
            return (Criteria) this;
        }

        public Criteria andPayNoGreaterThan(String value) {
            addCriterion("pay_no >", value, "payNo");
            return (Criteria) this;
        }

        public Criteria andPayNoGreaterThanOrEqualTo(String value) {
            addCriterion("pay_no >=", value, "payNo");
            return (Criteria) this;
        }

        public Criteria andPayNoLessThan(String value) {
            addCriterion("pay_no <", value, "payNo");
            return (Criteria) this;
        }

        public Criteria andPayNoLessThanOrEqualTo(String value) {
            addCriterion("pay_no <=", value, "payNo");
            return (Criteria) this;
        }

        public Criteria andPayNoLike(String value) {
            addCriterion("pay_no like", value, "payNo");
            return (Criteria) this;
        }

        public Criteria andPayNoNotLike(String value) {
            addCriterion("pay_no not like", value, "payNo");
            return (Criteria) this;
        }

        public Criteria andPayNoIn(List<String> values) {
            addCriterion("pay_no in", values, "payNo");
            return (Criteria) this;
        }

        public Criteria andPayNoNotIn(List<String> values) {
            addCriterion("pay_no not in", values, "payNo");
            return (Criteria) this;
        }

        public Criteria andPayNoBetween(String value1, String value2) {
            addCriterion("pay_no between", value1, value2, "payNo");
            return (Criteria) this;
        }

        public Criteria andPayNoNotBetween(String value1, String value2) {
            addCriterion("pay_no not between", value1, value2, "payNo");
            return (Criteria) this;
        }

        public Criteria andPaymentStatusIsNull() {
            addCriterion("payment_status is null");
            return (Criteria) this;
        }

        public Criteria andPaymentStatusIsNotNull() {
            addCriterion("payment_status is not null");
            return (Criteria) this;
        }

        public Criteria andPaymentStatusEqualTo(Integer value) {
            addCriterion("payment_status =", value, "paymentStatus");
            return (Criteria) this;
        }

        public Criteria andPaymentStatusNotEqualTo(Integer value) {
            addCriterion("payment_status <>", value, "paymentStatus");
            return (Criteria) this;
        }

        public Criteria andPaymentStatusGreaterThan(Integer value) {
            addCriterion("payment_status >", value, "paymentStatus");
            return (Criteria) this;
        }

        public Criteria andPaymentStatusGreaterThanOrEqualTo(Integer value) {
            addCriterion("payment_status >=", value, "paymentStatus");
            return (Criteria) this;
        }

        public Criteria andPaymentStatusLessThan(Integer value) {
            addCriterion("payment_status <", value, "paymentStatus");
            return (Criteria) this;
        }

        public Criteria andPaymentStatusLessThanOrEqualTo(Integer value) {
            addCriterion("payment_status <=", value, "paymentStatus");
            return (Criteria) this;
        }

        public Criteria andPaymentStatusIn(List<Integer> values) {
            addCriterion("payment_status in", values, "paymentStatus");
            return (Criteria) this;
        }

        public Criteria andPaymentStatusNotIn(List<Integer> values) {
            addCriterion("payment_status not in", values, "paymentStatus");
            return (Criteria) this;
        }

        public Criteria andPaymentStatusBetween(Integer value1, Integer value2) {
            addCriterion("payment_status between", value1, value2, "paymentStatus");
            return (Criteria) this;
        }

        public Criteria andPaymentStatusNotBetween(Integer value1, Integer value2) {
            addCriterion("payment_status not between", value1, value2, "paymentStatus");
            return (Criteria) this;
        }

        public Criteria andPaymentDateIsNull() {
            addCriterion("payment_date is null");
            return (Criteria) this;
        }

        public Criteria andPaymentDateIsNotNull() {
            addCriterion("payment_date is not null");
            return (Criteria) this;
        }

        public Criteria andPaymentDateEqualTo(String value) {
            addCriterion("payment_date =", value, "paymentDate");
            return (Criteria) this;
        }

        public Criteria andPaymentDateNotEqualTo(String value) {
            addCriterion("payment_date <>", value, "paymentDate");
            return (Criteria) this;
        }

        public Criteria andPaymentDateGreaterThan(String value) {
            addCriterion("payment_date >", value, "paymentDate");
            return (Criteria) this;
        }

        public Criteria andPaymentDateGreaterThanOrEqualTo(String value) {
            addCriterion("payment_date >=", value, "paymentDate");
            return (Criteria) this;
        }

        public Criteria andPaymentDateLessThan(String value) {
            addCriterion("payment_date <", value, "paymentDate");
            return (Criteria) this;
        }

        public Criteria andPaymentDateLessThanOrEqualTo(String value) {
            addCriterion("payment_date <=", value, "paymentDate");
            return (Criteria) this;
        }

        public Criteria andPaymentDateLike(String value) {
            addCriterion("payment_date like", value, "paymentDate");
            return (Criteria) this;
        }

        public Criteria andPaymentDateNotLike(String value) {
            addCriterion("payment_date not like", value, "paymentDate");
            return (Criteria) this;
        }

        public Criteria andPaymentDateIn(List<String> values) {
            addCriterion("payment_date in", values, "paymentDate");
            return (Criteria) this;
        }

        public Criteria andPaymentDateNotIn(List<String> values) {
            addCriterion("payment_date not in", values, "paymentDate");
            return (Criteria) this;
        }

        public Criteria andPaymentDateBetween(String value1, String value2) {
            addCriterion("payment_date between", value1, value2, "paymentDate");
            return (Criteria) this;
        }

        public Criteria andPaymentDateNotBetween(String value1, String value2) {
            addCriterion("payment_date not between", value1, value2, "paymentDate");
            return (Criteria) this;
        }

        public Criteria andPaymentNoIsNull() {
            addCriterion("payment_no is null");
            return (Criteria) this;
        }

        public Criteria andPaymentNoIsNotNull() {
            addCriterion("payment_no is not null");
            return (Criteria) this;
        }

        public Criteria andPaymentNoEqualTo(String value) {
            addCriterion("payment_no =", value, "paymentNo");
            return (Criteria) this;
        }

        public Criteria andPaymentNoNotEqualTo(String value) {
            addCriterion("payment_no <>", value, "paymentNo");
            return (Criteria) this;
        }

        public Criteria andPaymentNoGreaterThan(String value) {
            addCriterion("payment_no >", value, "paymentNo");
            return (Criteria) this;
        }

        public Criteria andPaymentNoGreaterThanOrEqualTo(String value) {
            addCriterion("payment_no >=", value, "paymentNo");
            return (Criteria) this;
        }

        public Criteria andPaymentNoLessThan(String value) {
            addCriterion("payment_no <", value, "paymentNo");
            return (Criteria) this;
        }

        public Criteria andPaymentNoLessThanOrEqualTo(String value) {
            addCriterion("payment_no <=", value, "paymentNo");
            return (Criteria) this;
        }

        public Criteria andPaymentNoLike(String value) {
            addCriterion("payment_no like", value, "paymentNo");
            return (Criteria) this;
        }

        public Criteria andPaymentNoNotLike(String value) {
            addCriterion("payment_no not like", value, "paymentNo");
            return (Criteria) this;
        }

        public Criteria andPaymentNoIn(List<String> values) {
            addCriterion("payment_no in", values, "paymentNo");
            return (Criteria) this;
        }

        public Criteria andPaymentNoNotIn(List<String> values) {
            addCriterion("payment_no not in", values, "paymentNo");
            return (Criteria) this;
        }

        public Criteria andPaymentNoBetween(String value1, String value2) {
            addCriterion("payment_no between", value1, value2, "paymentNo");
            return (Criteria) this;
        }

        public Criteria andPaymentNoNotBetween(String value1, String value2) {
            addCriterion("payment_no not between", value1, value2, "paymentNo");
            return (Criteria) this;
        }

        public Criteria andPaymentYsjeIsNull() {
            addCriterion("payment_ysje is null");
            return (Criteria) this;
        }

        public Criteria andPaymentYsjeIsNotNull() {
            addCriterion("payment_ysje is not null");
            return (Criteria) this;
        }

        public Criteria andPaymentYsjeEqualTo(String value) {
            addCriterion("payment_ysje =", value, "paymentYsje");
            return (Criteria) this;
        }

        public Criteria andPaymentYsjeNotEqualTo(String value) {
            addCriterion("payment_ysje <>", value, "paymentYsje");
            return (Criteria) this;
        }

        public Criteria andPaymentYsjeGreaterThan(String value) {
            addCriterion("payment_ysje >", value, "paymentYsje");
            return (Criteria) this;
        }

        public Criteria andPaymentYsjeGreaterThanOrEqualTo(String value) {
            addCriterion("payment_ysje >=", value, "paymentYsje");
            return (Criteria) this;
        }

        public Criteria andPaymentYsjeLessThan(String value) {
            addCriterion("payment_ysje <", value, "paymentYsje");
            return (Criteria) this;
        }

        public Criteria andPaymentYsjeLessThanOrEqualTo(String value) {
            addCriterion("payment_ysje <=", value, "paymentYsje");
            return (Criteria) this;
        }

        public Criteria andPaymentYsjeLike(String value) {
            addCriterion("payment_ysje like", value, "paymentYsje");
            return (Criteria) this;
        }

        public Criteria andPaymentYsjeNotLike(String value) {
            addCriterion("payment_ysje not like", value, "paymentYsje");
            return (Criteria) this;
        }

        public Criteria andPaymentYsjeIn(List<String> values) {
            addCriterion("payment_ysje in", values, "paymentYsje");
            return (Criteria) this;
        }

        public Criteria andPaymentYsjeNotIn(List<String> values) {
            addCriterion("payment_ysje not in", values, "paymentYsje");
            return (Criteria) this;
        }

        public Criteria andPaymentYsjeBetween(String value1, String value2) {
            addCriterion("payment_ysje between", value1, value2, "paymentYsje");
            return (Criteria) this;
        }

        public Criteria andPaymentYsjeNotBetween(String value1, String value2) {
            addCriterion("payment_ysje not between", value1, value2, "paymentYsje");
            return (Criteria) this;
        }

        public Criteria andPaymentYszbxjeIsNull() {
            addCriterion("payment_yszbxje is null");
            return (Criteria) this;
        }

        public Criteria andPaymentYszbxjeIsNotNull() {
            addCriterion("payment_yszbxje is not null");
            return (Criteria) this;
        }

        public Criteria andPaymentYszbxjeEqualTo(String value) {
            addCriterion("payment_yszbxje =", value, "paymentYszbxje");
            return (Criteria) this;
        }

        public Criteria andPaymentYszbxjeNotEqualTo(String value) {
            addCriterion("payment_yszbxje <>", value, "paymentYszbxje");
            return (Criteria) this;
        }

        public Criteria andPaymentYszbxjeGreaterThan(String value) {
            addCriterion("payment_yszbxje >", value, "paymentYszbxje");
            return (Criteria) this;
        }

        public Criteria andPaymentYszbxjeGreaterThanOrEqualTo(String value) {
            addCriterion("payment_yszbxje >=", value, "paymentYszbxje");
            return (Criteria) this;
        }

        public Criteria andPaymentYszbxjeLessThan(String value) {
            addCriterion("payment_yszbxje <", value, "paymentYszbxje");
            return (Criteria) this;
        }

        public Criteria andPaymentYszbxjeLessThanOrEqualTo(String value) {
            addCriterion("payment_yszbxje <=", value, "paymentYszbxje");
            return (Criteria) this;
        }

        public Criteria andPaymentYszbxjeLike(String value) {
            addCriterion("payment_yszbxje like", value, "paymentYszbxje");
            return (Criteria) this;
        }

        public Criteria andPaymentYszbxjeNotLike(String value) {
            addCriterion("payment_yszbxje not like", value, "paymentYszbxje");
            return (Criteria) this;
        }

        public Criteria andPaymentYszbxjeIn(List<String> values) {
            addCriterion("payment_yszbxje in", values, "paymentYszbxje");
            return (Criteria) this;
        }

        public Criteria andPaymentYszbxjeNotIn(List<String> values) {
            addCriterion("payment_yszbxje not in", values, "paymentYszbxje");
            return (Criteria) this;
        }

        public Criteria andPaymentYszbxjeBetween(String value1, String value2) {
            addCriterion("payment_yszbxje between", value1, value2, "paymentYszbxje");
            return (Criteria) this;
        }

        public Criteria andPaymentYszbxjeNotBetween(String value1, String value2) {
            addCriterion("payment_yszbxje not between", value1, value2, "paymentYszbxje");
            return (Criteria) this;
        }

        public Criteria andPaymentYsfyxjeIsNull() {
            addCriterion("payment_ysfyxje is null");
            return (Criteria) this;
        }

        public Criteria andPaymentYsfyxjeIsNotNull() {
            addCriterion("payment_ysfyxje is not null");
            return (Criteria) this;
        }

        public Criteria andPaymentYsfyxjeEqualTo(String value) {
            addCriterion("payment_ysfyxje =", value, "paymentYsfyxje");
            return (Criteria) this;
        }

        public Criteria andPaymentYsfyxjeNotEqualTo(String value) {
            addCriterion("payment_ysfyxje <>", value, "paymentYsfyxje");
            return (Criteria) this;
        }

        public Criteria andPaymentYsfyxjeGreaterThan(String value) {
            addCriterion("payment_ysfyxje >", value, "paymentYsfyxje");
            return (Criteria) this;
        }

        public Criteria andPaymentYsfyxjeGreaterThanOrEqualTo(String value) {
            addCriterion("payment_ysfyxje >=", value, "paymentYsfyxje");
            return (Criteria) this;
        }

        public Criteria andPaymentYsfyxjeLessThan(String value) {
            addCriterion("payment_ysfyxje <", value, "paymentYsfyxje");
            return (Criteria) this;
        }

        public Criteria andPaymentYsfyxjeLessThanOrEqualTo(String value) {
            addCriterion("payment_ysfyxje <=", value, "paymentYsfyxje");
            return (Criteria) this;
        }

        public Criteria andPaymentYsfyxjeLike(String value) {
            addCriterion("payment_ysfyxje like", value, "paymentYsfyxje");
            return (Criteria) this;
        }

        public Criteria andPaymentYsfyxjeNotLike(String value) {
            addCriterion("payment_ysfyxje not like", value, "paymentYsfyxje");
            return (Criteria) this;
        }

        public Criteria andPaymentYsfyxjeIn(List<String> values) {
            addCriterion("payment_ysfyxje in", values, "paymentYsfyxje");
            return (Criteria) this;
        }

        public Criteria andPaymentYsfyxjeNotIn(List<String> values) {
            addCriterion("payment_ysfyxje not in", values, "paymentYsfyxje");
            return (Criteria) this;
        }

        public Criteria andPaymentYsfyxjeBetween(String value1, String value2) {
            addCriterion("payment_ysfyxje between", value1, value2, "paymentYsfyxje");
            return (Criteria) this;
        }

        public Criteria andPaymentYsfyxjeNotBetween(String value1, String value2) {
            addCriterion("payment_ysfyxje not between", value1, value2, "paymentYsfyxje");
            return (Criteria) this;
        }

        public Criteria andProjectIdMd5IsNull() {
            addCriterion("project_id_md5 is null");
            return (Criteria) this;
        }

        public Criteria andProjectIdMd5IsNotNull() {
            addCriterion("project_id_md5 is not null");
            return (Criteria) this;
        }

        public Criteria andProjectIdMd5EqualTo(String value) {
            addCriterion("project_id_md5 =", value, "projectIdMd5");
            return (Criteria) this;
        }

        public Criteria andProjectIdMd5NotEqualTo(String value) {
            addCriterion("project_id_md5 <>", value, "projectIdMd5");
            return (Criteria) this;
        }

        public Criteria andProjectIdMd5GreaterThan(String value) {
            addCriterion("project_id_md5 >", value, "projectIdMd5");
            return (Criteria) this;
        }

        public Criteria andProjectIdMd5GreaterThanOrEqualTo(String value) {
            addCriterion("project_id_md5 >=", value, "projectIdMd5");
            return (Criteria) this;
        }

        public Criteria andProjectIdMd5LessThan(String value) {
            addCriterion("project_id_md5 <", value, "projectIdMd5");
            return (Criteria) this;
        }

        public Criteria andProjectIdMd5LessThanOrEqualTo(String value) {
            addCriterion("project_id_md5 <=", value, "projectIdMd5");
            return (Criteria) this;
        }

        public Criteria andProjectIdMd5Like(String value) {
            addCriterion("project_id_md5 like", value, "projectIdMd5");
            return (Criteria) this;
        }

        public Criteria andProjectIdMd5NotLike(String value) {
            addCriterion("project_id_md5 not like", value, "projectIdMd5");
            return (Criteria) this;
        }

        public Criteria andProjectIdMd5In(List<String> values) {
            addCriterion("project_id_md5 in", values, "projectIdMd5");
            return (Criteria) this;
        }

        public Criteria andProjectIdMd5NotIn(List<String> values) {
            addCriterion("project_id_md5 not in", values, "projectIdMd5");
            return (Criteria) this;
        }

        public Criteria andProjectIdMd5Between(String value1, String value2) {
            addCriterion("project_id_md5 between", value1, value2, "projectIdMd5");
            return (Criteria) this;
        }

        public Criteria andProjectIdMd5NotBetween(String value1, String value2) {
            addCriterion("project_id_md5 not between", value1, value2, "projectIdMd5");
            return (Criteria) this;
        }

        public Criteria andProjectIdIsNull() {
            addCriterion("project_id is null");
            return (Criteria) this;
        }

        public Criteria andProjectIdIsNotNull() {
            addCriterion("project_id is not null");
            return (Criteria) this;
        }

        public Criteria andProjectIdEqualTo(String value) {
            addCriterion("project_id =", value, "projectId");
            return (Criteria) this;
        }

        public Criteria andProjectIdNotEqualTo(String value) {
            addCriterion("project_id <>", value, "projectId");
            return (Criteria) this;
        }

        public Criteria andProjectIdGreaterThan(String value) {
            addCriterion("project_id >", value, "projectId");
            return (Criteria) this;
        }

        public Criteria andProjectIdGreaterThanOrEqualTo(String value) {
            addCriterion("project_id >=", value, "projectId");
            return (Criteria) this;
        }

        public Criteria andProjectIdLessThan(String value) {
            addCriterion("project_id <", value, "projectId");
            return (Criteria) this;
        }

        public Criteria andProjectIdLessThanOrEqualTo(String value) {
            addCriterion("project_id <=", value, "projectId");
            return (Criteria) this;
        }

        public Criteria andProjectIdLike(String value) {
            addCriterion("project_id like", value, "projectId");
            return (Criteria) this;
        }

        public Criteria andProjectIdNotLike(String value) {
            addCriterion("project_id not like", value, "projectId");
            return (Criteria) this;
        }

        public Criteria andProjectIdIn(List<String> values) {
            addCriterion("project_id in", values, "projectId");
            return (Criteria) this;
        }

        public Criteria andProjectIdNotIn(List<String> values) {
            addCriterion("project_id not in", values, "projectId");
            return (Criteria) this;
        }

        public Criteria andProjectIdBetween(String value1, String value2) {
            addCriterion("project_id between", value1, value2, "projectId");
            return (Criteria) this;
        }

        public Criteria andProjectIdNotBetween(String value1, String value2) {
            addCriterion("project_id not between", value1, value2, "projectId");
            return (Criteria) this;
        }

        public Criteria andDelFlagIsNull() {
            addCriterion("del_flag is null");
            return (Criteria) this;
        }

        public Criteria andDelFlagIsNotNull() {
            addCriterion("del_flag is not null");
            return (Criteria) this;
        }

        public Criteria andDelFlagEqualTo(Integer value) {
            addCriterion("del_flag =", value, "delFlag");
            return (Criteria) this;
        }

        public Criteria andDelFlagNotEqualTo(Integer value) {
            addCriterion("del_flag <>", value, "delFlag");
            return (Criteria) this;
        }

        public Criteria andDelFlagGreaterThan(Integer value) {
            addCriterion("del_flag >", value, "delFlag");
            return (Criteria) this;
        }

        public Criteria andDelFlagGreaterThanOrEqualTo(Integer value) {
            addCriterion("del_flag >=", value, "delFlag");
            return (Criteria) this;
        }

        public Criteria andDelFlagLessThan(Integer value) {
            addCriterion("del_flag <", value, "delFlag");
            return (Criteria) this;
        }

        public Criteria andDelFlagLessThanOrEqualTo(Integer value) {
            addCriterion("del_flag <=", value, "delFlag");
            return (Criteria) this;
        }

        public Criteria andDelFlagIn(List<Integer> values) {
            addCriterion("del_flag in", values, "delFlag");
            return (Criteria) this;
        }

        public Criteria andDelFlagNotIn(List<Integer> values) {
            addCriterion("del_flag not in", values, "delFlag");
            return (Criteria) this;
        }

        public Criteria andDelFlagBetween(Integer value1, Integer value2) {
            addCriterion("del_flag between", value1, value2, "delFlag");
            return (Criteria) this;
        }

        public Criteria andDelFlagNotBetween(Integer value1, Integer value2) {
            addCriterion("del_flag not between", value1, value2, "delFlag");
            return (Criteria) this;
        }

        public Criteria andDefine8IsNull() {
            addCriterion("define8 is null");
            return (Criteria) this;
        }

        public Criteria andDefine8IsNotNull() {
            addCriterion("define8 is not null");
            return (Criteria) this;
        }

        public Criteria andDefine8EqualTo(String value) {
            addCriterion("define8 =", value, "define8");
            return (Criteria) this;
        }

        public Criteria andDefine8NotEqualTo(String value) {
            addCriterion("define8 <>", value, "define8");
            return (Criteria) this;
        }

        public Criteria andDefine8GreaterThan(String value) {
            addCriterion("define8 >", value, "define8");
            return (Criteria) this;
        }

        public Criteria andDefine8GreaterThanOrEqualTo(String value) {
            addCriterion("define8 >=", value, "define8");
            return (Criteria) this;
        }

        public Criteria andDefine8LessThan(String value) {
            addCriterion("define8 <", value, "define8");
            return (Criteria) this;
        }

        public Criteria andDefine8LessThanOrEqualTo(String value) {
            addCriterion("define8 <=", value, "define8");
            return (Criteria) this;
        }

        public Criteria andDefine8Like(String value) {
            addCriterion("define8 like", value, "define8");
            return (Criteria) this;
        }

        public Criteria andDefine8NotLike(String value) {
            addCriterion("define8 not like", value, "define8");
            return (Criteria) this;
        }

        public Criteria andDefine8In(List<String> values) {
            addCriterion("define8 in", values, "define8");
            return (Criteria) this;
        }

        public Criteria andDefine8NotIn(List<String> values) {
            addCriterion("define8 not in", values, "define8");
            return (Criteria) this;
        }

        public Criteria andDefine8Between(String value1, String value2) {
            addCriterion("define8 between", value1, value2, "define8");
            return (Criteria) this;
        }

        public Criteria andDefine8NotBetween(String value1, String value2) {
            addCriterion("define8 not between", value1, value2, "define8");
            return (Criteria) this;
        }

        public Criteria andDefine9IsNull() {
            addCriterion("define9 is null");
            return (Criteria) this;
        }

        public Criteria andDefine9IsNotNull() {
            addCriterion("define9 is not null");
            return (Criteria) this;
        }

        public Criteria andDefine9EqualTo(String value) {
            addCriterion("define9 =", value, "define9");
            return (Criteria) this;
        }

        public Criteria andDefine9NotEqualTo(String value) {
            addCriterion("define9 <>", value, "define9");
            return (Criteria) this;
        }

        public Criteria andDefine9GreaterThan(String value) {
            addCriterion("define9 >", value, "define9");
            return (Criteria) this;
        }

        public Criteria andDefine9GreaterThanOrEqualTo(String value) {
            addCriterion("define9 >=", value, "define9");
            return (Criteria) this;
        }

        public Criteria andDefine9LessThan(String value) {
            addCriterion("define9 <", value, "define9");
            return (Criteria) this;
        }

        public Criteria andDefine9LessThanOrEqualTo(String value) {
            addCriterion("define9 <=", value, "define9");
            return (Criteria) this;
        }

        public Criteria andDefine9Like(String value) {
            addCriterion("define9 like", value, "define9");
            return (Criteria) this;
        }

        public Criteria andDefine9NotLike(String value) {
            addCriterion("define9 not like", value, "define9");
            return (Criteria) this;
        }

        public Criteria andDefine9In(List<String> values) {
            addCriterion("define9 in", values, "define9");
            return (Criteria) this;
        }

        public Criteria andDefine9NotIn(List<String> values) {
            addCriterion("define9 not in", values, "define9");
            return (Criteria) this;
        }

        public Criteria andDefine9Between(String value1, String value2) {
            addCriterion("define9 between", value1, value2, "define9");
            return (Criteria) this;
        }

        public Criteria andDefine9NotBetween(String value1, String value2) {
            addCriterion("define9 not between", value1, value2, "define9");
            return (Criteria) this;
        }

        public Criteria andDefine10IsNull() {
            addCriterion("define10 is null");
            return (Criteria) this;
        }

        public Criteria andDefine10IsNotNull() {
            addCriterion("define10 is not null");
            return (Criteria) this;
        }

        public Criteria andDefine10EqualTo(String value) {
            addCriterion("define10 =", value, "define10");
            return (Criteria) this;
        }

        public Criteria andDefine10NotEqualTo(String value) {
            addCriterion("define10 <>", value, "define10");
            return (Criteria) this;
        }

        public Criteria andDefine10GreaterThan(String value) {
            addCriterion("define10 >", value, "define10");
            return (Criteria) this;
        }

        public Criteria andDefine10GreaterThanOrEqualTo(String value) {
            addCriterion("define10 >=", value, "define10");
            return (Criteria) this;
        }

        public Criteria andDefine10LessThan(String value) {
            addCriterion("define10 <", value, "define10");
            return (Criteria) this;
        }

        public Criteria andDefine10LessThanOrEqualTo(String value) {
            addCriterion("define10 <=", value, "define10");
            return (Criteria) this;
        }

        public Criteria andDefine10Like(String value) {
            addCriterion("define10 like", value, "define10");
            return (Criteria) this;
        }

        public Criteria andDefine10NotLike(String value) {
            addCriterion("define10 not like", value, "define10");
            return (Criteria) this;
        }

        public Criteria andDefine10In(List<String> values) {
            addCriterion("define10 in", values, "define10");
            return (Criteria) this;
        }

        public Criteria andDefine10NotIn(List<String> values) {
            addCriterion("define10 not in", values, "define10");
            return (Criteria) this;
        }

        public Criteria andDefine10Between(String value1, String value2) {
            addCriterion("define10 between", value1, value2, "define10");
            return (Criteria) this;
        }

        public Criteria andDefine10NotBetween(String value1, String value2) {
            addCriterion("define10 not between", value1, value2, "define10");
            return (Criteria) this;
        }

        public Criteria andDefine11IsNull() {
            addCriterion("define11 is null");
            return (Criteria) this;
        }

        public Criteria andDefine11IsNotNull() {
            addCriterion("define11 is not null");
            return (Criteria) this;
        }

        public Criteria andDefine11EqualTo(String value) {
            addCriterion("define11 =", value, "define11");
            return (Criteria) this;
        }

        public Criteria andDefine11NotEqualTo(String value) {
            addCriterion("define11 <>", value, "define11");
            return (Criteria) this;
        }

        public Criteria andDefine11GreaterThan(String value) {
            addCriterion("define11 >", value, "define11");
            return (Criteria) this;
        }

        public Criteria andDefine11GreaterThanOrEqualTo(String value) {
            addCriterion("define11 >=", value, "define11");
            return (Criteria) this;
        }

        public Criteria andDefine11LessThan(String value) {
            addCriterion("define11 <", value, "define11");
            return (Criteria) this;
        }

        public Criteria andDefine11LessThanOrEqualTo(String value) {
            addCriterion("define11 <=", value, "define11");
            return (Criteria) this;
        }

        public Criteria andDefine11Like(String value) {
            addCriterion("define11 like", value, "define11");
            return (Criteria) this;
        }

        public Criteria andDefine11NotLike(String value) {
            addCriterion("define11 not like", value, "define11");
            return (Criteria) this;
        }

        public Criteria andDefine11In(List<String> values) {
            addCriterion("define11 in", values, "define11");
            return (Criteria) this;
        }

        public Criteria andDefine11NotIn(List<String> values) {
            addCriterion("define11 not in", values, "define11");
            return (Criteria) this;
        }

        public Criteria andDefine11Between(String value1, String value2) {
            addCriterion("define11 between", value1, value2, "define11");
            return (Criteria) this;
        }

        public Criteria andDefine11NotBetween(String value1, String value2) {
            addCriterion("define11 not between", value1, value2, "define11");
            return (Criteria) this;
        }

        public Criteria andDefine12IsNull() {
            addCriterion("define12 is null");
            return (Criteria) this;
        }

        public Criteria andDefine12IsNotNull() {
            addCriterion("define12 is not null");
            return (Criteria) this;
        }

        public Criteria andDefine12EqualTo(String value) {
            addCriterion("define12 =", value, "define12");
            return (Criteria) this;
        }

        public Criteria andDefine12NotEqualTo(String value) {
            addCriterion("define12 <>", value, "define12");
            return (Criteria) this;
        }

        public Criteria andDefine12GreaterThan(String value) {
            addCriterion("define12 >", value, "define12");
            return (Criteria) this;
        }

        public Criteria andDefine12GreaterThanOrEqualTo(String value) {
            addCriterion("define12 >=", value, "define12");
            return (Criteria) this;
        }

        public Criteria andDefine12LessThan(String value) {
            addCriterion("define12 <", value, "define12");
            return (Criteria) this;
        }

        public Criteria andDefine12LessThanOrEqualTo(String value) {
            addCriterion("define12 <=", value, "define12");
            return (Criteria) this;
        }

        public Criteria andDefine12Like(String value) {
            addCriterion("define12 like", value, "define12");
            return (Criteria) this;
        }

        public Criteria andDefine12NotLike(String value) {
            addCriterion("define12 not like", value, "define12");
            return (Criteria) this;
        }

        public Criteria andDefine12In(List<String> values) {
            addCriterion("define12 in", values, "define12");
            return (Criteria) this;
        }

        public Criteria andDefine12NotIn(List<String> values) {
            addCriterion("define12 not in", values, "define12");
            return (Criteria) this;
        }

        public Criteria andDefine12Between(String value1, String value2) {
            addCriterion("define12 between", value1, value2, "define12");
            return (Criteria) this;
        }

        public Criteria andDefine12NotBetween(String value1, String value2) {
            addCriterion("define12 not between", value1, value2, "define12");
            return (Criteria) this;
        }
    }

    /**
     */
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