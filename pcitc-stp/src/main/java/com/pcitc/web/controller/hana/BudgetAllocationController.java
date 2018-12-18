package com.pcitc.web.controller.hana;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.pcitc.base.common.LayuiTableParam;
import com.pcitc.base.common.PageResult;
import com.pcitc.base.util.CommonUtil;
import com.pcitc.base.util.DateUtil;
import com.pcitc.web.utils.HanaUtil;

//预算分配
@Controller
public class BudgetAllocationController {
	@Autowired
	private HttpHeaders httpHeaders;
	
	@Autowired
	private RestTemplate restTemplate;
	
	
	private static final String jtgszbkjjfys_data = "http://pcitc-zuul/system-proxy/out-project-provider/budget-proposals/group-company/stp-money";
	private static final String gfgszbkjjfysjyg_data = "http://pcitc-zuul/system-proxy/out-decision-provider/ysfp/jfysfx/stock-company-money";
	//private static final String cbmkjjfyszb_data = "http://pcitc-zuul/system-proxy/out-decision-provider/ysfp/jfysfx/department-budget";
	private static final String cbmkjjfyszb_data = "http://pcitc-zuul/system-proxy/out-decision-provider/budget-proposals/department/stp-money";
	private static final String jtjfysmxb_data = "http://pcitc-zuul/system-proxy/out-decision-provider/budget-proposals/group/stp-money";
	private static final String zcjfysmxb_data = "http://pcitc-zuul/system-proxy/out-decision-provider/budget-proposals/asset/stp-money";
	private static final String gfzsyjfysmxb_data = "http://pcitc-zuul/system-proxy/out-decision-provider/budget-proposals/institute/stp-money";
	private static final String gfxtwjjtjfysmxb_data = "http://pcitc-zuul/system-proxy/out-decision-provider/budget-proposals/other/stp-money";
	private static final String gffzgsys_data = "http://pcitc-zuul/system-proxy/out-decision-provider/budget-proposals/company/stp-money";
	private static final String gflysybhgsybB2Clkjjfysb_data = "http://pcitc-zuul/system-proxy/out-decision-provider/budget-proposals/b2c/stp-money";
	private static final String gfgskjzxjfysb_data = "http://pcitc-zuul/system-proxy/out-decision-provider/budget-proposals/tech/stp-money";

	
	
	
	
	  
	    //集团公司总部科技经费预算----（建议稿）
	    @RequestMapping(method = RequestMethod.POST, value = "/ba/jtgszbkjjfys_data")
		@ResponseBody
		public String jtgszbkjjfys_data(@ModelAttribute("param") LayuiTableParam param, HttpServletRequest request, HttpServletResponse response) {

	    	PageResult pageResult = new PageResult();
	    	String month = CommonUtil.getParameter(request, "month", "" + DateUtil.dateToStr(new Date(), DateUtil.FMT_MM));
			String companyCode = CommonUtil.getParameter(request, "companyCode", "");
			Map<String, Object> paramsMap = new HashMap<String, Object>();
			paramsMap.put("month", month);
			paramsMap.put("companyCode", companyCode);
			JSONObject jsonObject = JSONObject.parseObject(JSONObject.toJSONString(paramsMap));
			HttpEntity<String> entity = new HttpEntity<String>(jsonObject.toString(), httpHeaders);
			
			ResponseEntity<JSONArray> responseEntity = restTemplate.exchange(jtgszbkjjfys_data, HttpMethod.POST, entity, JSONArray.class);
			int statusCode = responseEntity.getStatusCodeValue();
			if (statusCode == 200)
			{
				
				JSONArray	jSONArray = responseEntity.getBody();
				pageResult.setData(jSONArray);
				pageResult.setCode(0);
				pageResult.setCount(Long.valueOf(jSONArray.size()));
				pageResult.setLimit(1000);
				pageResult.setPage(1l);
				
			}
			JSONObject result = JSONObject.parseObject(JSONObject.toJSONString(pageResult));
			System.out.println(">>>>>>>>>>>>>jtgszbkjjfys_data:" + result.toString());
			return result.toString();
		}
	  
