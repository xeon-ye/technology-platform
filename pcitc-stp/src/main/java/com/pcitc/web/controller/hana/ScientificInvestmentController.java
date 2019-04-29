package com.pcitc.web.controller.hana;

import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.time.DateFormatUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.pcitc.base.common.ExcelException;
import com.pcitc.base.common.LayuiTableData;
import com.pcitc.base.common.LayuiTableParam;
import com.pcitc.base.hana.report.ScientificInvestment;
import com.pcitc.base.system.SysUser;
import com.pcitc.base.util.CommonUtil;
import com.pcitc.base.util.DateUtil;
import com.pcitc.web.common.JwtTokenUtil;
import com.pcitc.web.utils.HanaUtil;
import com.pcitc.web.utils.PoiExcelExportUitl;

//科研投资
@Controller
public class ScientificInvestmentController {
	@Autowired
	private HttpHeaders httpHeaders;
	
	@Autowired
	private RestTemplate restTemplate;
	
	private static final String tzxmwcqktjb_data = "http://pcitc-zuul/hana-proxy/hana/scientificInvestment/tzxmwcqktjb";
	
	private static final String tzxmwcqktjb_out_excel = "http://pcitc-zuul/hana-proxy/hana/scientificInvestment/tzxmwcqktjb_out_excel";
	
	
	
	private static final String tzxmcgjdtjb_data = "http://pcitc-zuul/hana-proxy/hana/scientificInvestment/tzxmcgjdtjb";
	
	private static final String tzxmcgjdtjb_out_excel = "http://pcitc-zuul/hana-proxy/hana/scientificInvestment/tzxmcgjdtjb_out_excel";
	
	
	private static final String tzxmzcqkb_data =   "http://pcitc-zuul/hana-proxy/hana/scientificInvestment/tzxmzcqkb";
	
	private static final String tzxmzcqkb_out_excel =   "http://pcitc-zuul/hana-proxy/hana/scientificInvestment/tzxmzcqkb_out_excel";
	
	
		   //投资项目完成情况统计表
		   @RequestMapping(method = RequestMethod.GET, value = "/si/tzxmwcqktjb")
		   public String jtgszbkjjfys(HttpServletRequest request) throws Exception
		   {
			    SysUser userInfo = JwtTokenUtil.getUserFromToken(this.httpHeaders);
			    HanaUtil.setSearchParaForUser2(userInfo,restTemplate,httpHeaders,request);
				String month = HanaUtil.getCurrrent_Year_Moth();
				request.setAttribute("month", month);
		        return "stp/hana/scientificInvestment/tzxmwcqktjb";
		   }
	  
