package com.pcitc.web.controller.patent;


import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageInfo;
import com.pcitc.base.common.Result;
import com.pcitc.base.patent.PatentInfo;
import com.pcitc.base.system.SysDictionary;
import com.pcitc.base.system.SysUser;
import com.pcitc.base.util.DateUtil;
import com.pcitc.web.common.RestBaseController;
import com.pcitc.web.utils.EquipmentUtils;
import com.pcitc.web.utils.ImportExcelUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang.time.DateFormatUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.InputStream;
import java.math.BigDecimal;
import java.util.*;

@Api(value = "patent-api", description = "专利接口")
@RestController
@RequestMapping("/patentController")
public class PatentController extends RestBaseController {

    private static final String SAVE = "http://kjpt-zuul/stp-proxy/patent-provider/patentInfo/patentInfo_save";

    private static final String QUERY = "http://kjpt-zuul/stp-proxy/patent-provider/patentInfo/patentInfo_query";

    private static final String QUERY_PATENT = "http://kjpt-zuul/stp-proxy/patent-provider/patentInfo/patentInfo_queryPatent";

    private static final String queryNoPage = "http://kjpt-zuul/stp-proxy/patent-provider/patentInfo/queryNoPage";

    private static final String LOAD = "http://kjpt-zuul/stp-proxy/patent-provider/patentInfo/patentInfo_load/";

    private static final String DELETE = "http://kjpt-zuul/stp-proxy/patent-provider/patentInfo/patentInfo_delete/";

    private static final String batchRemove = "http://kjpt-zuul/stp-proxy/patent-provider/patentInfo/batchRemove/";
    private static final String postTreatment = "http://kjpt-zuul/stp-proxy/patent-provider/patentInfo/postTreatment/";

    private static final String countByLegalStatus = "http://kjpt-zuul/stp-proxy/patent-provider/patentInfo/countByLegalStatus";
    private static final String countByPatentType = "http://kjpt-zuul/stp-proxy/patent-provider/patentInfo/countByPatentType";

    private static final String GET_UNIT_ID = "http://kjpt-zuul/system-proxy/unit-provider/unit/getUnitId_by_name";

    private static final String ROOT_KJPT_ZLZL = "ROOT_KJPT_ZLZL";


    /**
     * 导入模板头部所占行数
     */
    private static final Integer IMPORT_HEAD = 3;

    private static final String PATENT_INFO_EXCEL_INPUT = "http://kjpt-zuul/stp-proxy/patent-provider/patentInfo/excel_input";

    //用于缓存导入时的字典数据
    private Map<String,Map<String,String>> dictMap = new HashMap<>();

    private static final String ROOT_KJPT_FLZT = "ROOT_KJPT_FLZT(ZL)";

    private static final String ROOT_KJPT_SQLX = "ROOT_KJPT_SQLX";

    private static final String ROOT_KJPT_GJ = "ROOT_KJPT_GJ";
    //用于缓存项目背景数据字典
    private Map<String,String> projectbckMap = new HashMap<>();

    private static final String ROOT_KJPT_XMBJ = "ROOT_KJPT_XMBJ";

    private static final String ROOT_KJPT_YYJSLYJSLJSL = "ROOT_KJPT_YYJSLYJSLJSL";

    private static final String ROOT_KJPT_YYJSLYCPL = "ROOT_KJPT_YYJSLYCPL";

    private static final String ROOT_KJPT_YYJSLYJSL_HKXJS = "ROOT_KJPT_YYJSLYJSL_HKXJS";

    private static final String ROOT_KJPT_YYJSLYCPL_HNYHJSYY = "ROOT_KJPT_YYJSLYCPL_HNYHJSYY";
    /**
     * 保存-专利信息
     *
     * @param patentInfo
     * @return PatentInfo
     */
    @ApiOperation(value = "保存专利信息", notes = "保存专利信息")
    @RequestMapping(value = "/save",method=RequestMethod.POST)
    @ResponseBody
    public PatentInfo save(@RequestBody PatentInfo patentInfo) {
        this.setBaseData(patentInfo);
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        ResponseEntity<PatentInfo> responseEntity = this.restTemplate.exchange(SAVE, HttpMethod.POST, new HttpEntity<PatentInfo>(patentInfo, this.httpHeaders), PatentInfo.class);
        return responseEntity.getBody();
    }

