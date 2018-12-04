package com.pcitc.service.out;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.pcitc.base.common.LayuiTableData;
import com.pcitc.base.common.LayuiTableParam;
import com.pcitc.base.stp.out.OutPatentWithBLOBs;

public interface OutPatentService {

	public int insertPatentData(List<OutPatentWithBLOBs> list);
	
	public LayuiTableData getOutPatentPage(LayuiTableParam param);
	
	/**
	 * 查询最大的导入日期，从这个日期开始导入
	 * @return
	 */
	public String getMaxImportDate(String remarks);
	
	public int getOutPatentCount(HashMap<String, String> map);
	
	public List getWXLXInfo(String nd);
	
	/**
     * @param nd
     * @return
     * 得到某个年度各专利类型在各个研究院的分布
     */
    public List getTypeInfoByUnit(String nd);
    
    /**
     * @param nd
     * @return
     * 得到某个年度各专利类型在各个研究院、分公司、集团、外部单位、研发中心的分布
     */
    public List getApplyAgreeCountBySix(String nd);
    
    public LayuiTableData getWXLXDetailsInfo(LayuiTableParam param);
    
    /**
     * @param nd
     * @return
     * 得到某个年度各专利类型在各个研究院的分布 详情页面
     */
    public LayuiTableData getTypeInfoByUnitDetails(LayuiTableParam param);
    
    /**
     * @return
     * 领导二级页面，直属研究院，8个院专利申请、授权、发明专利数量
     */
    public List getPatentTypeCountByInstituteForLD(Map hashmap);
	
}