		    // 三级表格
			@RequestMapping(method = RequestMethod.POST, value = "/tzxmwcqktjb_data")
			@ResponseBody
			public String tzxmwcqktjb_data(@ModelAttribute("param") LayuiTableParam param, HttpServletRequest request, HttpServletResponse response)
			{

				String month = CommonUtil.getParameter(request, "month", "" + DateUtil.dateToStr(new Date(), DateUtil.FMT_MM));
				String companyCode = CommonUtil.getParameter(request, "companyCode", HanaUtil.YJY_CODE_NOT_YINGKE);
				System.out.println(">>>>>>>>>>>>>>>>>>>>>参数      month = "+month+" companyCode="+companyCode);
				Map<String, Object> paramsMap = new HashMap<String, Object>();
				paramsMap.put("month", month);
				paramsMap.put("companyCode", companyCode);
				
				LayuiTableData layuiTableData = new LayuiTableData();
				HttpEntity<LayuiTableParam> entity = new HttpEntity<LayuiTableParam>(param, httpHeaders);
				ResponseEntity<LayuiTableData> responseEntity = restTemplate.exchange(tzxmwcqktjb_data, HttpMethod.POST, entity, LayuiTableData.class);
				int statusCode = responseEntity.getStatusCodeValue();
				if (statusCode == 200)
				{
					layuiTableData = responseEntity.getBody();
				}
				JSONObject result = JSONObject.parseObject(JSONObject.toJSONString(layuiTableData));
				System.out.println(">>>>>>>>>>>>>zlsbqkmxfxb_data:" + result.toString());
				return result.toString();
			}
			
			
			
			
			@RequestMapping(method = RequestMethod.GET, value = "/tzxmwcqktjb_exput_excel")
			@ResponseBody
			public String tzxmwcqktjb_exput_excel(HttpServletRequest request, HttpServletResponse response) throws Exception
			{
				
				
				this.httpHeaders.setContentType(MediaType.APPLICATION_JSON_UTF8);//设置参数类型和编码
				String month = CommonUtil.getParameter(request, "month", "" + DateUtil.dateToStr(new Date(), DateUtil.FMT_MM));
				String companyCode = CommonUtil.getParameter(request, "companyCode", HanaUtil.YJY_CODE_NOT_YINGKE);
				Map<String ,Object> paramMap = new HashMap<String ,Object>();
				paramMap.put("month", month);
				paramMap.put("companyCode", companyCode);
				System.out.println(">tzxmwcqktjb_exput_excel>>>>>>>>>>>>>>>>>>>>参数      month = "+month+" companyCode="+companyCode);
				
				HttpEntity<Map<String, Object>> httpEntity = new HttpEntity<Map<String, Object>>(paramMap,this.httpHeaders);
				ResponseEntity<JSONArray> responseEntity = restTemplate.exchange(tzxmwcqktjb_out_excel, HttpMethod.POST, httpEntity, JSONArray.class);
				int statusCode = responseEntity.getStatusCodeValue();
				List<ScientificInvestment> list =new ArrayList();
				JSONArray jSONArray=null;
				if (statusCode == 200)
				{
					jSONArray = responseEntity.getBody();
					
					list = JSONObject.parseArray(jSONArray.toJSONString(), ScientificInvestment.class);
					
					/*for(int i=0;i<list.size();i++)
					{
						ScientificInvestment scientificInvestment=list.get(i);
						System.out.println(">>>>>>>>>>>>>>>>>>>>>参数      getG0GSJC = "+scientificInvestment.getG0GSJC());
					}*/
				}
				
				
				    String[] headers = { "院所", "计划总投资", "累计支出额",       "预付余额",     "累计投资完成额", "项目资金计划结余",     "本年投资计划",         "本年累计支出"   , "本年预付款", "本年投资完成额"};
				    String[] cols =    {"g0GSJC","k0ZTYSJE","k0LJGLFPHJECB","k0LJSJDJJE","aDD1",       "k0LJYSJY",       "k0BNYSJHJE",       "k0BNGLFPHJECB","k0BNSJDJJE","k0BNSJDJJE"};
				   
			        // 文件名默认设置为当前时间：年月日时分秒
			        String fileName = DateFormatUtils.format(new Date(), "yyyyMMddhhmmss");
			        // 设置response头信息
			        response.reset();
			        response.setContentType("application/vnd.ms-excel");
			        response.setHeader("Content-disposition", "attachment;filename=" + fileName + ".xls");
			        try {
				        OutputStream os = response.getOutputStream();
				        PoiExcelExportUitl<ScientificInvestment>  pee = new PoiExcelExportUitl<ScientificInvestment>(fileName, headers, cols, list,os);
				        pee.exportExcel();
			            
			        } catch (Exception e)
			        {
			            e.printStackTrace();
			            // 如果是ExcelException,则直接抛出
			            if (e instanceof ExcelException) 
			            {
			                throw (ExcelException) e;
			            } else 
			            {
			                // 否则将其他异常包装成ExcelException再抛出
			                throw new ExcelException("导出excel失败");
			            }
			        }
				   return null;
			}
			

			
	  
		  //投资项目采购进度统计表
		  @RequestMapping(method = RequestMethod.GET, value = "/si/tzxmcgjdtjb")
		  public String tzxmcgjdtjb(HttpServletRequest request) throws Exception
		  {
			    SysUser userInfo = JwtTokenUtil.getUserFromToken(this.httpHeaders);
				HanaUtil.setSearchParaForUser2(userInfo,restTemplate,httpHeaders,request);
				String month = HanaUtil.getCurrrent_Year_Moth();
				request.setAttribute("month", month);
		        return "stp/hana/scientificInvestment/tzxmcgjdtjb";
		  }
	  
	  
	  
