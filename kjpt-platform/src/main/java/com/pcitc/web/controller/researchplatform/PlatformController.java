package com.pcitc.web.controller.researchplatform;

import com.github.pagehelper.PageInfo;
import com.pcitc.base.researchplatform.PlatformInfoModel;
import com.pcitc.web.common.RestBaseController;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
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
     * 保存平台
     */
    private static final String save = "http://kjpt-zuul/stp-proxy/researchPlatform-api/save";
    /**
     * 删除
     */
    private static final String delete = "http://kjpt-zuul/stp-proxy/researchPlatform-api/delete/";

    private static final String selectPaltinfoCount = "http://kjpt-zuul/stp-proxy/researchPlatform-api/selectPaltinfoCount/";


    @ApiOperation(value="读取")
    @RequestMapping(value = "/platform-api/load/{id}", method = RequestMethod.GET)
    @ResponseBody
    public PlatformInfoModel load(@PathVariable String id) {
        ResponseEntity<PlatformInfoModel> responseEntity = this.restTemplate.exchange(load+id, HttpMethod.GET, new HttpEntity(this.httpHeaders), PlatformInfoModel.class);
        return responseEntity.getBody();
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