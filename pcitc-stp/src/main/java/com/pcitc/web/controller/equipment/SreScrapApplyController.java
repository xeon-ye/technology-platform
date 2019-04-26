package com.pcitc.web.controller.equipment;

import java.io.PrintWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.pcitc.base.common.LayuiTableData;
import com.pcitc.base.common.LayuiTableParam;
import com.pcitc.base.common.Result;
import com.pcitc.base.stp.equipment.FindAppltid;
import com.pcitc.base.stp.equipment.FindView;
import com.pcitc.base.stp.equipment.SreEquipment;
import com.pcitc.base.stp.equipment.SrePurchase;
import com.pcitc.base.stp.equipment.SreScrapApply;
import com.pcitc.base.stp.equipment.sre_scrap_apply_item;
import com.pcitc.base.util.CommonUtil;
import com.pcitc.base.util.IdUtil;
import com.pcitc.web.common.BaseController;
import com.pcitc.web.utils.EquipmentUtils;

@Controller
public class SreScrapApplyController extends BaseController {
	private static final String PAGE_URL = "http://pcitc-zuul/stp-proxy/sre-provider/sreScrapApply/page";
	private static final String INSERT_URL="http://pcitc-zuul/stp-proxy/sre-provider/sreScrapApply/addApplyAndItem";
	private static final String GETVIEW_URL="http://pcitc-zuul/stp-proxy/sre-provider/sreScrapApply/getapplybyid/";
	private static final String GETLIST_URL="http://pcitc-zuul/stp-proxy/sre-provider/sreScrapApply/selectByAppltidList/";
	private static final String INVALID_URL="http://pcitc-zuul/stp-proxy/sre-provider/sreScrapApply/submitInvalid/";
	   private static final String PURCHASE_INNER_WORKFLOW_URL = "http://pcitc-zuul/stp-proxy/stp-provider/sreScrapApply/start_inner_activity/";
	@RequestMapping(value = "/sre-sreScrapApply/list")
	@ResponseBody
	public String ajaxlist(@ModelAttribute("param") LayuiTableParam param, HttpServletRequest request, HttpServletResponse response) {
		
		LayuiTableData layuiTableData = new LayuiTableData();
		HttpEntity<LayuiTableParam> entity = new HttpEntity<LayuiTableParam>(param, httpHeaders);
		ResponseEntity<LayuiTableData> responseEntity = restTemplate.exchange(PAGE_URL, HttpMethod.POST, entity, LayuiTableData.class);
		int statusCode = responseEntity.getStatusCodeValue();
		if (statusCode == 200) {
			layuiTableData = responseEntity.getBody();
		}
		JSONObject result = JSONObject.parseObject(JSONObject.toJSONString(layuiTableData));
		logger.info("============result" + result);
		return result.toString();
	}


	@RequestMapping(value = "/sre-sreScrapApply/to-list")
	public String list(HttpServletRequest request, HttpServletResponse response) {

		String	parentUnitPathIds="";
		String unitPathIds =   sysUserInfo.getUnitPath();
		if(!unitPathIds.equals(""))
		{
			if(unitPathIds.length()>4)
			{
				parentUnitPathIds=unitPathIds.substring(0, unitPathIds.length()-4);
				
			}
		}
		request.setAttribute("parentUnitPathIds", parentUnitPathIds);
		return "/stp/equipment/scrapapply/scrapapply_list";
	}
    	//启动采购管理-采购申请 确认流程
	    @RequestMapping(value = "/sre-sreScrapApply/start_purchase_workflow")
	    @ResponseBody
	    public Object start_purchase_workflow(HttpServletRequest request, HttpServletResponse response) throws Exception
	    {
	        this.httpHeaders.setContentType(MediaType.APPLICATION_JSON_UTF8);//设置参数类型和编码
	        String id = CommonUtil.getParameter(request, "id", "");
	        String functionId = CommonUtil.getParameter(request, "functionId", "");
	        String userIds = CommonUtil.getParameter(request, "userIds", "");
	        System.out.println("============start_purchase_workflow userIds="+userIds+" functionId="+functionId+" id="+id);

	    	SreScrapApply sreScrapApply=restTemplate.exchange(GETVIEW_URL + id, HttpMethod.GET,new HttpEntity<Object>(this.httpHeaders), SreScrapApply.class).getBody();

	        Map<String ,Object> paramMap = new HashMap<String ,Object>();
	        paramMap.put("id", id);
	        paramMap.put("functionId", functionId);
	        paramMap.put("processInstanceName", "报废申请确认->"+sreScrapApply.getName());
	        paramMap.put("authenticatedUserId", sysUserInfo.getUserId());
	        paramMap.put("authenticatedUserName", sysUserInfo.getUserDisp());
	        paramMap.put("auditor", userIds);
	        //申请者机构信息
	        paramMap.put("applyUnitCode", sysUserInfo.getUnitCode());
	        String parentApplyUnitCode=EquipmentUtils.getUnitParentCodesByUnitCodes(sysUserInfo.getUnitCode(), restTemplate, httpHeaders);
	        paramMap.put("parentApplyUnitCode", parentApplyUnitCode);


	     HttpEntity<Map<String, Object>> httpEntity = new HttpEntity<Map<String, Object>>(paramMap,this.httpHeaders);
	        //return null;
	     Result rs = this.restTemplate.exchange(PURCHASE_INNER_WORKFLOW_URL + id, HttpMethod.POST, httpEntity, Result.class).getBody();
	        return rs;
	    }
	
