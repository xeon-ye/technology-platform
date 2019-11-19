package com.pcitc.web.controller.expert;

import java.util.Date;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONObject;
import com.pcitc.base.common.Constant;
import com.pcitc.base.common.LayuiTableData;
import com.pcitc.base.common.LayuiTableParam;
import com.pcitc.base.common.Result;
import com.pcitc.base.common.enums.RequestProcessStatusEnum;
import com.pcitc.base.expert.ZjkBase;
import com.pcitc.base.util.CommonUtil;
import com.pcitc.web.common.BaseController;
import com.pcitc.web.utils.RestMessage;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;


@Api(value = "Expert-API",tags = {"专家库-专家接口"})
@RestController
public class ExpertController extends BaseController {
	
	
	/**
	 * 获取专家（分页）
	 */
	private static final String PAGE_EXPERT_URL = "http://kjpt-zuul/stp-proxy/expert/page";
	/**
	 * 根据ID获取对象信息
	 */
	public static final String ADD_EXPERT_URL = "http://kjpt-zuul/stp-proxy/expert/add";

	/**
	 * 根据ID获取对象信息
	 */
	public static final String UPDATE_EXPERT_URL = "http://kjpt-zuul/stp-proxy/expert/update";

	/**
	 * 根据ID逻辑删除
	 */
	private static final String DEL_EXPERT_LOGIC_URL = "http://kjpt-zuul/stp-proxy/expert/logic_delete/";
	
	
	/**
	 * 根据ID删除
	 */
	private static final String DEL_EXPERT_URL = "http://kjpt-zuul/stp-proxy/expert/delete/";
	
	
	/**
	 * 根据ID获取对象信息
	 */
	public static final String GET_EXPERT_URL = "http://kjpt-zuul/stp-proxy/expert/get/";

    
	
	
	
