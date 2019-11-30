package com.pcitc.web.controller.researchplatform;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageInfo;
import com.pcitc.base.expert.ZjkBase;
import com.pcitc.base.researchplatform.PlatformInfoModel;
import com.pcitc.web.common.RestBaseController;
import com.pcitc.web.utils.ImportExcelUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang.time.DateFormatUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedInputStream;
import java.io.InputStream;
import java.util.*;

/**
 * <p>服务接口</p>
 * <p>Table:  kypt-info 科研平台-基本信息</p>
 * @author ty
 */
@Api(value = "researchPlatform-api", description = "国家科研平台接口")
@RestController
public class PlatformController extends RestBaseController {
    /**
     * 根据ID获取对象信息
     */
    private static final String load = "http://kjpt-zuul/stp-proxy/researchPlatform-api/load/";
    /**
     * 查询平台列表
     */
    private static final String query = "http://kjpt-zuul/stp-proxy/researchPlatform-api/query";

    /**
     * 查询平台列表不分页
     */
    private static final String queryNopage = "http://kjpt-zuul/stp-proxy/researchPlatform-api/queryNoPage";
    /**
     * 保存平台
     */
    private static final String save = "http://kjpt-zuul/stp-proxy/researchPlatform-api/save";
    /**
     * 删除
     */
    private static final String delete = "http://kjpt-zuul/stp-proxy/researchPlatform-api/delete/";

    private static final String selectPaltinfoCount = "http://kjpt-zuul/stp-proxy/researchPlatform-api/selectPaltinfoCount/";

    private static final String importPath = "http://kjpt-zuul/stp-proxy/researchPlatform-api/excelImport/";


    @ApiOperation(value="读取")
    @RequestMapping(value = "/platform-api/load/{id}", method = RequestMethod.GET)
    @ResponseBody
    public PlatformInfoModel load(@PathVariable String id) {
        ResponseEntity<PlatformInfoModel> responseEntity = this.restTemplate.exchange(load+id, HttpMethod.GET, new HttpEntity(this.httpHeaders), PlatformInfoModel.class);
        return responseEntity.getBody();
    }


    @ApiOperation(value="Excel导入")
    @RequestMapping(value = "/kgjImport/{importType}", method = RequestMethod.POST)
    @ResponseBody
    public List kgjImport(@RequestParam(value = "file", required = false) MultipartFile impExcel, @PathVariable String importType) throws Exception {
        InputStream in = new BufferedInputStream(impExcel.getInputStream());
        List dataList = new ImportExcelUtil().getBankListByExcel(in, impExcel.getOriginalFilename());
        this.httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        ResponseEntity<List> responseEntity = this.restTemplate.exchange(importPath+this.getUserProfile().getUserName(), HttpMethod.POST,  new HttpEntity<List<List<String>>>(dataList, this.httpHeaders), List.class);
        return responseEntity.getBody();
    }

    @ApiOperation(value="导出excel")
    @RequestMapping(value = "/platform-api/export", method = RequestMethod.GET)
    @ResponseBody
    public void export(@RequestParam(required = false) String platformName) throws Exception {
        Map<String, Object> condition = new HashMap<>(2);
        if (platformName != null) {
            this.setParam(condition, "platformName", platformName);
        }
        String[] headers = { "平台名称",  "依托单位",    "主要负责人"  , "平台类型"  ,  "研究领域"  ,"科研整体情况","科研经费","平台评分" };
        String[] cols =    {"platformName","supportingInstitutionsText","personLiable","typeText","researchFieldText","overallSituation","researchFunds","platformScoring"};
        this.httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        ResponseEntity<JSONArray> responseEntity = this.restTemplate.exchange(queryNopage, HttpMethod.POST, new HttpEntity<Map>(condition, this.httpHeaders), JSONArray.class);
        List list = JSONObject.parseArray(responseEntity.getBody().toJSONString(), PlatformInfoModel.class);
        String fileName = "科研平台表_"+ DateFormatUtils.format(new Date(), "ddhhmmss");
        this.exportExcel(headers,cols,fileName,list);
    }