	/**
	 * 增加
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(method = RequestMethod.GET, value = "/sre-sreScrapApply/add")
	public String add(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String id = CommonUtil.getParameter(request, "id", "");
		request.setAttribute("id",id);
		if(id=="")
		{
			return "/stp/equipment/scrapapply/scrapapply_add";
		}
		else
		{
			System.out.println(id);
			String name="";
			ResponseEntity<SreScrapApply> sreScrapApply=restTemplate.exchange(GETVIEW_URL + id, HttpMethod.GET,new HttpEntity<Object>(this.httpHeaders), SreScrapApply.class);
			
			if(sreScrapApply!=null && sreScrapApply.getBody()!=null)
			{
				name=sreScrapApply.getBody().getName();				
			}
			request.setAttribute("name",name);
			ResponseEntity<List> selectByAppltidList=restTemplate.exchange(GETLIST_URL + id, HttpMethod.GET,new HttpEntity<Object>(this.httpHeaders),List.class);
			List<FindAppltid> list = selectByAppltidList.getBody();
			JSONArray jsonObject = JSONArray.parseArray(JSON.toJSONString(list));
			request.setAttribute("getList",jsonObject.toString());
			return "/stp/equipment/scrapapply/scrapapply_edit";
		}
	}
	
	@RequestMapping(method = RequestMethod.GET, value = "/sre-sreScrapApply/view")
	public String view(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String id = CommonUtil.getParameter(request, "id", "");
		request.setAttribute("id",id);
			System.out.println(id);
			String name="";
			ResponseEntity<SreScrapApply> sreScrapApply=restTemplate.exchange(GETVIEW_URL + id, HttpMethod.GET,new HttpEntity<Object>(this.httpHeaders), SreScrapApply.class);
			if(sreScrapApply!=null && sreScrapApply.getBody()!=null)
			{
				name=sreScrapApply.getBody().getName();				
			}
			request.setAttribute("name",name);
			ResponseEntity<List> selectByAppltidList=restTemplate.exchange(GETLIST_URL + id, HttpMethod.GET,new HttpEntity<Object>(this.httpHeaders),List.class);
			List<FindAppltid> list = selectByAppltidList.getBody();
			JSONArray jsonObject = JSONArray.parseArray(JSON.toJSONString(list));
			request.setAttribute("getList",jsonObject.toString());
			return "/stp/equipment/scrapapply/scrapapply_view";
	}
	
	
	
	@RequestMapping(value = "/sre-sreScrapApply/addApplyAndItem")
	@ResponseBody
	public String addApplyAndItem(HttpServletRequest request)throws Exception{
		ResponseEntity<String> responseEntity = null;
		String name = CommonUtil.getParameter(request, "name", "");
		String ids = CommonUtil.getParameter(request, "ids", "");
		String id=CommonUtil.getParameter(request, "id", "");
		FindView f=new FindView();
		f.setName(name);
		f.setIds(ids);
		f.setUserId(sysUserInfo.getUserId());
		f.setUserName(sysUserInfo.getUserName());
		f.setAddorupdate(id);
		responseEntity =restTemplate.exchange(INSERT_URL, HttpMethod.POST,new HttpEntity<FindView>(f, this.httpHeaders),String.class);
		String success ="{\"success\":\"success\"}";
		return success;
	}
	@RequestMapping(method = RequestMethod.GET,value = "/sre-sreScrapApply/submitInvalid")
	@ResponseBody
	public String submitInvalid(HttpServletRequest request)throws Exception{
		ResponseEntity<String> responseEntity = null;
		String id=CommonUtil.getParameter(request, "id", "");
		responseEntity =restTemplate.exchange(INVALID_URL + id, HttpMethod.GET,new HttpEntity<Object>(this.httpHeaders),String.class);
		String success ="{\"success\":\"success\"}";
		return success;
	}
	
}
