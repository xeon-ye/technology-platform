package com.pcitc.web.controller.achieve;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.pcitc.base.achieve.AchieveMaintainBI;
import com.pcitc.base.achieve.AchieveRecordBI;
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

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>成果维护</p>
 * @author
 */
@Api(value = "achieveMaintainBI-api", description = "成果管理领导驾驶舱接口")
@RestController
public class AchieveMaintainBIController extends RestBaseController {

    /**
     * 获奖按年份统计
     */
    private static final String getAwardSumByQuery = "http://kjpt-zuul/stp-proxy/achieveMaintainBI-api/getAwardSumByQuery";
    /**
     * 获奖按年份统计 - 饼图
     */
    private static final String getAwardSumByTypePie = "http://kjpt-zuul/stp-proxy/achieveMaintainBI-api/getAwardSumByTypePie";

    /**
     * 获奖明细
     */
    private static final String getDetailList = "http://kjpt-zuul/stp-proxy/achieveMaintainBI-api/getDetailList";


    /**
     * 历年成果转化完成情况
     */
    private static final String getAchieveTransferByYear = "http://kjpt-zuul/stp-proxy/achieveMaintainBI-api/getAchieveTransferByYear";


    /**
     * 成果转化方式
     */
    private static final String getAchieveTransferByType = "http://kjpt-zuul/stp-proxy/achieveMaintainBI-api/getAchieveTransferByType";

    /**
     * 二级单位成果转化情况
     */
    private static final String getAchieveTransferByOffice = "http://kjpt-zuul/stp-proxy/achieveMaintainBI-api/getAchieveTransferByOffice";


