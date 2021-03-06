package com.pcitc.web.controller.researchplatform;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageInfo;
import com.pcitc.base.researchplatform.PlatformInfoModel;
import com.pcitc.base.system.SysUser;
import com.pcitc.web.common.RestBaseController;
import com.pcitc.web.utils.EquipmentUtils;
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
     * 导出科技材料统计表
     */
    private static final String scienceStatisticsNoPage = "http://kjpt-zuul/stp-proxy/researchPlatform-api/scienceStatisticsNoPage";
    /**
     * 保存平台
     */
    private static final String save = "http://kjpt-zuul/stp-proxy/researchPlatform-api/save";
    /**
     * 删除
     */
    private static final String delete = "http://kjpt-zuul/stp-proxy/researchPlatform-api/delete/";
    /**
     * 科技材料统计表
     */
    private static final String scienceStatistics = "http://kjpt-zuul/stp-proxy/researchPlatform-api/scienceStatistics";

    private static final String selectPaltinfoCount = "http://kjpt-zuul/stp-proxy/researchPlatform-api/selectPaltinfoCount";

    private static final String importPath = "http://kjpt-zuul/stp-proxy/researchPlatform-api/excelImport/";


    @ApiOperation(value="读取")
    @RequestMapping(value = "/platform-api/load/{id}", method = RequestMethod.GET)
    @ResponseBody
    public PlatformInfoModel load(@PathVariable String id) {
        ResponseEntity<PlatformInfoModel> responseEntity = this.restTemplate.exchange(load+id, HttpMethod.GET, new HttpEntity(this.httpHeaders), PlatformInfoModel.class);
        return responseEntity.getBody();
    }



    @ApiOperation(value="导出excel")
    @RequestMapping(value = "/platform-api/export", method = RequestMethod.GET)
    @ResponseBody
    public void export(@RequestParam(required = false) String platformName,@RequestParam(required = false) String level) throws Exception {
        Map<String, Object> condition = new HashMap<>(2);
        if (platformName != null) {
            this.setParam(condition, "platformName", platformName);
        }
        if (level != null) {
            this.setParam(condition, "level", level);
        }
        String[] headers = { "平台名称",  "依托单位",    "主要负责人"  , "平台类型"  ,  "技术领域","平台介绍" ,"科研团队介绍" ,"科研整体情况","科研经费","平台评分" };
        String[] cols =    {"platformName","supportingInstitutionsText","personLiable","levelText","researchFieldText","platformIntroduction","teamIntroduction","overallSituation","researchFunds","platformScoring"};
        export(headers,cols,"科研平台表_",condition);
    }

    @ApiOperation(value="导出科研平台信息excel")
    @RequestMapping(value = "/platform-api/exportKyptInfo", method = RequestMethod.GET)
    @ResponseBody
    public void exportKyptInfo(@RequestParam(required = false) String researchField,
                               @RequestParam(required = false) String level,
                               @RequestParam(required = false,value = "supportingInstitutions") String supportingInstitutions,
                               @RequestParam(required = false,value = "platformScorinHigh") String platformScorinHigh,
                               @RequestParam(required = false,value = "platformScorinLow") String platformScorinLow
    ) throws Exception {
        Map<String, Object> condition = new HashMap<>(2);
        if (researchField != null) {
            this.setParam(condition, "researchField", researchField);
        }
        if (level != null) {
            this.setParam(condition, "level", level);
        }
        if (supportingInstitutions != null) {
            this.setParam(condition, "supportingInstitutions", supportingInstitutions);
        }
        if (platformScorinHigh != null) {
            this.setParam(condition, "platformScorinHigh", platformScorinHigh);
        }
        if (platformScorinLow != null) {
            this.setParam(condition, "platformScorinLow", platformScorinLow);
        }
        SysUser sysUserInfo = this.getUserProfile();
        String childUnitIds= EquipmentUtils.getAllChildsByIUnitPath(sysUserInfo.getDataScopeUnitPath(), restTemplate, httpHeaders);
        this.setParam(condition,"childUnitIds",childUnitIds);
        String[] headers = { "平台名称",  "依托单位",    "主要负责人"  , "平台类型"  ,  "技术领域"  ,"科研整体情况","科研经费","平台评分","项目数量","成果数量"};
        String[] cols =    {"platformName","supportingInstitutionsText","personLiable","levelText","researchFieldText","overallSituation","researchFunds","platformScoring","projectCount","achievementCount"};
        export(headers,cols,"科研平台信息表_",condition);
    }

    private void export(String[] headers,String[] cols,String fileName,Map condition) throws Exception {
        this.setBaseParam(condition);
        this.httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        ResponseEntity<JSONArray> responseEntity = this.restTemplate.exchange(queryNopage, HttpMethod.POST, new HttpEntity<Map>(condition, this.httpHeaders), JSONArray.class);
        List list = JSONObject.parseArray(responseEntity.getBody().toJSONString(), PlatformInfoModel.class);
        fileName = fileName+ DateFormatUtils.format(new Date(), "ddhhmmss");
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
            @ApiImplicitParam(name = "platformScorinLow", value = "平台评分区间低", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "secretLevel", value = "秘级", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "customQueryConditionStr",                   value = "条件",     dataType = "string", paramType = "query")
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
            @RequestParam(required = false,value = "platformScorinLow") String platformScorinLow,
            @RequestParam(required = false,value = "secretLevel") String secretLevel,
            @RequestParam(required = false) String customQueryConditionStr

    ) throws Exception {
        SysUser sysUserInfo = this.getUserProfile();
        Map<String, Object> condition = new HashMap<>();
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

        if(secretLevel != null){
            this.setParam(condition,"secretLevel",secretLevel);
        }
        if(customQueryConditionStr != null){
            this.setParam(condition,"customQueryConditionStr",customQueryConditionStr);
        }

        String childUnitIds= EquipmentUtils.getAllChildsByIUnitPath(sysUserInfo.getDataScopeUnitPath(), restTemplate, httpHeaders);
        this.setParam(condition,"childUnitIds",childUnitIds);
        this.setBaseParam(condition);
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
        Map<String, Object> condition = new HashMap<>(6);
        SysUser sysUserInfo = this.getUserProfile();
        this.setBaseParam(condition);
        //默认查询当前人所在机构下所有的科研平台
        String childUnitIds= EquipmentUtils.getAllChildsByIUnitPath(sysUserInfo.getDataScopeUnitPath(), restTemplate, httpHeaders);
        this.setParam(condition,"childUnitIds",childUnitIds);
        this.setParam(condition,"id",id);
        ResponseEntity<List> responseEntity = this.restTemplate.exchange(selectPaltinfoCount, HttpMethod.POST, new HttpEntity<Map>(condition,this.httpHeaders), List.class);
        return responseEntity.getBody();
    }

    @ApiOperation(value="查询科技材料统计表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pageNum", value = "页码", dataType = "Integer", paramType = "query"),
            @ApiImplicitParam(name = "pageSize", value = "每页显示条数", dataType = "Integer", paramType = "query"),
            @ApiImplicitParam(name = "year", value = "年份", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "unitId", value = "单位ID", dataType = "string", paramType = "query")
    })
    @RequestMapping(value = "/scienceStatistics", method = RequestMethod.GET)
    @ResponseBody
    public PageInfo scienceStatistics(
            @RequestParam(required = false,value = "pageNum") Integer pageNum,
            @RequestParam(required = false,value = "pageSize") Integer pageSize,
            @RequestParam(required = false,value = "year") String year,
            @RequestParam(required = false,value = "unitId") String unitId
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
        if (!StringUtils.isEmpty(year)) {
            this.setParam(condition, "year", year);
        }
        if (!StringUtils.isEmpty(unitId)) {
            this.setParam(condition, "unitId", unitId);
        }
        this.setBaseParam(condition);

        this.httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        ResponseEntity<PageInfo> responseEntity = this.restTemplate.exchange(scienceStatistics, HttpMethod.POST, new HttpEntity<Map>(condition, this.httpHeaders), PageInfo.class);
        return responseEntity.getBody();
    }
    @ApiOperation(value="导出科技材料统计表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "startYear", value = "开始年份", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "endYear", value = "结束年份", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "unitId", value = "单位ID", dataType = "string", paramType = "query")
    })
    @RequestMapping(value = "/exportScienceStatistics", method = RequestMethod.GET)
    @ResponseBody
    public void scienceStatistics(
            @RequestParam(required = false,value = "year") String year,
            @RequestParam(required = false,value = "unitId") String unitId
    ) throws Exception {

        Map<String, Object> condition = new HashMap<>(6);
        this.setBaseParam(condition);
        if (!StringUtils.isEmpty(year)) {
            this.setParam(condition, "year", year);
        }
        if (!StringUtils.isEmpty(unitId)) {
            this.setParam(condition, "unitId", unitId);
        }

        String[] headers = { "上报年度",  "上报单位",    "科技规划数量"  , "工作要点数量"  ,  "科技进展动态数量"  ,"年度总结数量","研究报告数量" };
        String[] cols =    {"annualYear","unitName","scientificCount","workPointCount","progressTrendsCount","annualSummaryCount","researchReportCount"};

        SysUser sysUserInfo = this.getUserProfile();
        this.httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        ResponseEntity<JSONArray> responseEntity = this.restTemplate.exchange(scienceStatisticsNoPage, HttpMethod.POST, new HttpEntity<Map>(condition, this.httpHeaders), JSONArray.class);
        List list = JSONObject.parseArray(responseEntity.getBody().toJSONString(), Map.class);
        this.exportExcel(headers,cols,"科技材料统计表_"+ DateFormatUtils.format(new Date(), "ddhhmmss"),list);
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
        p.setDeleted("0");
        return p;
    }



}
