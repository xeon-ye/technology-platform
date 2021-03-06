package com.pcitc.web.expert;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.pcitc.base.common.LayuiTableData;
import com.pcitc.base.common.LayuiTableParam;
import com.pcitc.base.common.Result;
import com.pcitc.base.expert.ZjkAchievement;
import com.pcitc.base.expert.ZjkBase;
import com.pcitc.base.expert.ZjkPatent;
import com.pcitc.base.expert.ZjkProject;
import com.pcitc.base.expert.ZjkReward;
import com.pcitc.base.expert.ZjkRewardPunish;
import com.pcitc.service.expert.IExpertService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api(value = "Expert-API",tags = {"专家库相关的接口"})
@RestController
public class ExpertProviderClient {
	
	
	private final static Logger logger = LoggerFactory.getLogger(ExpertProviderClient.class); 
	@Autowired
    private IExpertService expertService; 
	
	
	
	
	
	/**===============================================专家===================================================*/
	
	
	
	@ApiOperation(value = "人才转为专家", notes = "人才转为专家")
	@RequestMapping(value = "/expert/outPersonToZjkBase", method = RequestMethod.POST)
	public Integer outPersonToZjkBase(@RequestBody  Map map)throws Exception
	{

		JSONObject parma = JSONObject.parseObject(JSONObject.toJSONString(map));
		System.out.println(">>>>>>>>>>人才转为专家 参数: "+parma.toJSONString());
		Integer count=expertService.outPersonToZjkBase(map);
		return count;
	}
	
	
	
	
	
	
	@ApiOperation(value = "获取专家（分页）", notes = "获取专家（分页）")
	@RequestMapping(value = "/expert/page", method = RequestMethod.POST)
	public LayuiTableData getZjkBaseList(@RequestBody LayuiTableParam param)throws Exception
	{
		JSONObject result = JSONObject.parseObject(JSONObject.toJSONString(param));
		System.out.println("======== 获取专家参数: "+result.toString());
		return expertService.getZjkBasePage(param) ;
	}
	
	
	@ApiOperation(value = "获取专家", notes = "获取专家")
	@RequestMapping(value = "/expert/list", method = RequestMethod.POST)
	public JSONArray getZjkBase_List(@RequestBody Map param)throws Exception
	{
		logger.info("=== ZjkBase param============"+param);
		List list=expertService.getZjkBaseList(param);
		JSONArray json = JSONArray.parseArray(JSON.toJSONString(list));
		return json;
	}
	
	@ApiOperation(value = "获取专家个数", notes = "获取专家个数")
	@RequestMapping(value = "/expert/getZjkBaseCount", method = RequestMethod.POST)
	public Integer getZjkBaseCount()throws Exception
	{
		Integer count=expertService.getZjkBaseCount();
		return count;
	}
	
	

	
	@ApiOperation(value = "增加专家信息", notes = "增加专家信息")
	@RequestMapping(value = "/expert/add", method = RequestMethod.POST)
	public String insertZjkBase(@RequestBody ZjkBase zjkBase) throws Exception{
		logger.info("====================add ZjkBase....========================");
		Integer count= expertService.insertZjkBase(zjkBase);
		return zjkBase.getId();
	}
	
	
	@ApiOperation(value = "修改专家信息", notes = "修改专家信息")
	@RequestMapping(value = "/expert/update", method = RequestMethod.POST)
	public Integer updateZjkBase(@RequestBody ZjkBase zjkBase) throws Exception{
		logger.info("==================update ZjkBase===========================");
		return expertService.updateZjkBase(zjkBase);
	}
	