	  //集团公司总部科技经费预算（建议稿）
	  @RequestMapping(method = RequestMethod.GET, value = "/ba/jtgszbkjjfys")
	  public String jtgszbkjjfys(HttpServletRequest request) throws Exception
	  {
		    
		  
		  String year= HanaUtil.getCurrrentYear();
		    request.setAttribute("year", year);
	        return "stp/hana/budgetAllocation/jtgszbkjjfys";
	  }
	  
	  
	  
	//资产公司总部科技项目经费预算（建议稿）
	  @RequestMapping(method = RequestMethod.GET, value = "/ba/zcgszbkjxmjfys")
	  public String zcgszbkjxmjfys(HttpServletRequest request) throws Exception
	  {
		  String month = HanaUtil.getCurrrentYear();
			request.setAttribute("year", month);
	        return "stp/hana/budgetAllocation/zcgszbkjxmjfys";
	  }
	//股份公司总部科技经费预算（调整稿）
	  @RequestMapping(method = RequestMethod.GET, value = "/ba/gfgszbkjjfystzg")
	  public String gfgszbkjjfystzg(HttpServletRequest request) throws Exception
	  {
		  String month = HanaUtil.getCurrrentYear();
			request.setAttribute("month", month);
	        return "stp/hana/budgetAllocation/gfgszbkjjfystzg";
	  }
	  //股份公司总部科技经费预算（建议稿）
	  @RequestMapping(method = RequestMethod.GET, value = "/ba/gfgszbkjjfysjyg")
	  public String gfgszbkjjfysjyg(HttpServletRequest request) throws Exception
	  {
		  String month = HanaUtil.getCurrrentYear();
			request.setAttribute("year", month);
	        return "stp/hana/budgetAllocation/gfgszbkjjfysjyg";
	  }

	  // 股份公司总部科技经费预算----（建议稿）
	  @RequestMapping(method = RequestMethod.GET, value = "/ba/gfgszbkjjfysjyg_data")
	  @ResponseBody
	  public String gfgszbkjjfysjyg_data(@ModelAttribute("param") LayuiTableParam param, HttpServletRequest request,
			HttpServletResponse response) {
		  
		  	String month = CommonUtil.getParameter(request, "month", "" + DateUtil.dateToStr(new Date(), DateUtil.FMT_MM));
			String companyCode = CommonUtil.getParameter(request, "companyCode", "");
			Map<String, Object> paramsMap = new HashMap<String, Object>();
			paramsMap.put("month", month);
			paramsMap.put("companyCode", companyCode);
			
			String result = getTableDataNotPagin(paramsMap,gfgszbkjjfysjyg_data);
			System.out.println(">>>>>>>>>>>>>股份公司总部科技经费预算gfgszbkjjfysjyg_data:" + result);
			return result;
			/*PageResult pageResult = new PageResult();
			String month = CommonUtil.getParameter(request, "month", "" + DateUtil.dateToStr(new Date(), DateUtil.FMT_MM));
			String companyCode = CommonUtil.getParameter(request, "companyCode", "");
			Map<String, Object> paramsMap = new HashMap<String, Object>();
			paramsMap.put("month", month);
			paramsMap.put("companyCode", companyCode);
			JSONObject jsonObject = JSONObject.parseObject(JSONObject.toJSONString(paramsMap));
			HttpEntity<String> entity = new HttpEntity<String>(jsonObject.toString(), httpHeaders);
	
			ResponseEntity<JSONArray> responseEntity = restTemplate.exchange(gfgszbkjjfysjyg_data, HttpMethod.POST, entity,
					JSONArray.class);
			int statusCode = responseEntity.getStatusCodeValue();
			if (statusCode == 200) {
	
				JSONArray jSONArray = responseEntity.getBody();
				pageResult.setData(jSONArray);
				pageResult.setCode(0);
				pageResult.setCount(Long.valueOf(jSONArray.size()));
				pageResult.setLimit(1000);
				pageResult.setPage(1l);
	
			}
			JSONObject result = JSONObject.parseObject(JSONObject.toJSONString(pageResult));
			System.out.println(">>>>>>>>>>>>>股份公司总部科技经费预算gfgszbkjjfysjyg_data:" + result.toString());
			return result.toString();*/
	  }

