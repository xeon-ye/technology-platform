package com.pcitc.mapper.techFamily;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.pcitc.base.common.LayuiTableParam;
import com.pcitc.base.common.TreeNode;
import com.pcitc.base.common.TreeNodeApi;
import com.pcitc.base.stp.techFamily.TechFamily;
import com.pcitc.base.stp.techFamily.TechFamilyExample;

public interface TechFamilyMapper {
    int countByExample(TechFamilyExample example);

    int deleteByExample(TechFamilyExample example);

    int deleteByPrimaryKey(String tfmTypeId);

    int insert(TechFamily record);

    int insertSelective(TechFamily record);

    List<TechFamily> selectByExample(TechFamilyExample example);

    TechFamily selectByPrimaryKey(String tfmTypeId);

    int updateByPrimaryKey(TechFamily record);
    
    
    /**
     * 获取技术族分类树
     * @param function
     * @return
     */
    List<TreeNode> selectTreeNodeByLevel(TechFamily techType);
    
    /**
     * 获取技术族分类树
     * @param function
     * @return
     */
    List<TreeNode> selectTreeNodeByLevelCond(TechFamily techType);
    
    /**
	 * 查询技术族分类列表
	 * @param param
	 * @return
	 * @throws Exception
	 */
	public List<TechFamily> getTechTypeList(LayuiTableParam param);
	
	/**
	 * @param techType
	 * @return
	 * 按照固定条件修改类型
	 */
	public int updateTechTypeCondition(TechFamily techType);
	
	/**
	 * 查询最大的分类编码
	 * @param map
	 * @return
	 */
	public String getMaxTechTypeCode(HashMap<String, String> map);
	
	
	public List<TechFamily> getTechFamilyListByCodes(List list);
	
	
	
	
	public List<TreeNode> getListByCodesForApi(List<String> list);
	
	
	
	public List<TechFamily> getTechFamilyList(Map map);
	
	
	
	public List getList(Map map);
	    
	public   int getCount(Map map);
	
	
	public List<TreeNode> getTreeNodeList(Map map);
	
	public List<TreeNodeApi> getTreeNodeApiList(Map map);

	public String getMaxTechTypeCodeByParentId(String parentId);
	
	 int deleteByParentId(String tfmTypeId);

	 public List<TechFamily> getTechFalmilyByAccurateInfo(TechFamily techType);
	
}