	/**
	  * 获取专家（分页）
	 */
	
	
    @ApiOperation(value = "专家管理（分页）", notes = "专家管理（分页）")
    @ApiImplicitParams({
        @ApiImplicitParam(name = "page", value = "页码", dataType = "string", paramType = "query",required=true),
        @ApiImplicitParam(name = "limit", value = "每页显示条数", dataType = "string", paramType = "query",required=true),
        @ApiImplicitParam(name = "name", value = "专家名称", dataType = "string", paramType = "query")
    })
    @RequestMapping(value = "/expert-api/list", method = RequestMethod.POST)
	public String getExpertPage(
			
			@RequestParam(required = true) Integer page,
            @RequestParam(required = true) Integer limit,
            @RequestParam(required = false) String name,
			HttpServletRequest request, HttpServletResponse response)throws Exception 
     {

    	LayuiTableParam param =new LayuiTableParam();
    	param.getParam().put("name", name);
    	param.getParam().put("delStatus", Constant.DEL_STATUS_NOT);
    	param.setLimit(limit);
    	param.setPage(page);
		LayuiTableData layuiTableData = new LayuiTableData();
		HttpEntity<LayuiTableParam> entity = new HttpEntity<LayuiTableParam>(param, httpHeaders);
		ResponseEntity<LayuiTableData> responseEntity = restTemplate.exchange(PAGE_EXPERT_URL, HttpMethod.POST, entity, LayuiTableData.class);
		int statusCode = responseEntity.getStatusCodeValue();
		if (statusCode == 200) {
			layuiTableData = responseEntity.getBody();
		}
		JSONObject result = JSONObject.parseObject(JSONObject.toJSONString(layuiTableData));
		logger.info("============获取专家列表（分页） " + result.toString());
		return result.toString();
	}
    
    
    
    
    @ApiOperation(value = "专家查询（分页）", notes = "专家查询（分页）")
    @ApiImplicitParams({
        @ApiImplicitParam(name = "page",           value = "页码", dataType = "string", paramType = "query"),
        @ApiImplicitParam(name = "limit",          value = "每页显示条数", dataType = "string", paramType = "query"),
        @ApiImplicitParam(name = "name",           value = "专家名称", dataType = "string", paramType = "query"),
        @ApiImplicitParam(name = "belongUnit",     value = "所在单位", dataType = "string", paramType = "query"),
        @ApiImplicitParam(name = "useStatus",      value = "状态", dataType = "string", paramType = "query"),
        @ApiImplicitParam(name = "post",           value = "职务", dataType = "string", paramType = "query"),
        @ApiImplicitParam(name = "title",          value = "职称", dataType = "string", paramType = "query"),
        @ApiImplicitParam(name = "technicalField", value = "技术领域", dataType = "string", paramType = "query"),
        
        
        
    })
    @RequestMapping(value = "/expert-api/query", method = RequestMethod.POST)
	public String queryExpertPage(
			
			@RequestParam(required = true) Integer page,
            @RequestParam(required = true) Integer limit,
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String belongUnit,
            @RequestParam(required = false) String useStatus,
            @RequestParam(required = false) String post,
            @RequestParam(required = false) String title,
            @RequestParam(required = false) String technicalField,
			HttpServletRequest request, HttpServletResponse response)throws Exception 
     {

    	LayuiTableParam param =new LayuiTableParam();
    	param.getParam().put("name", name);
    	param.getParam().put("delStatus", Constant.DEL_STATUS_NOT);
    	param.setLimit(limit);
    	param.setPage(page);
    	
    	param.getParam().put("belongUnit", belongUnit);
    	param.getParam().put("useStatus", useStatus);
    	param.getParam().put("post", post);
    	param.getParam().put("title", title);
    	param.getParam().put("technicalField", technicalField);
    	
		LayuiTableData layuiTableData = new LayuiTableData();
		HttpEntity<LayuiTableParam> entity = new HttpEntity<LayuiTableParam>(param, httpHeaders);
		ResponseEntity<LayuiTableData> responseEntity = restTemplate.exchange(PAGE_EXPERT_URL, HttpMethod.POST, entity, LayuiTableData.class);
		int statusCode = responseEntity.getStatusCodeValue();
		if (statusCode == 200) {
			layuiTableData = responseEntity.getBody();
		}
		JSONObject result = JSONObject.parseObject(JSONObject.toJSONString(layuiTableData));
		logger.info("============获取专家列表（分页） " + result.toString());
		return result.toString();
	}

    
    /**
	  * 删除专家
	 */
    @ApiOperation(value = "根据ID删除专家信息", notes = "根据ID删除专家信息")
	@RequestMapping(value = "/expert-api/delete/{id}", method = RequestMethod.GET)
	public String deleteExpert(@PathVariable("id") String id, HttpServletRequest request, HttpServletResponse response) throws Exception {
    	RestMessage resultsDate = new RestMessage();
		ResponseEntity<Integer> responseEntity = this.restTemplate.exchange(DEL_EXPERT_LOGIC_URL + id, HttpMethod.POST, new HttpEntity<Object>(this.httpHeaders), Integer.class);
		int statusCode = responseEntity.getStatusCodeValue();
		int status = responseEntity.getBody();
		logger.info("============远程返回  statusCode " + statusCode + "  status=" + status);
		if (statusCode == 200) {
			resultsDate = new RestMessage(true,RequestProcessStatusEnum.OK.getStatusDesc());
		} else {
			resultsDate = new RestMessage(false, "删除失败");
		}
		response.setContentType("text/html;charset=UTF-8");
		JSONObject ob = JSONObject.parseObject(JSONObject.toJSONString(resultsDate));
		return ob.toString();
	}
    
    
    /**
	  *根据ID获取专家信息详情
	 */
    @ApiOperation(value = "根据ID获取专家信息详情", notes = "根据ID获取专家信息详情")
	@RequestMapping(value = "/expert-api/get/{id}", method = RequestMethod.GET)
	public String getExpert(@PathVariable("id") String id, HttpServletRequest request, HttpServletResponse response) throws Exception {
    	Result resultsDate = new Result();
    	ResponseEntity<ZjkBase> responseEntity = this.restTemplate.exchange(GET_EXPERT_URL + id, HttpMethod.GET, new HttpEntity<Object>(this.httpHeaders), ZjkBase.class);
		int statusCode = responseEntity.getStatusCodeValue();
		ZjkBase zjkBase = responseEntity.getBody();
		logger.info("============远程返回  statusCode " + statusCode);
		if (statusCode == 200) {
			resultsDate = new Result(true,RequestProcessStatusEnum.OK.getStatusDesc());
			resultsDate.setData(zjkBase);
		} else {
			resultsDate = new Result(false, "根据ID获取专家信息详情失败");
		}
		JSONObject result = JSONObject.parseObject(JSONObject.toJSONString(resultsDate));
		return result.toString();
	}
    
    
    
  
    
    

    
    @ApiOperation(value = "保存、修改专家信息", notes = "保存、修改专家信息")
    @ApiImplicitParams({
    	@ApiImplicitParam(name = "id", value = "主键", dataType = "string", paramType = "form"),
        @ApiImplicitParam(name = "sex", value = "性别", dataType = "string", paramType = "form",required=true),
        @ApiImplicitParam(name = "name", value = "姓名", dataType = "string", paramType = "form",required=true),
        @ApiImplicitParam(name = "useStatus", value = "启用状态（1启用，0未启用）", dataType = "string", paramType = "form"),
        @ApiImplicitParam(name = "age", value = "年龄", dataType = "string", paramType = "form",required=true),
        @ApiImplicitParam(name = "idCardNo", value = "身份证号码", dataType = "string", paramType = "form",required=true),
        @ApiImplicitParam(name = "education", value = "学历", dataType = "string", paramType = "form"),
        @ApiImplicitParam(name = "technicalField", value = "技术领域", dataType = "string", paramType = "form"),
        @ApiImplicitParam(name = "belongUnit", value = "所在单位", dataType = "string", paramType = "form",required=true),
        @ApiImplicitParam(name = "title", value = "职称", dataType = "string", paramType = "form"),
        @ApiImplicitParam(name = "post", value = "职务", dataType = "string", paramType = "form"),
        @ApiImplicitParam(name = "workExperience", value = "工作经历", dataType = "string", paramType = "form"),
        @ApiImplicitParam(name = "contactWay", value = "联系方式", dataType = "string", paramType = "form"),
        @ApiImplicitParam(name = "email", value = "邮箱", dataType = "string", paramType = "form"),
        @ApiImplicitParam(name = "brief", value = "人物简介", dataType = "string", paramType = "form"),
        @ApiImplicitParam(name = "achievement", value = "人物成就", dataType = "string", paramType = "form"),
        @ApiImplicitParam(name = "zjkAchievementJsonList", value = "相关成果信息", dataType = "string", paramType = "form"),
        @ApiImplicitParam(name = "zjkPatentJsonList", value = "相关专利信息", dataType = "string", paramType = "form"),
        @ApiImplicitParam(name = "zjkProjectJsonList", value = "相关项目信息", dataType = "string", paramType = "form"),
        @ApiImplicitParam(name = "zjkRewardJsonList", value = "相关奖励信息", dataType = "string", paramType = "form")
        
    })
    @RequestMapping(method = RequestMethod.POST, value = "/expert-api/save")
	public String saveExpert(@RequestBody  ZjkBase zjkBase,HttpServletRequest request, HttpServletResponse response) throws Exception {

    	Result resultsDate = new Result();
    	String id=zjkBase.getId();
    	
    	JSONObject parma = JSONObject.parseObject(JSONObject.toJSONString(zjkBase));
		System.out.println(">>>>>>>>>> 参数: "+parma.toJSONString());
    
		if (id!=null && !id.equals("")) {
			
			ResponseEntity<ZjkBase> se = this.restTemplate.exchange(GET_EXPERT_URL + id, HttpMethod.GET, new HttpEntity<Object>(this.httpHeaders), ZjkBase.class);
			ZjkBase oldZjkBase = se.getBody();
			oldZjkBase.setAge(zjkBase.getAge());
			oldZjkBase.setAchievement(zjkBase.getAchievement());
			oldZjkBase.setBelongUnit(zjkBase.getBelongUnit());
			oldZjkBase.setBrief(zjkBase.getBrief());
			oldZjkBase.setContactWay(zjkBase.getContactWay());
			oldZjkBase.setEducation(zjkBase.getEducation());
			oldZjkBase.setEmail(zjkBase.getEmail());
			oldZjkBase.setHeadPic(zjkBase.getHeadPic());
			oldZjkBase.setNum(zjkBase.getNum());
			oldZjkBase.setWorkExperience(zjkBase.getWorkExperience());
			oldZjkBase.setTechnicalField(zjkBase.getTechnicalField());
			oldZjkBase.setTitle(zjkBase.getTitle());
			oldZjkBase.setSex(zjkBase.getSex());
			oldZjkBase.setPost(zjkBase.getPost());
			oldZjkBase.setPersonnelNum(zjkBase.getPersonnelNum());
			oldZjkBase.setUseStatus(zjkBase.getUseStatus());
			
			ResponseEntity<Integer> responseEntity = this.restTemplate.exchange(UPDATE_EXPERT_URL, HttpMethod.POST, new HttpEntity<ZjkBase>(oldZjkBase, this.httpHeaders), Integer.class);
			int statusCode = responseEntity.getStatusCodeValue();
			Integer dataId = responseEntity.getBody();
			// 返回结果代码
			if (statusCode == 200) {
				resultsDate = new Result(true, RequestProcessStatusEnum.OK.getStatusDesc());
			} else {
				resultsDate = new Result(false, RequestProcessStatusEnum.SERVER_BUSY.getStatusDesc());
			}
			
			
		} else {
			
			zjkBase.setCreateTime(new Date());
			zjkBase.setDelStatus(Constant.DEL_STATUS_NOT);
			zjkBase.setSourceType(Constant.SOURCE_TYPE_LOCATION);//数据来源（1本系统，2外系统）
			String dateid = UUID.randomUUID().toString().replaceAll("-", "");
			zjkBase.setId(dateid);
			zjkBase.setCreateUser(sysUserInfo.getUserId());
			zjkBase.setNum(UUID.randomUUID().toString().replaceAll("-", ""));//专家编号-自动生成
			zjkBase.setPersonnelNum(UUID.randomUUID().toString().replaceAll("-", ""));//人事系统编号-自动生成
			
			ResponseEntity<String> responseEntity = this.restTemplate.exchange(ADD_EXPERT_URL, HttpMethod.POST, new HttpEntity<ZjkBase>(zjkBase, this.httpHeaders), String.class);
			int statusCode = responseEntity.getStatusCodeValue();
			String dataId = responseEntity.getBody();
			// 返回结果代码
			if (statusCode == 200) {
				resultsDate = new Result(true,RequestProcessStatusEnum.OK.getStatusDesc());
			} else {
				resultsDate = new Result(false, "保存专家信息失败");
			}
			
		}
    	
		
		
	
		JSONObject result = JSONObject.parseObject(JSONObject.toJSONString(resultsDate));
		return result.toString();
    }
    
    
	/*
	 * @ApiOperation(value = "修改专家信息", notes = "修改专家信息")
	 * 
	 * @RequestMapping(method = RequestMethod.POST, value = "/expert-api/update")
	 * public String updateExpert(HttpServletRequest request, HttpServletResponse
	 * response) throws Exception {
	 * 
	 * 
	 * Result resultsDate = new Result(); String id =
	 * CommonUtil.getParameter(request, "id", ""); String name =
	 * CommonUtil.getParameter(request, "name", ""); //根据ID获取详情
	 * ResponseEntity<ZjkBase> responseEntity =
	 * this.restTemplate.exchange(GET_EXPERT_URL + id, HttpMethod.GET, new
	 * HttpEntity<Object>(this.httpHeaders), ZjkBase.class); int statusCode =
	 * responseEntity.getStatusCodeValue(); if (statusCode == 200) { ZjkBase zjkBase
	 * = responseEntity.getBody(); //修改相应信息 zjkBase.setName(name);
	 * ResponseEntity<Integer> response_entity =
	 * this.restTemplate.exchange(UPDATE_EXPERT_URL, HttpMethod.POST, new
	 * HttpEntity<ZjkBase>(zjkBase, this.httpHeaders), Integer.class); int
	 * status_code = response_entity.getStatusCodeValue(); Integer dataId =
	 * response_entity.getBody(); // 返回结果代码 if (status_code == 200) { resultsDate =
	 * new Result(true, RequestProcessStatusEnum.OK.getStatusDesc()); } else {
	 * resultsDate = new Result(false, "修改专家信息失败"); } } JSONObject result =
	 * JSONObject.parseObject(JSONObject.toJSONString(resultsDate)); return
	 * result.toString(); }
	 */
    
    
    

}