    @ApiOperation(value="获奖按年份统计")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "year", value = "年份", dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "type", value = "获奖类型", dataType = "String", paramType = "query")
    })
    @RequestMapping(value = "/achieveMaintainBI-api/getAwardSumByQuery", method = RequestMethod.GET)
    public List<AchieveMaintainBI> getAwardSumByQuery(
            @RequestParam(required = false,value = "year") String year,
            @RequestParam(required = false,value = "type") String type
    ) {
        Map<String, Object> condition = new HashMap<>(2);
        if (!StringUtils.isEmpty(year)) {
            this.setParam(condition, "year", year);
        }
        if (!StringUtils.isEmpty(type)) {
            this.setParam(condition, "type", type);
        }
        this.setBaseParam(condition);
        this.httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        ResponseEntity<JSONArray> responseEntity = this.restTemplate.exchange(getAwardSumByQuery, HttpMethod.POST, new HttpEntity<Map>(condition, this.httpHeaders), JSONArray.class);
        List list = JSONObject.parseArray(responseEntity.getBody().toJSONString(), AchieveMaintainBI.class);
        return list;
    }


    @ApiOperation(value="获奖按年份统计 - 饼图")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "year", value = "年份", dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "type", value = "获奖类型", dataType = "String", paramType = "query")
    })
    @RequestMapping(value = "/achieveMaintainBI-api/getAwardSumByTypePie", method = RequestMethod.GET)
    public List<AchieveMaintainBI> getAwardSumByTypePie(
            @RequestParam(required = false,value = "year") String year,
            @RequestParam(required = false,value = "type") String type
    ) {
        Map<String, Object> condition = new HashMap<>(2);
        if (!StringUtils.isEmpty(year)) {
            this.setParam(condition, "year", year);
        }
        if (!StringUtils.isEmpty(type)) {
            this.setParam(condition, "type", type);
        }
        this.setBaseParam(condition);
        this.httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        ResponseEntity<JSONArray> responseEntity = this.restTemplate.exchange(getAwardSumByTypePie, HttpMethod.POST, new HttpEntity<Map>(condition, this.httpHeaders), JSONArray.class);
        List list = JSONObject.parseArray(responseEntity.getBody().toJSONString(), AchieveMaintainBI.class);
        return list;
    }


    @ApiOperation(value = "查询列表", notes = "查询列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "year", value = "年份", dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "type", value = "获奖类型", dataType = "String", paramType = "query")
    })
    @RequestMapping(value = "/achieveMaintainBI-api/getDetailList", method = RequestMethod.GET)
    @ResponseBody
    public List<AchieveMaintainBI>  getDetailList(
            @RequestParam(required = false,value = "year") String year,
            @RequestParam(required = false,value = "type") String type
    ){

        Map<String, Object> condition = new HashMap<>(6);
        if (!StringUtils.isEmpty(year)) {
            this.setParam(condition, "year", year);
        }
        if (!StringUtils.isEmpty(type)) {
            this.setParam(condition, "type", type);
        }
        this.setBaseParam(condition);
        this.httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        //控制数据范围
        /*String childUnitIds= EquipmentUtils.getAllChildsByIUnitPath(sysUserInfo.getDataScopeUnitPath(), restTemplate, httpHeaders);
        this.setParam(condition,"childUnitIds",childUnitIds);*/
        ResponseEntity<JSONArray> responseEntity = this.restTemplate.exchange(getDetailList, HttpMethod.POST, new HttpEntity<Map>(condition, this.httpHeaders), JSONArray.class);
        List list = JSONObject.parseArray(responseEntity.getBody().toJSONString(), AchieveMaintainBI.class);
        return list;
    }

    @ApiOperation(value = "历年成果转化完成情况", notes = "历年成果转化完成情况")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "year", value = "年份", dataType = "String", paramType = "query")
    })
    @RequestMapping(value = "/achieveMaintainBI-api/getAchieveTransferByYear", method = RequestMethod.GET)
    @ResponseBody
    public List<AchieveRecordBI>  getAchieveTransferByYear(
            @RequestParam(required = false,value = "year") String year
    ){

        Map<String, Object> condition = new HashMap<>(6);
        if (!StringUtils.isEmpty(year)) {
            this.setParam(condition, "year", year);
        }
        this.setBaseParam(condition);
        this.httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        //控制数据范围
        /*String childUnitIds= EquipmentUtils.getAllChildsByIUnitPath(sysUserInfo.getDataScopeUnitPath(), restTemplate, httpHeaders);
        this.setParam(condition,"childUnitIds",childUnitIds);*/
        ResponseEntity<JSONArray> responseEntity = this.restTemplate.exchange(getAchieveTransferByYear, HttpMethod.POST, new HttpEntity<Map>(condition, this.httpHeaders), JSONArray.class);
        List list = JSONObject.parseArray(responseEntity.getBody().toJSONString(), AchieveRecordBI.class);
        return list;
    }

    @ApiOperation(value = "成果转化方式", notes = "成果转化方式")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "year", value = "年份", dataType = "String", paramType = "query")
    })
    @RequestMapping(value = "/achieveMaintainBI-api/getAchieveTransferByType", method = RequestMethod.GET)
    @ResponseBody
    public List<AchieveRecordBI>  getAchieveTransferByType(
            @RequestParam(required = false,value = "year") String year
    ){

        Map<String, Object> condition = new HashMap<>(6);
        if (!StringUtils.isEmpty(year)) {
            this.setParam(condition, "year", year);
        }
        this.setBaseParam(condition);
        this.httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        //控制数据范围
        /*String childUnitIds= EquipmentUtils.getAllChildsByIUnitPath(sysUserInfo.getDataScopeUnitPath(), restTemplate, httpHeaders);
        this.setParam(condition,"childUnitIds",childUnitIds);*/
        ResponseEntity<JSONArray> responseEntity = this.restTemplate.exchange(getAchieveTransferByType, HttpMethod.POST, new HttpEntity<Map>(condition, this.httpHeaders), JSONArray.class);
        List list = JSONObject.parseArray(responseEntity.getBody().toJSONString(), AchieveRecordBI.class);
        return list;
    }

    @ApiOperation(value = "二级单位成果转化情况", notes = "二级单位成果转化情况")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "year", value = "年份", dataType = "String", paramType = "query")
    })
    @RequestMapping(value = "/achieveMaintainBI-api/getAchieveTransferByOffice", method = RequestMethod.GET)
    @ResponseBody
    public List<AchieveRecordBI>  getAchieveTransferByOffice(
            @RequestParam(required = false,value = "year") String year
    ){

        Map<String, Object> condition = new HashMap<>(6);
        if (!StringUtils.isEmpty(year)) {
            this.setParam(condition, "year", year);
        }
        this.setBaseParam(condition);
        this.httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        //控制数据范围
        /*String childUnitIds= EquipmentUtils.getAllChildsByIUnitPath(sysUserInfo.getDataScopeUnitPath(), restTemplate, httpHeaders);
        this.setParam(condition,"childUnitIds",childUnitIds);*/
        ResponseEntity<JSONArray> responseEntity = this.restTemplate.exchange(getAchieveTransferByOffice, HttpMethod.POST, new HttpEntity<Map>(condition, this.httpHeaders), JSONArray.class);
        List list = JSONObject.parseArray(responseEntity.getBody().toJSONString(), AchieveRecordBI.class);
        return list;
    }

}
