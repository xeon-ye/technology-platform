package com.pcitc.service.system;

import java.util.HashMap;
import java.util.List;

import com.alibaba.fastjson.JSONObject;
import com.pcitc.base.system.SysFunctionProperty;

/**
 * @author ms 菜单配置管理逻辑层
 */
public interface SysFunctionPropertyService {

	/**
	 * 条件查询菜单配置列表
	 * @param obj
	 * @return
	 * @throws Exception
	 */
	JSONObject selectSysFunctionPropertyList(SysFunctionProperty obj) throws Exception;
	
	/**
	 * 新增或修改菜单配置
	 * @param function
	 * @return
	 * @throws Exception
	 */
	int updateOrInsertSysFunctionProperty(SysFunctionProperty obj) throws Exception;
	
	/**
	 * 删除菜单配置
	 * @param id
	 * @return
	 * @throws Exception
	 */
	int deleteSysFunctionProperty(String id) throws Exception;
	
	/**
     * 根据id查询菜单配置
     * @param id
     * @return
     */
    SysFunctionProperty getSysFunctionPropertyById(String id) throws Exception;
    
    /**
     * 查询菜单配置
     * @param obj
     * @return
     */
    SysFunctionProperty getSysFunctionProperty(SysFunctionProperty obj) throws Exception;

    /**
     * 查询已经某个菜单、某个配置项、某个岗位已经配置的研究院
     * @return
     */
    public List selectInstituteData(HashMap<String, Object> hashmap);
}