	    @RequestMapping(method = RequestMethod.POST, value = "/tzxmcgjdtjb_data")
		@ResponseBody
		public String tzxmcgjdtjb_data(@ModelAttribute("param") LayuiTableParam param, HttpServletRequest request, HttpServletResponse response)
		{

	    	String month = CommonUtil.getParameter(request, "month", "" + DateUtil.dateToStr(new Date(), DateUtil.FMT_MM));
			String companyCode = CommonUtil.getParameter(request, "companyCode", HanaUtil.YJY_CODE_NOT_YINGKE);
			System.out.println(">>>>>>>>>>>>>>>>>>>>>参数      month = "+month+" companyCode="+companyCode);
			Map<String, Object> paramsMap = new HashMap<String, Object>();
			paramsMap.put("month", month);
			paramsMap.put("companyCode", companyCode);
			LayuiTableData layuiTableData = new LayuiTableData();
			HttpEntity<LayuiTableParam> entity = new HttpEntity<LayuiTableParam>(param, httpHeaders);
			ResponseEntity<LayuiTableData> responseEntity = restTemplate.exchange(tzxmcgjdtjb_data, HttpMethod.POST, entity, LayuiTableData.class);
			int statusCode = responseEntity.getStatusCodeValue();
			if (statusCode == 200)
			{
				layuiTableData = responseEntity.getBody();
			}
			JSONObject result = JSONObject.parseObject(JSONObject.toJSONString(layuiTableData));
			System.out.println(">>>>>>>>>>>>>tzxmcgjdtjb_data:" + result.toString());
			return result.toString();
		}
	  
	  
	  //投资项目转出情况表
	  @RequestMapping(method = RequestMethod.GET, value = "/si/tzxmzcqkb")
	  public String tzxmzcqkb(HttpServletRequest request) throws Exception
	  {
		    SysUser userInfo = JwtTokenUtil.getUserFromToken(this.httpHeaders);
			HanaUtil.setSearchParaForUser2(userInfo,restTemplate,httpHeaders,request);
			
			String month = HanaUtil.getCurrrent_Year_Moth();
			request.setAttribute("month", month);
	        return "stp/hana/scientificInvestment/tzxmzcqkb";
	  }
	  
	  
	  
    @RequestMapping(method = RequestMethod.POST, value = "/tzxmzcqkb_data")
	@ResponseBody
	public String tzxmzcqkb_data(@ModelAttribute("param") LayuiTableParam param, HttpServletRequest request, HttpServletResponse response)
	{

	    String month = CommonUtil.getParameter(request, "month", "" + DateUtil.dateToStr(new Date(), DateUtil.FMT_MM));
		String companyCode = CommonUtil.getParameter(request, "companyCode", HanaUtil.YJY_CODE_NOT_YINGKE);
		System.out.println(">>>>>>>>>>>>>>>>>>>>>参数      month = "+month+" companyCode="+companyCode);
		Map<String, Object> paramsMap = new HashMap<String, Object>();
		paramsMap.put("month", month);
		paramsMap.put("companyCode", companyCode);
		LayuiTableData layuiTableData = new LayuiTableData();
		HttpEntity<LayuiTableParam> entity = new HttpEntity<LayuiTableParam>(param, httpHeaders);
		ResponseEntity<LayuiTableData> responseEntity = restTemplate.exchange(tzxmzcqkb_data, HttpMethod.POST, entity, LayuiTableData.class);
		int statusCode = responseEntity.getStatusCodeValue();
		if (statusCode == 200)
		{
			layuiTableData = responseEntity.getBody();
		}
		JSONObject result = JSONObject.parseObject(JSONObject.toJSONString(layuiTableData));
		System.out.println(">>>>>>>>>>>>>tzxmzcqkb_data:" + result.toString());
		return result.toString();
		
	}
	  
	  
	  
	  
}
