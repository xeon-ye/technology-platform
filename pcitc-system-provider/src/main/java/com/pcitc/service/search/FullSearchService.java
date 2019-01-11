package com.pcitc.service.search;

import com.alibaba.fastjson.JSONObject;
import com.pcitc.base.common.LayuiTableData;
import com.pcitc.base.common.LayuiTableParam;
import com.pcitc.base.stp.out.OutProjectPlan;
import com.pcitc.base.system.SysFileVo;

import java.util.HashMap;
import java.util.List;

public interface FullSearchService {

	/**
	 * 科技查询
	 * @param param
	 * @return
	 */
	public LayuiTableData getTableDataScientific(LayuiTableParam param);

    /**
     * 成果查询
     * @param param
     * @return
     */
    public LayuiTableData getTableDataAchivement(LayuiTableParam param);

    /**
     * 首页查询
     * @param param
     * @return
     */
    public LayuiTableData getTableSearch(LayuiTableParam param);

    /**
     * 设置文件标志bak10,file
     * @param vo
     * @return
     */
    public JSONObject setFileFlag(SysFileVo vo);
}