	//处部门科技经费预算总表
	  @RequestMapping(method = RequestMethod.GET, value = "/ba/cbmkjjfyszb")
	  public String cbmkjjfyszb(HttpServletRequest request) throws Exception
	  {
		  
		  
			String month = HanaUtil.getCurrrentYear();
			request.setAttribute("year", month);
		    
	        return "stp/hana/budgetAllocation/cbmkjjfyszb";
	  }
	  
	  
	  @RequestMapping(method = RequestMethod.GET, value = "/ba/cbmkjjfyszb_data")
		@ResponseBody
		public String cbmkjjfyszb_data(@ModelAttribute("param") LayuiTableParam param, HttpServletRequest request, HttpServletResponse response) {

	    	PageResult pageResult = new PageResult();
	    	String month = CommonUtil.getParameter(request, "month", "" + DateUtil.dateToStr(new Date(), DateUtil.FMT_MM));
			String companyCode = CommonUtil.getParameter(request, "companyCode", "");
			Map<String, Object> paramsMap = new HashMap<String, Object>();
			paramsMap.put("month", month);
			paramsMap.put("companyCode", companyCode);
			JSONObject jsonObject = JSONObject.parseObject(JSONObject.toJSONString(paramsMap));
			HttpEntity<String> entity = new HttpEntity<String>(jsonObject.toString(), httpHeaders);
			
			ResponseEntity<JSONArray> responseEntity = restTemplate.exchange(cbmkjjfyszb_data, HttpMethod.POST, entity, JSONArray.class);
			int statusCode = responseEntity.getStatusCodeValue();
			if (statusCode == 200)
			{
				
				JSONArray	jSONArray = responseEntity.getBody();
				pageResult.setData(jSONArray);
				pageResult.setCode(0);
				pageResult.setCount(Long.valueOf(jSONArray.size()));
				pageResult.setLimit(1000);
				pageResult.setPage(1l);
				
			}
			JSONObject result = JSONObject.parseObject(JSONObject.toJSONString(pageResult));
			System.out.println(">>>>>>>>>>>>>处部门科技经费预算总表 gfgszbkjjfysjyg_data:" + result.toString());
			return result.toString();
		}
	  
	  
	  //集团经费预算明细表
	  @RequestMapping(method = RequestMethod.GET, value = "/ba/jtjfysmxb")
	  public String jtjfysmxb(HttpServletRequest request) throws Exception
	  {
		    
	        return "stp/hana/budgetAllocation/jtjfysmxb";
	  }
	  //集团经费预算明细表
	  @RequestMapping(method = RequestMethod.GET, value = "/ba/jtjfysmxb_data")
	  @ResponseBody
	  public String jtjfysmxbData(HttpServletRequest request) throws Exception
	  {
		    
		String month = CommonUtil.getParameter(request, "month", "" + DateUtil.dateToStr(new Date(), DateUtil.FMT_MM));
		String companyCode = CommonUtil.getParameter(request, "companyCode", "");
		Map<String, Object> paramsMap = new HashMap<String, Object>();
		paramsMap.put("month", month);
		paramsMap.put("companyCode", companyCode);
		
		String result = getTableDataNotPagin(paramsMap,jtjfysmxb_data);
		System.out.println(">>>>>>>>>>>>>集团经费预算明细表jtjfysmxb_data:" + result);
		return result;
	  }
	  //资产经费预算明细表
	  @RequestMapping(method = RequestMethod.GET, value = "/ba/zcjfysmxb")
	  public String zcjfysmxb(HttpServletRequest request) throws Exception
	  {
		    
	        return "stp/hana/budgetAllocation/zcjfysmxb";
	  }
	  //资产经费预算明细表
	  @RequestMapping(method = RequestMethod.GET, value = "/ba/zcjfysmxb_data")
	  @ResponseBody
	  public String zcjfysmxbData(HttpServletRequest request) throws Exception
	  {
		String month = CommonUtil.getParameter(request, "month", "" + DateUtil.dateToStr(new Date(), DateUtil.FMT_MM));
		String companyCode = CommonUtil.getParameter(request, "companyCode", "");
		Map<String, Object> paramsMap = new HashMap<String, Object>();
		paramsMap.put("month", month);
		paramsMap.put("companyCode", companyCode);
		
		String result = getTableDataNotPagin(paramsMap,zcjfysmxb_data);
		System.out.println(">>>>>>>>>>>>>资产经费预算明细表zcjfysmxb_data:" + result);
		return result;
	  }
	  //股份（直属院）经费预算明细表
	  @RequestMapping(method = RequestMethod.GET, value = "/ba/gfzsyjfysmxb")
	  public String gfzsyjfysmxb(HttpServletRequest request) throws Exception
	  {
		    
	        return "stp/hana/budgetAllocation/gfzsyjfysmxb";
	  }
	  //股份（直属院）经费预算明细表
	  @RequestMapping(method = RequestMethod.GET, value = "/ba/gfzsyjfysmxb_data")
	  @ResponseBody
	  public String gfzsyjfysmxbData(HttpServletRequest request) throws Exception
	  {
		    
		  	String month = CommonUtil.getParameter(request, "month", "" + DateUtil.dateToStr(new Date(), DateUtil.FMT_MM));
			String companyCode = CommonUtil.getParameter(request, "companyCode", "");
			Map<String, Object> paramsMap = new HashMap<String, Object>();
			paramsMap.put("month", month);
			paramsMap.put("companyCode", companyCode);
			
			String result = getTableDataNotPagin(paramsMap,gfzsyjfysmxb_data);
			System.out.println(">>>>>>>>>>>>>股份（直属院）经费预算明细表gfzsyjfysmxb_data:" + result);
			return result;
	  }
	  //股份（系统外及集团）经费预算明细表
	  @RequestMapping(method = RequestMethod.GET, value = "/ba/gfxtwjjtjfysmxb")
	  public String gfxtwjjtjfysmxb(HttpServletRequest request) throws Exception
	  {
		    
	        return "stp/hana/budgetAllocation/gfxtwjjtjfysmxb";
	  }
	  //股份（系统外及集团）经费预算明细表
	  @RequestMapping(method = RequestMethod.GET, value = "/ba/gfxtwjjtjfysmxb_data")
	  @ResponseBody
	  public String gfxtwjjtjfysmxbData(HttpServletRequest request) throws Exception
	  {
		    
		  String month = CommonUtil.getParameter(request, "month", "" + DateUtil.dateToStr(new Date(), DateUtil.FMT_MM));
			String companyCode = CommonUtil.getParameter(request, "companyCode", "");
			Map<String, Object> paramsMap = new HashMap<String, Object>();
			paramsMap.put("month", month);
			paramsMap.put("companyCode", companyCode);
			
			String result = getTableDataNotPagin(paramsMap,gfxtwjjtjfysmxb_data);
			System.out.println(">>>>>>>>>>>>>股份（直属院）经费预算明细表gfzsyjfysmxb_data:" + result);
			return result;
	  }
	  //股份（分子公司）预算
	  @RequestMapping(method = RequestMethod.GET, value = "/ba/gffzgsys")
	  public String gffzgsys(HttpServletRequest request) throws Exception
	  {
		    
	        return "stp/hana/budgetAllocation/gffzgsys";
	  }
	  //股份（分子公司）预算
	  @RequestMapping(method = RequestMethod.GET, value = "/ba/gffzgsys_data")
	  @ResponseBody
	  public String gffzgsysData(HttpServletRequest request) throws Exception
	  {
		    
		  	String month = CommonUtil.getParameter(request, "month", "" + DateUtil.dateToStr(new Date(), DateUtil.FMT_MM));
			String companyCode = CommonUtil.getParameter(request, "companyCode", "");
			Map<String, Object> paramsMap = new HashMap<String, Object>();
			paramsMap.put("month", month);
			paramsMap.put("companyCode", companyCode);
			
			String result = getTableDataNotPagin(paramsMap,gffzgsys_data);
			System.out.println(">>>>>>>>>>>>>股份（分子公司）预算gffzgsys_data:" + result);
			return result;
	  }
	  //股份炼油事业部、化工事业部B2、C类科技经费预算表
	  @RequestMapping(method = RequestMethod.GET, value = "/ba/gflysybhgsybB2Clkjjfysb")
	  public String gflysybhgsybB2Clkjjfysb(HttpServletRequest request) throws Exception
	  {
		    
	        return "stp/hana/budgetAllocation/gflysybhgsybB2Clkjjfysb";
	  }
	  //股份炼油事业部、化工事业部B2、C类科技经费预算表
	  @RequestMapping(method = RequestMethod.GET, value = "/ba/gflysybhgsybB2Clkjjfysb_data")
	  @ResponseBody
	  public String gflysybhgsybB2ClkjjfysbData(HttpServletRequest request) throws Exception
	  {
		    
			String month = CommonUtil.getParameter(request, "month", "" + DateUtil.dateToStr(new Date(), DateUtil.FMT_MM));
			String companyCode = CommonUtil.getParameter(request, "companyCode", "");
			Map<String, Object> paramsMap = new HashMap<String, Object>();
			paramsMap.put("month", month);
			paramsMap.put("companyCode", companyCode);
			
			String result = getTableDataNotPagin(paramsMap,gflysybhgsybB2Clkjjfysb_data);
			System.out.println(">>>>>>>>>>>>>股份炼油事业部、化工事业部B2、C类科技经费预算表gflysybhgsybB2Clkjjfysb_data:" + result);
			return result;
	  }
	  //股份公司科技专项经费预算表
	  @RequestMapping(method = RequestMethod.GET, value = "/ba/gfgskjzxjfysb")
	  public String gfgskjzxjfysb(HttpServletRequest request) throws Exception
	  {
		    
	        return "stp/hana/budgetAllocation/gfgskjzxjfysb";
	  }
	  //股份公司科技专项经费预算表
	  @RequestMapping(method = RequestMethod.GET, value = "/ba/gfgskjzxjfysb_data")
	  @ResponseBody
	  public String gfgskjzxjfysbData(HttpServletRequest request) throws Exception
	  {
		  String month = CommonUtil.getParameter(request, "month", "" + DateUtil.dateToStr(new Date(), DateUtil.FMT_MM));
			String companyCode = CommonUtil.getParameter(request, "companyCode", "");
			Map<String, Object> paramsMap = new HashMap<String, Object>();
			paramsMap.put("month", month);
			paramsMap.put("companyCode", companyCode);
			
			String result = getTableDataNotPagin(paramsMap,gfgskjzxjfysb_data);
			System.out.println(">>>>>>>>>>>>>股份公司科技专项经费预算表gfgskjzxjfysb_data:" + result);
			return result;
	  }
	  //获取二维表格数据（不分页）
	  private String getTableDataNotPagin(Map<String, Object> paramsMap,String data_url) 
	  {
		  	PageResult pageResult = new PageResult();
			JSONObject jsonObject = JSONObject.parseObject(JSONObject.toJSONString(paramsMap));
			HttpEntity<String> entity = new HttpEntity<String>(jsonObject.toString(), httpHeaders);
			
			ResponseEntity<JSONArray> responseEntity = restTemplate.exchange(data_url, HttpMethod.POST, entity, JSONArray.class);
			int statusCode = responseEntity.getStatusCodeValue();
			if (statusCode == 200)
			{
				
				JSONArray	jSONArray = responseEntity.getBody();
				pageResult.setData(jSONArray);
				pageResult.setCode(0);
				pageResult.setCount(Long.valueOf(jSONArray.size()));
				pageResult.setLimit(1000);
				pageResult.setPage(1l);
				
			}
			JSONObject result = JSONObject.parseObject(JSONObject.toJSONString(pageResult));
			System.out.println("load data>>>>>>>>>>>>>:" + result.toString());
			return result.toString();
	  }
}