	@ApiOperation(value = "根据ID物理删除专家信息", notes = "根据ID删除专家信息")
	@RequestMapping(value = "/expert/delete/{id}", method = RequestMethod.POST)
	public int deleteZjkBase(@PathVariable("id") String id)throws Exception{
		logger.info("=============================根据ID物理删除专家信息 zjkBase==="+id+"==============");
		return expertService.deleteZjkBase(id) ;
	}
	
	
	@ApiOperation(value = "根据ID逻辑删除专家信息", notes = "根据ID删除专家信息")
	@RequestMapping(value = "/expert/logic_delete/{id}", method = RequestMethod.POST)
	public int deleteLoginZjkBase(@PathVariable("id") String id)throws Exception{
		logger.info("=============================根据ID逻辑删除专家信息 zjkBase==="+id+"==============");
		return expertService.deleteLogicZjkBase(id);
	}
	
	
	@ApiOperation(value = "根据ID获取专家信息详情", notes = "根据ID获取专家信息详情")
	@RequestMapping(value = "/expert/get/{id}", method = RequestMethod.GET)
	public ZjkBase selectMeetingById(@PathVariable(value = "id", required = true) String id) throws Exception {
		logger.info("===============================get zjkBase id "+id+"===========");
		return expertService.selectZjkBase(id);
	}
	
	
	
	@ApiOperation(value = "根据ID获取专家详情", notes = "根据ID获取专家详情")
	@RequestMapping(value = "/expert/getByNum/{num}", method = RequestMethod.GET)
	public ZjkBase getZjkBaseByNum(@PathVariable(value = "num", required = true) String num) throws Exception {
		logger.info("===============================get getZjkBaseByNum num "+num+"===========");
		return expertService.getZjkBaseByNum(num);
	}
	
	
	
	
	@ApiOperation(value = "导入专家信息", notes = "导入专家信息")
    @RequestMapping(value = "/expert/excel_input", method = RequestMethod.POST)
    public Result excel_input(@RequestBody List<ZjkBase> list) throws Exception 
	{
		Result result=new Result();
        try {
            int count = expertService.insertBatch(list);
            result.setSuccess(true);
        } catch (Exception e) {
        	 result.setSuccess(false);
        	 result.setMessage("导入专家信息失败");
            logger.error("导入专家信息失败", e);
        }
        return result;
    }
	
	
	
	
	
	
	
	
	
	
	
	@ApiOperation(value = "获取专家奖励（分页）", notes = "获取专家奖励（分页）")
	@RequestMapping(value = "/expert_reward/page", method = RequestMethod.POST)
	public LayuiTableData getZjkRewardList(@RequestBody LayuiTableParam param)throws Exception
	{
		
		logger.info("=== ZjkReward param============"+param);
		return expertService.getZjkRewardPage(param) ;
	}
	
	@ApiOperation(value = "增加专家奖励信息", notes = "增加专家奖励信息")
	@RequestMapping(value = "/expert_reward/add", method = RequestMethod.POST)
	public String insertZjkReward(@RequestBody ZjkReward zjkReward) throws Exception{
		logger.info("====================add zjkReward....========================");
		Integer count= expertService.insertZjkReward(zjkReward);
		return zjkReward.getId();
	}
	
	
	@ApiOperation(value = "修改专家奖励信息", notes = "修改专家奖励信息")
	@RequestMapping(value = "/expert_reward/update", method = RequestMethod.POST)
	public Integer updateZjkReward(@RequestBody ZjkReward zjkReward) throws Exception{
		logger.info("==================update ZjkReward===========================");
		return expertService.updateZjkReward(zjkReward);
	}
	
