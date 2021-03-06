package com.pcitc.base.expert;

import java.io.Serializable;
import java.util.Date;

/**
 * zjk_achievement
 * @author 
 */
public class ZjkAchievement implements Serializable {
    /**
     * 主键
     */
    private String id;

    /**
     * 专家ID
     */
    private String expertId;

    /**
     * 数据来源（1本系统，2外系统）
     */
    private String sourceType="";

    /**
     * 删除状态（0未删除，1删除）
     */
    private String delStatus="";

    /**
     * 成果名称
     */
    private String achieveName="";

    /**
     * 申请年度
     */
    private String applyYear="";

    /**
     * 申请单位
     */
    private String applyUnit="";
    
    private String  applyUnitStr;

    /**
     * 成果类别
     */
    private String achieveType="";
    private String achieveTypeStr;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 外系统ID
     */
    private String outSystemId="";

    /*
    * 录入/导入标识
    * */
    private String  flag = "";
    
    private String secretLevel="";
    private String secretLevelStr="";
    
    private String knowledgeScope="";
    private String knowledgePerson="";
    private String createUser="";
    
    
    
    
    
    
    
	public String getKnowledgeScope() {
		return knowledgeScope;
	}

	public void setKnowledgeScope(String knowledgeScope) {
		this.knowledgeScope = knowledgeScope;
	}

	public String getKnowledgePerson() {
		return knowledgePerson;
	}

	public void setKnowledgePerson(String knowledgePerson) {
		this.knowledgePerson = knowledgePerson;
	}

	public String getCreateUser() {
		return createUser;
	}

	public void setCreateUser(String createUser) {
		this.createUser = createUser;
	}

	public String getSecretLevel() {
		return secretLevel;
	}

	public void setSecretLevel(String secretLevel) {
		this.secretLevel = secretLevel;
	}

	public String getSecretLevelStr() {
		return secretLevelStr;
	}

	public void setSecretLevelStr(String secretLevelStr) {
		this.secretLevelStr = secretLevelStr;
	}

	public String getApplyUnitStr() {
		return applyUnitStr;
	}

	public void setApplyUnitStr(String applyUnitStr) {
		this.applyUnitStr = applyUnitStr;
	}

	public String getAchieveTypeStr() {
		return achieveTypeStr;
	}

	public void setAchieveTypeStr(String achieveTypeStr) {
		this.achieveTypeStr = achieveTypeStr;
	}

	private static final long serialVersionUID = 1L;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getExpertId() {
        return expertId;
    }

    public void setExpertId(String expertId) {
        this.expertId = expertId;
    }

    public String getSourceType() {
        return sourceType;
    }

    public void setSourceType(String sourceType) {
        this.sourceType = sourceType;
    }

    public String getDelStatus() {
        return delStatus;
    }

    public void setDelStatus(String delStatus) {
        this.delStatus = delStatus;
    }

    public String getAchieveName() {
        return achieveName;
    }

    public void setAchieveName(String achieveName) {
        this.achieveName = achieveName;
    }

    public String getApplyYear() {
        return applyYear;
    }

    public void setApplyYear(String applyYear) {
        this.applyYear = applyYear;
    }

    public String getApplyUnit() {
        return applyUnit;
    }

    public void setApplyUnit(String applyUnit) {
        this.applyUnit = applyUnit;
    }

    public String getAchieveType() {
        return achieveType;
    }

    public void setAchieveType(String achieveType) {
        this.achieveType = achieveType;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getOutSystemId() {
        return outSystemId;
    }

    public void setOutSystemId(String outSystemId) {
        this.outSystemId = outSystemId;
    }

    public String getFlag() {
        return flag;
    }

    public void setFlag(String flag) {
        this.flag = flag;
    }
}