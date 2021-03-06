package com.pcitc.service.feign;
import java.util.List;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.pcitc.base.common.LayuiTableData;
import com.pcitc.base.common.LayuiTableParam;
import com.pcitc.base.system.SysDictionary;
import com.pcitc.base.system.SysFunction;
import com.pcitc.base.system.SysMeeting;
import com.pcitc.base.system.SysPost;
import com.pcitc.base.system.SysUser;
import com.pcitc.base.workflow.WorkflowVo;

@FeignClient(value = "kjpt-system-provider", fallback = SystemHystric.class)
public interface SystemRemoteClient {
    @RequestMapping(value = "/workflow-provider/workflow/start/id", method = RequestMethod.POST)
    public String startWorkflowByProcessDefinitionId(@RequestBody WorkflowVo workflowVo);

    @RequestMapping(value = "/user-provider/user/get-user-bypostcode/{postCode}", method = RequestMethod.POST)
    public List<SysUser> selectUsersByPostCode(@PathVariable(value = "postCode", required = true) String postCode);

    @RequestMapping(value = "/post-provider/post/get-post-bycode/{postCode}", method = RequestMethod.POST)
    public SysPost getSysPostByCode(@PathVariable(value = "postCode", required = true) String postCode);
    /**
     * 字典-根据父编码获取子集
     * @param parentCode
     * @return
     */
    @RequestMapping(value = "/dictionary-provider/dictionary/{parentCode}", method = RequestMethod.POST)
    public List<SysDictionary> getDictionaryListByParentCode(@PathVariable(value = "parentCode", required = false) String parentCode);
    

   
	/**
	 * 获取项目计划数据
	 * @param param
	 * @return
	 * @throws Exception
	 */
    @RequestMapping(value = "/out-project-plan-provider/project-plan/page/list", method = RequestMethod.POST)
	public LayuiTableData selectProjectPlanByCond(@RequestBody LayuiTableParam param);
    /**
     * 获取项目实际完成金额数据
     * @param param
     * @return
     */
	@RequestMapping(value = "/out-project-provider/common-project/list", method = RequestMethod.POST)
	public LayuiTableData selectCommonProjectByCond(@RequestBody LayuiTableParam param);
	
	//增加会议纪要
	@RequestMapping(value = "/system-provider/sys_meeting/add", method = RequestMethod.POST)
	public String insertSysMeeting(@RequestBody SysMeeting sysMeeting);
	
	//查询成果信息
    @RequestMapping(value = "/out-provider/appraisal-list", method = RequestMethod.POST)
    public LayuiTableData getOutAppraisalListPage(@RequestBody LayuiTableParam param) throws Exception;
    //查询专利信息
    @RequestMapping(value = "/out-patent-provider/outpatent_list", method = RequestMethod.POST)
    public LayuiTableData selectOutPatentList(@RequestBody LayuiTableParam param);
    //查询奖励信息
    @RequestMapping(value = "/out-provider/reward-list", method = RequestMethod.POST)
    public LayuiTableData getOutRewardListPage(@RequestBody LayuiTableParam param) throws Exception;
    //查询课题信息
    @RequestMapping(value = "/out-provider/project-list-expert", method = RequestMethod.POST)
    public LayuiTableData getOutProjectListPageExpert(@RequestBody LayuiTableParam param) throws Exception;

    //根据菜单URL查询菜单信息
    @RequestMapping(value = "/function-provider/get-by-url", method = RequestMethod.POST)
    public SysFunction getFunctionByUrl(@RequestBody String url);
    
}