    @ApiOperation(value = "查询科研平台列表", notes = "查询科研平台列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pageNum", value = "页码", dataType = "Integer", paramType = "query"),
            @ApiImplicitParam(name = "pageSize", value = "每页显示条数", dataType = "Integer", paramType = "query"),
            @ApiImplicitParam(name = "platformName", value = "平台名称", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "supportingInstitutions", value = "依托单位", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "personLiable", value = "主要负责人", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "researchField", value = "科研经费", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "level", value = "平台级别", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "platformScorinHigh", value = "平台评分区间高", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "platformScorinLow", value = "平台评分区间低", dataType = "string", paramType = "query")
    })
    @RequestMapping(value = "/platform-api/query", method = RequestMethod.GET)
    @ResponseBody
    public PageInfo query(
            @RequestParam(required = false,value = "pageNum") Integer pageNum,
            @RequestParam(required = false,value = "pageSize") Integer pageSize,
            @RequestParam(required = false,value = "platformName") String platformName,
            @RequestParam(required = false,value = "supportingInstitutions") String supportingInstitutions,
            @RequestParam(required = false,value = "personLiable") String personLiable,
            @RequestParam(required = false,value = "researchField") String researchField,
            @RequestParam(value = "level") String level,
            @RequestParam(required = false,value = "platformScorinHigh") String platformScorinHigh,
            @RequestParam(required = false,value = "platformScorinLow") String platformScorinLow

    ) {
        Map<String, Object> condition = new HashMap<>(6);
        if (pageNum == null) {
            this.setParam(condition, "pageNum", 1);
        }else {
            this.setParam(condition, "pageNum", pageNum);
        }
        if (pageSize == null) {
            this.setParam(condition, "pageSize", 10);
        }else {
            this.setParam(condition, "pageSize", pageSize);
        }
        if (!StringUtils.isEmpty(platformName)) {
            this.setParam(condition, "platformName", platformName);
        }
        if (!StringUtils.isEmpty(supportingInstitutions)) {
            this.setParam(condition, "supportingInstitutions", supportingInstitutions);
        }
        if (!StringUtils.isEmpty(personLiable)) {
            this.setParam(condition, "personLiable", personLiable);
        }
        if (!StringUtils.isEmpty(researchField)) {
            this.setParam(condition, "researchField", researchField);
        }
        if (!StringUtils.isEmpty(platformScorinHigh)) {
            this.setParam(condition, "platformScorinHigh", platformScorinHigh);
        }
        if (!StringUtils.isEmpty(platformScorinLow)) {
            this.setParam(condition, "platformScorinLow", platformScorinLow);
        }
        if (!StringUtils.isEmpty(level)) {
            this.setParam(condition, "level", level);
        }
        this.httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        ResponseEntity<PageInfo> responseEntity = this.restTemplate.exchange(query, HttpMethod.POST, new HttpEntity<Map>(condition, this.httpHeaders), PageInfo.class);
        return responseEntity.getBody();
    }

    @ApiOperation(value="保存")
    @RequestMapping(value = "/platform-api/save", method = RequestMethod.POST)
    @ResponseBody
    public PlatformInfoModel save(@RequestBody PlatformInfoModel pm) {
        this.setBaseData(pm);
        this.httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        ResponseEntity<PlatformInfoModel> responseEntity = this.restTemplate.exchange(save, HttpMethod.POST, new HttpEntity<PlatformInfoModel>(pm, this.httpHeaders), PlatformInfoModel.class);
        return responseEntity.getBody();
    }

    @ApiOperation(value="删除")
    @RequestMapping(value = "/platform-api/delete/{id}", method = RequestMethod.DELETE)
    @ResponseBody
    public Integer delete(@PathVariable String id) {
        ResponseEntity<Integer> responseEntity = this.restTemplate.exchange(delete+id, HttpMethod.DELETE, new HttpEntity(this.httpHeaders), Integer.class);
        return responseEntity.getBody();
    }

    @ApiOperation(value="查询平台相关条数")
    @RequestMapping(value = "/platform-api/selectPaltinfoCount/{id}", method = RequestMethod.GET)
    @ResponseBody
    public List selectPaltinfoCount(@PathVariable String id) {
        ResponseEntity<List> responseEntity = this.restTemplate.exchange(selectPaltinfoCount+id, HttpMethod.GET, new HttpEntity(this.httpHeaders), List.class);
        return responseEntity.getBody();
    }

    @ApiOperation(value="初始化")
    @RequestMapping(value = "/platform-api/newInit/{level}", method = RequestMethod.GET)
    @ResponseBody
    public PlatformInfoModel newInit(@PathVariable String level) {
        PlatformInfoModel p = new PlatformInfoModel();
        p.setId(UUID.randomUUID().toString().replace("-",""));
        p.setCreateDate(new Date());
        p.setCreator(this.getUserProfile().getUserName());
        p.setLevel(level);
        p.setPlatformScoring("0");
        p.setResearchFunds("0");
        p.setDeleted("0");
        return p;
    }



}