	@ApiOperation(value = "根据ID物理删除专家奖励信息", notes = "根据ID删除专家奖励信息")
	@RequestMapping(value = "/expert_reward/delete/{id}", method = RequestMethod.POST)
	public int deleteZjkReward(@PathVariable("id") String id)throws Exception{
		logger.info("=============================根据ID物理删除专家奖励信息 ZjkReward==="+id+"==============");
		return expertService.deleteZjkReward(id) ;
	}
	
	
	@ApiOperation(value = "根据ID逻辑删除专家奖励信息", notes = "根据ID删除专家奖励信息")
	@RequestMapping(value = "/expert_reward/logic_delete/{id}", method = RequestMethod.POST)
	public int deleteLoginZjkReward(@PathVariable("id") String id)throws Exception{
		logger.info("=============================根据ID逻辑删除专家奖励信息 ZjkReward==="+id+"==============");
		return expertService.deleteLogicZjkReward(id);
	}
	
	
	@ApiOperation(value = "根据ID获取专家奖励信息详情", notes = "根据ID获取专家奖励信息详情")
	@RequestMapping(value = "/expert_reward/get/{id}", method = RequestMethod.GET)
	public ZjkReward selectZjkRewardId(@PathVariable(value = "id", required = true) String id) throws Exception {
		logger.info("===============================get ZjkReward id "+id+"===========");
		return expertService.selectZjkReward(id);
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	

	@ApiOperation(value = "获取专家项目（分页）", notes = "获取专家项目（分页）")
	@RequestMapping(value = "/expert_project/page", method = RequestMethod.POST)
	public LayuiTableData getZjkProjectList(@RequestBody LayuiTableParam param)throws Exception
	{
		
		logger.info("=== ZjkProject param============"+param);
		return expertService.getZjkProjectPage(param) ;
	}
	
	@ApiOperation(value = "增加专家项目信息", notes = "增加专家项目信息")
	@RequestMapping(value = "/expert_project/add", method = RequestMethod.POST)
	public String insertZjkProject(@RequestBody ZjkProject zjkProject) throws Exception{
		logger.info("====================add ZjkProject....========================");
		Integer count= expertService.insertZjkProject(zjkProject);
		return zjkProject.getId();
	}
	
	
	@ApiOperation(value = "修改专家项目信息", notes = "修改专家项目信息")
	@RequestMapping(value = "/expert_project/update", method = RequestMethod.POST)
	public Integer updateZjkProject(@RequestBody ZjkProject zjkProject) throws Exception{
		logger.info("==================update ZjkProject===========================");
		return expertService.updateZjkProject(zjkProject);
	}
	
	@ApiOperation(value = "根据ID物理删除专家项目信息", notes = "根据ID删除专家项目信息")
	@RequestMapping(value = "/expert_project/delete/{id}", method = RequestMethod.POST)
	public int deleteZjkProject(@PathVariable("id") String id)throws Exception{
		logger.info("=============================根据ID物理删除专家项目信息 ZjkProject==="+id+"==============");
		return expertService.deleteZjkProject(id) ;
	}
	
	
	@ApiOperation(value = "根据ID逻辑删除专家项目信息", notes = "根据ID删除专家项目信息")
	@RequestMapping(value = "/expert_project/logic_delete/{id}", method = RequestMethod.POST)
	public int deleteLoginZjkProject(@PathVariable("id") String id)throws Exception{
		logger.info("=============================根据ID逻辑删除专家项目信息 ZjkProject==="+id+"==============");
		return expertService.deleteLogicZjkProjectById(id);
	}
	
	
	@ApiOperation(value = "根据ID获取专家项目信息详情", notes = "根据ID获取专家项目信息详情")
	@RequestMapping(value = "/expert_project/get/{id}", method = RequestMethod.GET)
	public ZjkProject selectZjkProjectId(@PathVariable(value = "id", required = true) String id) throws Exception {
		logger.info("===============================get ZjkProject id "+id+"===========");
		return expertService.selectZjkProject(id);
	}
	
	
	
	
	
	
	
	


	@ApiOperation(value = "获取专家专利（分页）", notes = "获取专家专利（分页）")
	@RequestMapping(value = "/expert_patent/page", method = RequestMethod.POST)
	public LayuiTableData getZjkPatentList(@RequestBody LayuiTableParam param)throws Exception
	{
		
		logger.info("=== ZjkPatent param============"+param);
		return expertService.getZjkPatentPage(param) ;
	}
	
	@ApiOperation(value = "增加专家专利信息", notes = "增加专家专利信息")
	@RequestMapping(value = "/expert_patent/add", method = RequestMethod.POST)
	public String insertZjkPatent(@RequestBody ZjkPatent zjkPatent) throws Exception{
		logger.info("====================add ZjkPatent....========================");
		Integer count= expertService.insertZjkPatent(zjkPatent);
		return zjkPatent.getId();
	}
	
	
	@ApiOperation(value = "修改专家专利信息", notes = "修改专家专利信息")
	@RequestMapping(value = "/expert_patent/update", method = RequestMethod.POST)
	public Integer updateZjkPatent(@RequestBody ZjkPatent zjkPatent) throws Exception{
		logger.info("==================update zjkPatent===========================");
		return expertService.updateZjkPatent(zjkPatent);
	}
	
	@ApiOperation(value = "根据ID物理删除专家专利信息", notes = "根据ID删除专家专利信息")
	@RequestMapping(value = "/expert_patent/delete/{id}", method = RequestMethod.POST)
	public int deleteZjkPatent(@PathVariable("id") String id)throws Exception{
		logger.info("=============================根据ID物理删除专家专利信息 ZjkPatent==="+id+"==============");
		return expertService.deleteZjkPatent(id) ;
	}
	
	
	@ApiOperation(value = "根据ID逻辑删除专家专利信息", notes = "根据ID删除专家专利信息")
	@RequestMapping(value = "/expert_patent/logic_delete/{id}", method = RequestMethod.POST)
	public int deleteLoginZjkPatent(@PathVariable("id") String id)throws Exception{
		logger.info("=============================根据ID逻辑删除专家专利信息==="+id+"==============");
		return expertService.deleteLogicZjkPatentById(id);
	}
	
	
	@ApiOperation(value = "根据ID获取专家专利信息详情", notes = "根据ID获取专家专利信息详情")
	@RequestMapping(value = "/expert_patent/get/{id}", method = RequestMethod.GET)
	public ZjkPatent selectZjkPatentId(@PathVariable(value = "id", required = true) String id) throws Exception {
		logger.info("===============================get ZjkPatent id "+id+"===========");
		return expertService.selectZjkPatent(id);
	}
	
	
	@ApiOperation(value = "批量保存-专家专利", notes = "批量保存-专家专利")
	@RequestMapping(value = "/expert_patent/insertBatchZjkPatent", method = RequestMethod.POST)
	public Integer insertBatchZjkPatent(@RequestBody String jsonStr) throws Exception 
    {
    	
		System.out.println("======insertBatchZjkPatent===========" + jsonStr);
		List<ZjkPatent> list = JSONObject.parseArray(jsonStr, ZjkPatent.class);
		expertService.deleteAllZjkPatent();
		return expertService.insertBatchZjkPatent(list);

	}
	
	
	
	
	
	
	
	
	



	@ApiOperation(value = "获取专家成果（分页）", notes = "获取专家成果（分页）")
	@RequestMapping(value = "/expert_achievement/page", method = RequestMethod.POST)
	public LayuiTableData getZjkAchievementList(@RequestBody LayuiTableParam param)throws Exception
	{
		
		logger.info("=== ZjkAchievement param============"+param);
		return expertService.getZjkAchievementPage(param) ;
	}
	
	@ApiOperation(value = "增加专家成果信息", notes = "增加专家成果信息")
	@RequestMapping(value = "/expert_achievement/add", method = RequestMethod.POST)
	public String insertZjkAchievement(@RequestBody ZjkAchievement zjkAchievement) throws Exception{
		logger.info("====================add ZjkAchievement....========================");
		Integer count= expertService.insertZjkAchievement(zjkAchievement);
		return zjkAchievement.getId();
	}
	
	
	@ApiOperation(value = "修改专家成果信息", notes = "修改专家成果信息")
	@RequestMapping(value = "/expert_achievement/update", method = RequestMethod.POST)
	public Integer updateZjkAchievement(@RequestBody ZjkAchievement zjkAchievement) throws Exception{
		logger.info("==================update zjkAchievement===========================");
		return expertService.updateZjkAchievement(zjkAchievement);
	}
	
	@ApiOperation(value = "根据ID物理删除专家成果信息", notes = "根据ID删除专家成果信息")
	@RequestMapping(value = "/expert_achievement/delete/{id}", method = RequestMethod.POST)
	public int deleteZjkAchievement(@PathVariable("id") String id)throws Exception{
		logger.info("=============================根据ID物理删除专家成果信息 ZjkAchievement==="+id+"==============");
		return expertService.deleteZjkAchievement(id) ;
	}
	
	
	@ApiOperation(value = "根据ID逻辑删除专家成果信息", notes = "根据ID删除专家成果信息")
	@RequestMapping(value = "/expert_achievement/logic_delete/{id}", method = RequestMethod.POST)
	public int deleteLoginZjkAchievement(@PathVariable("id") String id)throws Exception{
		logger.info("=============================根据ID逻辑删除专家成果信息==="+id+"==============");
		return expertService.deleteLogicZjkAchievementById(id);
	}
	
	
	@ApiOperation(value = "根据ID获取专家成果信息详情", notes = "根据ID获取专家成果信息详情")
	@RequestMapping(value = "/expert_achievement/get/{id}", method = RequestMethod.GET)
	public ZjkAchievement selectZjkAchievementId(@PathVariable(value = "id", required = true) String id) throws Exception {
		logger.info("===============================get ZjkAchievement id "+id+"===========");
		return expertService.selectZjkAchievement(id);
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	

	
	@ApiOperation(value = "获取专家奖惩（分页）", notes = "获取专家奖惩（分页）")
	@RequestMapping(value = "/expert_punish/page", method = RequestMethod.POST)
	public LayuiTableData getZjkRewardPunishList(@RequestBody LayuiTableParam param)throws Exception
	{
		
		logger.info("=== getZjkRewardPunishList param============"+param);
		return expertService.getZjkRewardPunishPage(param) ;
	}
	
	@ApiOperation(value = "增加专家奖惩信息", notes = "增加专家奖惩信息")
	@RequestMapping(value = "/expert_punish/add", method = RequestMethod.POST)
	public String insertZjkRewardPunish(@RequestBody ZjkRewardPunish ZjkRewardPunish) throws Exception{
		logger.info("====================add getZjkRewardPunishList....========================");
		Integer count= expertService.insertZjkRewardPunish(ZjkRewardPunish);
		return ZjkRewardPunish.getId();
	}
	
	@ApiOperation(value = "修改专家奖惩信息", notes = "修改专家奖惩信息")
	@RequestMapping(value = "/expert_punish/update", method = RequestMethod.POST)
	public Integer updateZjkRewardPunish(@RequestBody ZjkRewardPunish ZjkRewardPunish) throws Exception{
		logger.info("==================update getZjkRewardPunishList===========================");
		return expertService.updateZjkRewardPunish(ZjkRewardPunish);
	}
	
	@ApiOperation(value = "根据ID物理删除专家奖惩信息", notes = "根据ID删除专家奖惩信息")
	@RequestMapping(value = "/expert_punish/delete/{id}", method = RequestMethod.POST)
	public int deleteZjkRewardPunish(@PathVariable("id") String id)throws Exception{
		logger.info("=============================根据ID物理删除专家奖惩信息 ZjkRewardPunish==="+id+"==============");
		return expertService.deleteZjkRewardPunish(id) ;
	}
	
	
	@ApiOperation(value = "根据ID获取专家奖惩信息详情", notes = "根据ID获取专家奖惩信息详情")
	@RequestMapping(value = "/expert_punish/get/{id}", method = RequestMethod.GET)
	public ZjkRewardPunish selectZjkRewardPunishId(@PathVariable(value = "id", required = true) String id) throws Exception {
		logger.info("===============================get getZjkRewardPunishList id "+id+"===========");
		return expertService.selectZjkRewardPunish(id);
	}
	
	
	@ApiOperation(value = "批量保存-专家奖惩", notes = "批量保存-专家奖惩")
	@RequestMapping(value = "/expert_punish/insertBatchPunish", method = RequestMethod.POST)
	public Integer insertBatchPunish(@RequestBody String jsonStr) throws Exception 
    {
    	
		System.out.println("==============insertBatchPunish=============" + jsonStr);
		List<ZjkRewardPunish> list = JSONObject.parseArray(jsonStr, ZjkRewardPunish.class);
		expertService.deleteAllZjkRewardPunish();
		return expertService.insertBatchZjkRewardPunish(list);

	}
	

}