    /**
     * 专利列表-分页查询
     *
     * @return PageInfo
     */
    @ApiOperation(value = "查询专利列表", notes = "查询专利列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pageNum", value = "页码", dataType = "Integer", paramType = "query"),
            @ApiImplicitParam(name = "pageSize", value = "每页显示条数", dataType = "Integer", paramType = "query"),
            @ApiImplicitParam(name = "unitName", value = "单位名称", dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "createUnitName", value = "单位名称", dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "applicationDateStart", value = "申请日期开始", dataType = "Date", paramType = "query"),
            @ApiImplicitParam(name = "applicationDateEnd", value = "申请日期结束", dataType = "Date", paramType = "query"),
            @ApiImplicitParam(name = "applicationType", value = "申请类型", dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "patentType", value = "专利类型", dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "applicationNumber", value = "申请号（专利号）", dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "patentName", value = "专利名称", dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "applicant", value = "申请人", dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "inventor", value = "发明人", dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "technicalFieldIndex", value = "技术领域索引", dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "type", value = "后专项处理", dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "projectBackground", value = "项目背景", dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "secretLevel", value = "密级", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "legalStatus", value = "法律状态", dataType = "string", paramType = "query")

    })
    @RequestMapping(value = "/query",  method = RequestMethod.GET)
    @ResponseBody
    public PageInfo query(
            @RequestParam(required = false) Integer pageNum,
            @RequestParam(required = false) Integer pageSize,
            @RequestParam(required = false) String unitName,
            @RequestParam(required = false) String createUnitId,
            @RequestParam(required = false) @DateTimeFormat(pattern="yyyy-MM-dd") Date applicationDateStart,
            @RequestParam(required = false) @DateTimeFormat(pattern="yyyy-MM-dd") Date applicationDateEnd,
            @RequestParam(required = false) String applicationType,
            @RequestParam(required = false) String patentType,
            @RequestParam(required = false) String applicationNumber,
            @RequestParam(required = false) String patentName,
            @RequestParam(required = false) String applicant,
            @RequestParam(required = false) String inventor,
            @RequestParam(required = false) String technicalFieldIndex,
            @RequestParam(required = false) String projectBackground,
            @RequestParam(required = false) String secretLevel,
            @RequestParam(required = false) String type,
            @RequestParam(required = false) String legalStatus
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
        if (!StringUtils.isEmpty(unitName)) {
            this.setParam(condition, "unitName", unitName);
        }
        if (!StringUtils.isEmpty(createUnitId)) {
            this.setParam(condition, "createUnitId", createUnitId);
        }
        if (!StringUtils.isEmpty(DateUtil.format(applicationDateStart,DateUtil.FMT_SS))) {
            this.setParam(condition, "applicationDateStart", DateUtil.format(applicationDateStart,DateUtil.FMT_SS));
        }
        if (!StringUtils.isEmpty(DateUtil.format(applicationDateEnd,DateUtil.FMT_SS))) {
            this.setParam(condition, "applicationDateEnd", DateUtil.format(applicationDateEnd,DateUtil.FMT_SS));
        }
        if (!StringUtils.isEmpty(applicationType)) {
            this.setParam(condition, "applicationType", applicationType);
        }
        if (!StringUtils.isEmpty(patentType)) {
            this.setParam(condition, "patentType", patentType);
        }
        if (!StringUtils.isEmpty(type)) {
            this.setParam(condition, "type", type);
        }
        if (!StringUtils.isEmpty(applicationNumber)) {
            this.setParam(condition, "applicationNumber", applicationNumber);
        }
        if (!StringUtils.isEmpty(patentName)) {
            this.setParam(condition, "patentName", patentName);
        }
        if (!StringUtils.isEmpty(applicant)) {
            this.setParam(condition, "applicant", applicant);
        }
        if (!StringUtils.isEmpty(inventor)) {
            this.setParam(condition, "inventor", inventor);
        }
        if (!StringUtils.isEmpty(technicalFieldIndex)) {
            this.setParam(condition, "technicalFieldIndex", technicalFieldIndex);
        }
        if (!StringUtils.isEmpty(projectBackground)) {
            this.setParam(condition, "projectBackground", projectBackground);
        }

        if(secretLevel != null){
            this.setParam(condition,"secretLevel",secretLevel);
        }
        if(legalStatus != null){
            this.setParam(condition,"legalStatus",legalStatus);
        }

        this.setBaseParam(condition);

        this.httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        SysUser sysUserInfo = this.getUserProfile();
        String childUnitIds= EquipmentUtils.getAllChildsByIUnitPath(sysUserInfo.getDataScopeUnitPath(), restTemplate, httpHeaders);
        this.setParam(condition,"childUnitIds",childUnitIds);
        ResponseEntity<PageInfo> responseEntity = this.restTemplate.exchange(QUERY, HttpMethod.POST, new HttpEntity<Map>(condition, this.httpHeaders), PageInfo.class);
        return responseEntity.getBody();
    }

    /**
     * 专利列表-分页查询
     *
     * @return PageInfo
     */
    @ApiOperation(value = "查询专利列表", notes = "查询专利列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pageNum", value = "页码", dataType = "Integer", paramType = "query"),
            @ApiImplicitParam(name = "pageSize", value = "每页显示条数", dataType = "Integer", paramType = "query"),
            @ApiImplicitParam(name = "unitName", value = "单位名称", dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "applicationDateStart", value = "申请日期开始", dataType = "Date", paramType = "query"),
            @ApiImplicitParam(name = "applicationDateEnd", value = "申请日期结束", dataType = "Date", paramType = "query"),
            @ApiImplicitParam(name = "applicationType", value = "申请类型", dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "patentType", value = "专利类型", dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "applicationNumber", value = "申请号（专利号）", dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "patentName", value = "专利名称", dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "applicant", value = "申请人", dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "inventor", value = "发明人", dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "technicalFieldIndex", value = "技术领域索引", dataType = "String", paramType = "query")
    })
    @RequestMapping(value = "/queryPatent",  method = RequestMethod.GET)
    @ResponseBody
    public List queryPatent(
            @RequestParam(required = false) Integer pageNum,
            @RequestParam(required = false) Integer pageSize,
            @RequestParam(required = false) String unitName,
            @RequestParam(required = false) @DateTimeFormat(pattern="yyyy-MM-dd") Date applicationDateStart,
            @RequestParam(required = false) @DateTimeFormat(pattern="yyyy-MM-dd") Date applicationDateEnd,
            @RequestParam(required = false) String applicationType,
            @RequestParam(required = false) String patentType,
            @RequestParam(required = false) String applicationNumber,
            @RequestParam(required = false) String patentName,
            @RequestParam(required = false) String applicant,
            @RequestParam(required = false) String inventor,
            @RequestParam(required = false) String technicalFieldIndex

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
        if (!StringUtils.isEmpty(unitName)) {
            this.setParam(condition, "unitName", unitName);
        }
        if (!StringUtils.isEmpty(DateUtil.format(applicationDateStart,DateUtil.FMT_SS))) {
            this.setParam(condition, "applicationDateStart", DateUtil.format(applicationDateStart,DateUtil.FMT_SS));
        }
        if (!StringUtils.isEmpty(DateUtil.format(applicationDateEnd,DateUtil.FMT_SS))) {
            this.setParam(condition, "applicationDateEnd", DateUtil.format(applicationDateEnd,DateUtil.FMT_SS));
        }
        if (!StringUtils.isEmpty(applicationType)) {
            this.setParam(condition, "applicationType", applicationType);
        }
        if (!StringUtils.isEmpty(patentType)) {
            this.setParam(condition, "patentType", patentType);
        }
        if (!StringUtils.isEmpty(applicationNumber)) {
            this.setParam(condition, "applicationNumber", applicationNumber);
        }
        if (!StringUtils.isEmpty(patentName)) {
            this.setParam(condition, "patentName", patentName);
        }
        if (!StringUtils.isEmpty(applicant)) {
            this.setParam(condition, "applicant", applicant);
        }
        if (!StringUtils.isEmpty(inventor)) {
            this.setParam(condition, "inventor", inventor);
        }
        if (!StringUtils.isEmpty(technicalFieldIndex)) {
            this.setParam(condition, "technicalFieldIndex", technicalFieldIndex);
        }
        SysUser sysUserInfo = this.getUserProfile();
        String childUnitIds= EquipmentUtils.getAllChildsByIUnitPath(sysUserInfo.getDataScopeUnitPath(), restTemplate, httpHeaders);
        this.setParam(condition,"childUnitIds",childUnitIds);
        this.httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        ResponseEntity<List> responseEntity = this.restTemplate.exchange(QUERY_PATENT, HttpMethod.POST, new HttpEntity<Map>(condition, this.httpHeaders), List.class);
        List arraylist = new ArrayList();
        Map hashMap = new HashMap<String,Object>();
        hashMap.put("total",responseEntity.getBody().size());
        hashMap.put("list",responseEntity.getBody());
        arraylist.add(hashMap);
        return arraylist;
    }
    /**
     * 专利列表-导出
     *
     * @return PageInfo
     */
    @ApiOperation(value = "导出", notes = "导出")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pageNum", value = "页码", dataType = "Integer", paramType = "query"),
            @ApiImplicitParam(name = "pageSize", value = "每页显示条数", dataType = "Integer", paramType = "query"),
            @ApiImplicitParam(name = "unitName", value = "单位名称", dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "createUnitName", value = "单位名称", dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "applicationDateStart", value = "申请日期开始", dataType = "Date", paramType = "query"),
            @ApiImplicitParam(name = "applicationDateEnd", value = "申请日期结束", dataType = "Date", paramType = "query"),
            @ApiImplicitParam(name = "applicationType", value = "申请类型", dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "patentType", value = "专利类型", dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "applicationNumber", value = "申请号（专利号）", dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "patentName", value = "专利名称", dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "applicant", value = "申请人", dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "inventor", value = "发明人", dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "technicalFieldIndex", value = "技术领域索引", dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "type", value = "后专项处理", dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "projectBackground", value = "项目背景", dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "secretLevel", value = "密级", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "legalStatus", value = "法律状态", dataType = "string", paramType = "query")
    })
    @RequestMapping(value = "/exportExcel",  method = RequestMethod.GET)
    @ResponseBody
    public void queryPatent(
            @RequestParam(required = false) Integer pageNum,
            @RequestParam(required = false) Integer pageSize,
            @RequestParam(required = false) String unitName,
            @RequestParam(required = false) String createUnitId,
            @RequestParam(required = false) @DateTimeFormat(pattern="yyyy-MM-dd") Date applicationDateStart,
            @RequestParam(required = false) @DateTimeFormat(pattern="yyyy-MM-dd") Date applicationDateEnd,
            @RequestParam(required = false) String applicationType,
            @RequestParam(required = false) String patentType,
            @RequestParam(required = false) String applicationNumber,
            @RequestParam(required = false) String patentName,
            @RequestParam(required = false) String applicant,
            @RequestParam(required = false) String inventor,
            @RequestParam(required = false) String technicalFieldIndex,
            @RequestParam(required = false) String projectBackground,
            @RequestParam(required = false) String secretLevel,
            @RequestParam(required = false) String type,
            @RequestParam(required = false) String legalStatus


    ) throws Exception {
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
        if (!StringUtils.isEmpty(unitName)) {
            this.setParam(condition, "unitName", unitName);
        }
        if (!StringUtils.isEmpty(createUnitId)) {
            this.setParam(condition, "createUnitId", createUnitId);
        }
        if (!StringUtils.isEmpty(DateUtil.format(applicationDateStart,DateUtil.FMT_SS))) {
            this.setParam(condition, "applicationDateStart", DateUtil.format(applicationDateStart,DateUtil.FMT_SS));
        }
        if (!StringUtils.isEmpty(DateUtil.format(applicationDateEnd,DateUtil.FMT_SS))) {
            this.setParam(condition, "applicationDateEnd", DateUtil.format(applicationDateEnd,DateUtil.FMT_SS));
        }
        if (!StringUtils.isEmpty(applicationType)) {
            this.setParam(condition, "applicationType", applicationType);
        }
        if (!StringUtils.isEmpty(patentType)) {
            this.setParam(condition, "patentType", patentType);
        }
        if (!StringUtils.isEmpty(type)) {
            this.setParam(condition, "type", type);
        }
        if (!StringUtils.isEmpty(applicationNumber)) {
            this.setParam(condition, "applicationNumber", applicationNumber);
        }
        if (!StringUtils.isEmpty(patentName)) {
            this.setParam(condition, "patentName", patentName);
        }
        if (!StringUtils.isEmpty(applicant)) {
            this.setParam(condition, "applicant", applicant);
        }
        if (!StringUtils.isEmpty(inventor)) {
            this.setParam(condition, "inventor", inventor);
        }
        if (!StringUtils.isEmpty(technicalFieldIndex)) {
            this.setParam(condition, "technicalFieldIndex", technicalFieldIndex);
        }
        if (!StringUtils.isEmpty(projectBackground)) {
            this.setParam(condition, "projectBackground", projectBackground);
        }

        if(secretLevel != null){
            this.setParam(condition,"secretLevel",secretLevel);
        }
        if(legalStatus != null){
            this.setParam(condition,"legalStatus",legalStatus);
        }
        this.setBaseParam(condition);
        String[] headers = { "专利名称",  "专利号",    "申请（专利权）人"  , "发明人","申请类型","专利类别","国别组织","申请日期","授权日期","终止日期","法律状态",
                "法律状态变更日期","应用技术领域（技术类）","项目背景","立项部门","项目编号","项目名称","应用技术领域（产品类）","应用型号产品名称","应用分系统名称","元器件及配套材料名称",
                "主分类号","副分类号","联合申请人","优先权","代理机构","公开（公告）号","说明","法人代码"};
        String[] cols =    {"patentName","applicationNumber","applicant","inventor","applicationTypeText","patentTypeText","countryText","applicationDate","authorizationDate",
                            "terminationDate","legalStatusText","legalStatusUpdateTime","technicalFieldText","projectBackgroundText","establishmentDepartment","projectNumber","projectName","applicationTechnologyTechnologyText",
                            "applicationModelProductName","applicationSubsystemName","nameOfComponentsAndSupportingMaterials","mainClassificationNumber","subCategoryNumber","jointApplicant","priorityRight","agency","publicAnnouncementNo", "explainer","legalPersonCode"};
        this.httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        SysUser sysUserInfo = this.getUserProfile();
        String childUnitIds= EquipmentUtils.getAllChildsByIUnitPath(sysUserInfo.getDataScopeUnitPath(), restTemplate, httpHeaders);
        this.setParam(condition,"childUnitIds",childUnitIds);
        this.httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        ResponseEntity<JSONArray> responseEntity = this.restTemplate.exchange(queryNoPage, HttpMethod.POST, new HttpEntity<Map>(condition, this.httpHeaders), JSONArray.class);
        List list = JSONObject.parseArray(responseEntity.getBody().toJSONString(), PatentInfo.class);
        String fileName = "专利明细表_"+ DateFormatUtils.format(new Date(), "ddhhmmss");
        this.exportExcel(headers,cols,fileName,list);
    }



    /**
     * 根据ID查询专利信息
     *
     * @return PatentInfo
     */
    @ApiOperation(value="根据ID查询专利信息")
    @RequestMapping(value = "/load/{id}", method = RequestMethod.GET)
    @ResponseBody
    public PatentInfo load(@PathVariable String id) {
        ResponseEntity<PatentInfo> responseEntity = this.restTemplate.exchange(LOAD+id, HttpMethod.GET, new HttpEntity(this.httpHeaders), PatentInfo.class);
        return responseEntity.getBody();
    }
    /**
     * 后专项处理批量移除
     *
     * @return PatentInfo
     */
    @ApiOperation(value="批量移除")
    @RequestMapping(value = "/batchRemove/{ids}", method = RequestMethod.GET)
    @ResponseBody
    public void batchRemove(@PathVariable String ids) {
        Map<String, Object> condition = new HashMap<>(6);
        this.restTemplate.exchange(batchRemove+ids, HttpMethod.POST, new HttpEntity(this.httpHeaders),Integer.class);
//        return responseEntity.getBody();

    }

    /**
     * 批量后处理
     *
     * @return PatentInfo
     */
    @ApiOperation(value="批量后处理")
    @RequestMapping(value = "/postTreatment/{ids}", method = RequestMethod.GET)
    @ResponseBody
    public void postTreatment(@PathVariable String ids) {
        Map<String, Object> condition = new HashMap<>(6);
        this.restTemplate.exchange(postTreatment+ids, HttpMethod.POST, new HttpEntity(this.httpHeaders),Integer.class);
//        return responseEntity.getBody();

    }
    /**
     * 根据法律状态查询专利数量
     *
     * @return PatentInfo
     */
    @ApiOperation(value="根据法律状态查询专利数量")
    @RequestMapping(value = "/countByLegalStatus", method = RequestMethod.GET)
    @ResponseBody
    public List countByLegalStatus(@RequestParam(required = false) String type) {
        Map<String, Object> condition = new HashMap<>(6);
        SysUser sysUserInfo = this.getUserProfile();
        String childUnitIds= EquipmentUtils.getAllChildsByIUnitPath(sysUserInfo.getDataScopeUnitPath(), restTemplate, httpHeaders);
        this.setParam(condition,"childUnitIds",childUnitIds);
        if (!StringUtils.isEmpty(type)) {
            this.setParam(condition, "type", type);
        }
        this.httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        ResponseEntity<List> responseEntity = this.restTemplate.exchange(countByLegalStatus, HttpMethod.POST, new HttpEntity<Map>(condition,this.httpHeaders),List.class);
        return responseEntity.getBody();
    }

    /**
     * 根据专利类型查询专利数量
     *
     * @return PatentInfo
     */
    @ApiOperation(value="根据专利类型查询专利数量")
    @RequestMapping(value = "/countByPatentType", method = RequestMethod.GET)
    @ResponseBody
    public List countByPatentType(@RequestParam(required = false) String type) {
        Map<String, Object> condition = new HashMap<>(6);
        SysUser sysUserInfo = this.getUserProfile();
        String childUnitIds= EquipmentUtils.getAllChildsByIUnitPath(sysUserInfo.getDataScopeUnitPath(), restTemplate, httpHeaders);
        this.setParam(condition,"childUnitIds",childUnitIds);
        if (!StringUtils.isEmpty(type)) {
            this.setParam(condition, "type", type);
        }
        this.httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        ResponseEntity<List> responseEntity = this.restTemplate.exchange(countByPatentType, HttpMethod.POST, new HttpEntity<Map>(condition,this.httpHeaders),List.class);
        return responseEntity.getBody();

    }

    /**
     * 删除专利信息
     *
     * @return Integer
     */
    @ApiOperation(value="删除专利信息")
    @RequestMapping(value = "/delete/{id}", method = RequestMethod.DELETE)
    @ResponseBody
    public Integer delete(@PathVariable String id){
        ResponseEntity<Integer> responseEntity = this.restTemplate.exchange(DELETE +id, HttpMethod.DELETE, new HttpEntity(this.httpHeaders), Integer.class);
        return responseEntity.getBody();
    }

    /**
     * 初始化方法
     *
     * @param request
     * @return PatentInfo
     */
    @ApiOperation(value="初始化专利信息")
    @RequestMapping(value = "/newInit",method = RequestMethod.GET)
    @ResponseBody
    public PatentInfo newInit(HttpServletRequest request) {
        PatentInfo patentInfo = new PatentInfo();
        patentInfo.setId(UUID.randomUUID().toString().replace("-",""));
        patentInfo.setCreateDate(new Date());
        patentInfo.setCreator(this.getUserProfile().getUserName());
        patentInfo.setDeleted("0");
        return patentInfo;
    }


    @ApiOperation(value = "根据模板导入专利管理信息（EXCEL）", notes = "根据模板导入专利管理信息（EXCEL）")
    @RequestMapping(value = "/input_excel", method = RequestMethod.POST)
    public Object newImportData(HttpServletRequest req, HttpServletResponse resp, MultipartFile file) throws Exception
    {
        Result resultsDate = new Result();
        String type = req.getQueryString();
        if(StringUtils.isBlank(type)){
            resultsDate.setSuccess(false);
            resultsDate.setMessage("未能获取专利管理信息类型，请重试");
        }else{
            type = getReportTypeFromQueryString(type);
        }
        SysUser sysUserInfo = this.getUserProfile();

        if (file.isEmpty())
        {
            resultsDate.setSuccess(false);
            resultsDate.setMessage("上传异常，请重试");
        }else
        {
            InputStream in = file.getInputStream();
            List<List<Object>> listob = new ImportExcelUtil().getBankListByExcel(in, file.getOriginalFilename());
            System.out.println(">>>>>>行数:"+listob.size());
            List<PatentInfo> list = new ArrayList<PatentInfo>();
            resultsDate= getResult( listob );
            if(resultsDate.isSuccess()==true)
            {
                for (int i = IMPORT_HEAD; i < listob.size(); i++)
                {
                    List<Object> lo = listob.get(i);
                    if(lo.size()<40) break;
                    Object col_1 = lo.get(1);    //专利名称
                    Object col_2 = lo.get(2);    //专利号
                    Object col_3 = lo.get(3);    //申请人
                    Object col_4 = lo.get(4);    //发明人
                    if(checkIfBlank(col_1)&&checkIfBlank(col_2)&&checkIfBlank(col_3)&&checkIfBlank(col_4)) break;
                    Object col_5 = lo.get(5);    //申请类型
                    Object col_6 = lo.get(6); //专利类别
                    Object col_7 = lo.get(7);    //国别组织
                    Object col_8 = lo.get(8);    //申请日期
                    Object col_9 = lo.get(9);    //授权日期
                    Object col_10 = lo.get(10);  //终止日期
                    Object col_11 = lo.get(11);  //法律状态
                    Object col_12 = lo.get(12);  //法律状态变更日期
                    Object col_13 = lo.get(13);  //应用技术领域（技术类）
                    Object col_14 = lo.get(14);  //项目背景
                    Object col_15 = lo.get(15);  //立项部门
                    Object col_16 = lo.get(16);  //项目编号
                    Object col_17 = lo.get(17);  //项目名称
                    Object col_18 = lo.get(18);  //应用技术领域（产品类）
                    Object col_19 = lo.get(19);  //应用型号产品名称
                    Object col_20 = lo.get(20);  //应用分系统名称
                    Object col_21 = lo.get(21);  //元器件及配套材料名称
                    Object col_22 = lo.get(22);  //主分类号
                    Object col_23 = lo.get(23);  //副分类号
                    Object col_24 = lo.get(24);  //联合申请人
                    Object col_25 = lo.get(25);  //优先权
                    Object col_26 = lo.get(26);  //代理机构
                    Object col_27 = lo.get(27);  //公开号
                    Object col_28 = lo.get(28);  //说明
                    Object col_29 = lo.get(29);  //密级--不填
                    Object col_30 = lo.get(30);  //法人代码--不填
                    Object col_31 = lo.get(31);  //单位名称
                    Object col_32 = lo.get(32);  //自定义分类
                    Object col_33 = lo.get(33);  //专利范围
                    Object col_34 = lo.get(34);  //备注
                    Object col_35 = lo.get(35);  //专业转化应用
                    Object col_36 = lo.get(36);  //未实施原因
                    Object col_37 = lo.get(37);  //被许可人
                    Object col_38 = lo.get(38);  //许可收益（万元）
                    Object col_39 = lo.get(39);  //被转让人
                    Object col_40 = lo.get(40);  //转让收益（万元）
                    //将excel导入数据转换为PatentInfo对象
                    PatentInfo obj = new PatentInfo();
                    obj.setPatentName(String.valueOf(col_1));
                    obj.setApplicationNumber(String.valueOf(col_2));
                    obj.setApplicant(String.valueOf(col_3));
                    obj.setInventor(String.valueOf(col_4));
                    obj.setApplicationType(getValueFromDictMap(String.valueOf(col_5),ROOT_KJPT_SQLX));
                    obj.setPatentType(getValueFromDictMap(String.valueOf(col_6),ROOT_KJPT_ZLZL));
                    obj.setPatentTypeText(String.valueOf(col_6));
                    obj.setCountry(getValueFromDictMap(String.valueOf(col_7),ROOT_KJPT_GJ));
                    obj.setCountryText(String.valueOf(col_7));
                    Date applicationDate = DateUtil.strToDate(String.valueOf(col_8),DateUtil.FMT_DD);
                    obj.setApplicationDate(applicationDate);
                    obj.setApplicationDateStr(String.valueOf(col_8));
                    if(!checkIfBlank(col_9)){
                        Date authDate = DateUtil.strToDate(String.valueOf(col_9),DateUtil.FMT_DD);
                        obj.setAuthorizationDate(authDate);
                    }
                    if(!checkIfBlank(col_10)){
                        Date temDate = DateUtil.strToDate(String.valueOf(col_10),DateUtil.FMT_DD);
                        obj.setTerminationDate(temDate);
                    }
                    obj.setLegalStatus(getValueFromDictMap(String.valueOf(col_11),ROOT_KJPT_FLZT));
                    obj.setLegalStatusText(String.valueOf(col_11));
                    Date legalStatusUpdateTime = DateUtil.strToDate(String.valueOf(col_12),DateUtil.FMT_DD);
                    obj.setLegalStatusUpdateTime(legalStatusUpdateTime);
                    //先从领域技术里获取，如果没有，再从核科学技术中获取
                    String tech = getValueFromDictMap(String.valueOf(col_13),ROOT_KJPT_YYJSLYJSLJSL);
                    if(!StringUtils.isNotBlank(tech)){
                        tech = getValueFromDictMap(String.valueOf(col_13),ROOT_KJPT_YYJSLYJSL_HKXJS);
                    }
                    obj.setApplicationTechnologyTechnology(tech);
                    obj.setTechnicalFieldText(String.valueOf(col_13));
                    obj.setProjectBackground(projectbckMap.get(String.valueOf(col_14)));
                    obj.setProjectBackgroundText(String.valueOf(col_14));
                    obj.setEstablishmentDepartment(String.valueOf(col_15));
                    obj.setProjectNumber(String.valueOf(col_16));
                    obj.setProjectName(String.valueOf(col_17));
                    //应用技术领域产品类
                    if(!checkIfBlank(lo.get(17))){
                        String techProduct = getValueFromDictMap(String.valueOf(col_18),ROOT_KJPT_YYJSLYCPL);
                        if(!StringUtils.isNotBlank(techProduct)){
                            techProduct = getValueFromDictMap(String.valueOf(col_18),ROOT_KJPT_YYJSLYCPL_HNYHJSYY);
                        }
                        obj.setApplicationTechnologyProducts(techProduct);
                    }
                    obj.setApplicationModelProductName(String.valueOf(col_19));
                    obj.setApplicationSubsystemName(String.valueOf(col_20));
                    obj.setNameOfComponentsAndSupportingMaterials(String.valueOf(col_21));
                    obj.setMainClassificationNumber(String.valueOf(col_22));
                    obj.setSubCategoryNumber(String.valueOf(col_23));
                    obj.setJointApplicant(String.valueOf(col_24));
                    obj.setPriorityRight(String.valueOf(col_25));
                    obj.setAgency(String.valueOf(col_26));
                    obj.setPublicAnnouncementNo(String.valueOf(col_27));
                    //todo:数据库中的字段是explainer，可能会导入失败
                    obj.setExplain(String.valueOf(col_28));
                    obj.setUnitName(restTemplate.exchange(GET_UNIT_ID, HttpMethod.POST, new HttpEntity<Object>(col_31,this.httpHeaders), String.class).getBody());
                    obj.setCreateUnitName(String.valueOf(col_31));
                    obj.setCreateUnitId(obj.getUnitName());
                    obj.setUnitNameText(String.valueOf(col_31));
                    obj.setCustomClassification(String.valueOf(col_32));
                    obj.setPatentRange(String.valueOf(col_33));
                    obj.setRemark(String.valueOf(col_34));
                    //todo:专业转化应用
                    obj.setApplicationOfPatentTransformation(String.valueOf(col_35));
                    obj.setApplicationOfPatentTransformationText(String.valueOf(col_35));
                    obj.setUnenforcedReason(String.valueOf(col_36));
                    obj.setLicensee(String.valueOf(col_37));
                    //todo:需要修改字段类型为Double
                   if(!checkIfBlank(col_38)){
                        String profit  = String.valueOf(col_38);
                        obj.setLicenseeProfit(new BigDecimal(profit));
                    }
                    obj.setAssignor(String.valueOf(col_39));
                    if(!checkIfBlank(col_39)){
                        String profit  = String.valueOf(col_39);
                        obj.setAssignProfit(new BigDecimal(profit));
                    }
                    obj.setType(type);
                    /*obj.setCreateUnitId(sysUserInfo.getUnitId());*/
                    obj.setDeleted("0");
                    String dateid = UUID.randomUUID().toString().replaceAll("-", "");
                    obj.setId(dateid);
                    obj.setSecretLevel("0");
                    list.add(obj);
                }
                ResponseEntity<Result> responseEntity =  this.restTemplate.exchange(PATENT_INFO_EXCEL_INPUT, HttpMethod.POST, new HttpEntity<Object>(list, this.httpHeaders), Result.class);
                int statusCode = responseEntity.getStatusCodeValue();
                // 返回结果代码
                if (statusCode == 200) {
                    resultsDate.setSuccess(true);

                    resultsDate.setCode("0");
                } else {
                    Result back = responseEntity.getBody();
                    resultsDate.setSuccess(false);
                    resultsDate.setMessage(back.getMessage());
                }
            }else{
                resultsDate.setSuccess(false);
                resultsDate.setCode("1");
            }

        }

        return resultsDate;
    }

    private Result getResult(List<List<Object>> listob )
    {
        Result resultsDate = new Result();
        resultsDate.setSuccess(true);
        StringBuffer sb=new StringBuffer();
        for (int i = IMPORT_HEAD; i < listob.size(); i++)
        {
            List<Object> lo = listob.get(i);
            if(lo.size()<40) break;
            Object col_1 = lo.get(1);    //专利名称
            Object col_2 = lo.get(2);    //专利号
            Object col_3 = lo.get(3);    //申请人
            Object col_4 = lo.get(4);    //发明人
            if(checkIfBlank(col_1)&&checkIfBlank(col_2)&&checkIfBlank(col_3)&&checkIfBlank(col_4)) break;
            Object col_5 = lo.get(5);    //申请类型
            Object col_6 = lo.get(6); //专利类别
            Object col_7 = lo.get(7);    //国别组织
            Object col_8 = lo.get(8);    //申请日期
            Object col_9 = lo.get(9);    //授权日期
            Object col_10 = lo.get(10);  //终止日期
            Object col_11 = lo.get(11);  //法律状态
            Object col_12 = lo.get(12);  //法律状态变更日期
            Object col_13 = lo.get(13);  //应用技术领域（技术类）
            Object col_14 = lo.get(14);  //项目背景
            Object col_15 = lo.get(15);  //立项部门
            Object col_16 = lo.get(16);  //项目编号
            Object col_17 = lo.get(17);  //项目名称
            Object col_18 = lo.get(18);  //应用技术领域（产品类）
            Object col_19 = lo.get(19);  //应用型号产品名称
            Object col_20 = lo.get(20);  //应用分系统名称
            Object col_21 = lo.get(21);  //元器件及配套材料名称
            Object col_22 = lo.get(22);  //主分类号
            Object col_23 = lo.get(23);  //副分类号
            Object col_24 = lo.get(24);  //联合申请人
            Object col_25 = lo.get(25);  //优先权
            Object col_26 = lo.get(26);  //代理机构
            Object col_27 = lo.get(27);  //公开号
            Object col_28 = lo.get(28);  //说明
            Object col_29 = lo.get(29);  //密级--不填
            Object col_30 = lo.get(30);  //法人代码--不填
            Object col_31 = lo.get(31);  //单位名称
            Object col_32 = lo.get(32);  //自定义分类
            Object col_33 = lo.get(33);  //专利范围
            Object col_34 = lo.get(34);  //备注
            Object col_35 = lo.get(35);  //专业转化应用
            Object col_36 = lo.get(36);  //未实施原因
            Object col_37 = lo.get(37);  //被许可人
            Object col_38 = lo.get(38);  //许可收益（万元）
            Object col_39 = lo.get(39);  //被转让人
            Object col_40 = lo.get(40);  //转让收益（万元）

            // 必填项和字典值校验
            if(checkIfBlank(col_1))
            {
                sb.append("第"+(i+2)+"行专利名称为空,");
                break;
            }
            if(checkIfBlank(col_2))
            {
                sb.append("第"+(i+2)+"行专利号为空,");
                break;
            }
            if(checkIfBlank(col_5))
            {
                sb.append("第"+(i+2)+"行申请类型为空,");
                break;
            }else if(!checkIfReasonable(String.valueOf(col_5),ROOT_KJPT_SQLX)){
                sb.append("第"+(i+2)+"行申请类型取值非法,请参考对应sheet页取值!");
                break;
            }
            if(checkIfBlank(col_6))
            {
                sb.append("第"+(i+2)+"行专利类别为空,");
                break;

            }else if(!checkIfReasonable(String.valueOf(col_6),ROOT_KJPT_ZLZL)){
                sb.append("第"+(i+2)+"行专利类别取值非法,请参考对应sheet页取值!");
                break;
            }
            if(checkIfBlank(col_8))
            {
                sb.append("第"+(i+2)+"行申请日期为空,");
                break;
            }
            if(checkIfBlank(col_11))
            {
                sb.append("第"+(i+2)+"行法律状态为空,");
                break;
            }else if(!checkIfReasonable(String.valueOf(col_11),ROOT_KJPT_FLZT)){
                sb.append("第"+(i+2)+"行法律状态取值非法,请参考对应sheet页取值!");
                break;
            }
            if(checkIfBlank(col_12))
            {
                sb.append("第"+(i+2)+"行法律状态变更日期为空,");
                break;
            }
            if(checkIfBlank(col_13))
            {
                sb.append("第"+(i+2)+"行应用技术领域为空,");
                break;
            } else if(!checkIfReasonable(String.valueOf(col_13),ROOT_KJPT_YYJSLYJSLJSL)){
                if(!checkIfReasonable(String.valueOf(col_13),ROOT_KJPT_YYJSLYJSL_HKXJS)){
                    sb.append("第"+(i+2)+"行应用技术领域取值非法,请参考对应sheet页取值!");
                    break;
                }
            }
            if(checkIfBlank(col_14))
            {
                sb.append("第"+(i+2)+"行项目背景为空,");
                break;
            }else if(!checkProjectBackgroundIfExists(String.valueOf(col_14))){
                sb.append("第"+(i+2)+"行项目背景取值非法,请参考对应sheet页取值!");
                break;
            }
            if(checkIfBlank(col_31))
            {
                sb.append("第"+(i+2)+"行单位名称为空,");
                break;
            }
            if(checkIfBlank(col_35))
            {
                sb.append("第"+(i+2)+"行专业转化应用为空,");
                break;
            }else if("未实施".equals(col_35) && checkIfBlank(col_36)){
                sb.append("第"+(i+2)+"行未实施原因为空,");
                break;
            }else if("许可实施".equals(col_35) && (checkIfBlank(col_37) || checkIfBlank(col_38)) ){
                sb.append("第"+(i+2)+"行被许可人或者许可收益为空,");
                break;
            }else if("转让".equals(col_35) && (checkIfBlank(col_39) || checkIfBlank(col_40)) ){
                sb.append("第"+(i+2)+"行被转让人或者转让收益为空,");
                break;
            }
            if(!checkIfBlank(col_7)){
                if(!checkIfReasonable(String.valueOf(col_7),ROOT_KJPT_GJ)){
                    sb.append("第"+(i+2)+"行国别组织取值非法,请参考对应sheet页取值!");
                    break;
                }
            }
            if(!checkIfBlank(col_18)){
                if(!checkIfReasonable(String.valueOf(col_18),ROOT_KJPT_YYJSLYCPL)){
                    if(!checkIfReasonable(String.valueOf(col_18),ROOT_KJPT_YYJSLYCPL_HNYHJSYY)){
                        sb.append("第"+(i+2)+"行应用技术领域（产品类）取值非法,请参考对应sheet页取值!");
                        break;
                    }
                }
            }

           /* if(checkIfBlank(col_10))
            {
                sb.append("第"+(i+2)+"行申请人为空,");
                break;
            }
            if(checkIfBlank(col_11))
            {
                sb.append("第"+(i+2)+"行发明人为空,");
                break;
            }

        */


        }
        resultsDate.setMessage(sb.toString());
        if((sb.toString()).equals(""))
        {
            resultsDate.setSuccess(true);
        }else
        {
            resultsDate.setSuccess(false);
        }
        return resultsDate;
    }

    private Boolean checkIfBlank(Object o){
        if(o==null) return true;
        if(String.valueOf(o)=="") return true;
        return false;
    }

    private String getReportTypeFromQueryString(String queryString){
        String[] querys = queryString.split("=");
        return querys[1];
    }

    private Boolean checkIfReasonable(String content,String dictCode){
        //导入的数据确认用户只输入一个字典值20200605
        //针对模板中使用到的字典数据进行缓存
        Map<String,String> detailDicMap;
        if(dictMap.containsKey(dictCode)){
            detailDicMap = dictMap.get(dictCode);
            if(detailDicMap.containsKey(content)) return  true;
        }else{
            detailDicMap = new HashMap<>();
            dictMap.put(dictCode,detailDicMap);
        }

        List<SysDictionary> sysDictionaryList=    EquipmentUtils.getSysDictionaryListByParentCode(dictCode, restTemplate, httpHeaders);
        for(SysDictionary dictionary:sysDictionaryList){
            if(content.equals(dictionary.getName())) {
                Map<String,String> temp = dictMap.get(dictCode);
                temp.put(content,dictionary.getNumValue());
                dictMap.put(dictCode,temp);
                return true;
            }
        }
        return  false;
    }

    public String getDictNumValueByDictCodeAndName(String dictCode,String name){
        List<SysDictionary> sysDictionaryList = EquipmentUtils.getSysDictionaryListByParentCode(dictCode, restTemplate, httpHeaders);
        for(SysDictionary dictionary:sysDictionaryList){
            if(name.equals(dictionary.getName())) {
                return dictionary.getNumValue();
            }
        }
        return "";
    }

    private String getValueFromDictMap(String name,String dictCode){
        if(StringUtils.isNotBlank(name)&&StringUtils.isNotBlank(dictCode)){
            Map<String,String> detail = dictMap.get(dictCode);
            if(detail!=null){
                return detail.get(name);
            }else if(checkIfReasonable(String.valueOf(name),dictCode)){
                Map<String,String> stemp = dictMap.get(dictCode);
                if(detail!=null){
                    return detail.get(name);
                }
            }
        }
        return "null";
    }


    //查看项目背景是否存在
    private Boolean checkProjectBackgroundIfExists(String name){

        List<SysDictionary> sysDictionaryList=    EquipmentUtils.getSysDictionaryListLikeParentCode(ROOT_KJPT_XMBJ, restTemplate, httpHeaders);
        for(SysDictionary dictionary:sysDictionaryList){
            if(name.equals(dictionary.getName())) {
                projectbckMap.put(name,dictionary.getNumValue());
                return true;
            }
        }
        return false;
    }

}
