package com.pcitc.web.qims;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageInfo;
import com.pcitc.base.qims.QualityStatistics;
import com.pcitc.base.util.DateUtil;
import com.pcitc.base.util.DateUtils;
import com.pcitc.service.qims.QimsService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.*;

/**
 * <p>
 * 服务接口
 * </p>
 * <p>
 * Table: qualityStatistics - 质量信息
 * </p>
 *
 * @since 2019-12-27 15:37:36
 */
@Api(value = "QualityStatisticsClient-API", description = "质量信息服务接口")
@RestController
public class QimsClient {

	private final static Logger logger = LoggerFactory.getLogger(QimsClient.class);

	private static final String QIMS_URL = "http://10.102.111.112/qims/a/interface/indicator/";

	@Autowired
	QimsService qimsService;

	/**
	 * 保存
	 *
	 * @param keyStr
	 * @return
	 */
	@ApiOperation(value = "质量信息服务接口", notes = "质量信息服务接口")

	@RequestMapping(value = "/qims-provider/qualityStatistics/qualityStatistics_excute/{keyStr}", method = RequestMethod.GET)
	public JSONObject save(@PathVariable String keyStr) {
		JSONObject jsonObject = new JSONObject(3);

		String [] keyArray = keyStr.split(",");
		for(String str:keyArray){
			RestTemplate restTemplate = new RestTemplate();
			restTemplate.getMessageConverters().add(new WxMappingJackson2HttpMessageConverter());
			ResponseEntity<String> responseEntity = restTemplate.getForEntity(QIMS_URL+str+"/kjpt",String.class);
			QualityStatistics qualityStatistics = new QualityStatistics();
			qualityStatistics.setId(UUID.randomUUID().toString().replace("-",""));
			qualityStatistics.setKey(str);
			qualityStatistics.setContent(responseEntity.getBody().toString());
			qualityStatistics.setDate(DateUtil.dateAdd(new Date(),-1));
			qualityStatistics.setSecretLevel("4");
			qimsService.save(qualityStatistics);
		}
		jsonObject.put("code","success");
		jsonObject.put("message","成功");
		jsonObject.put("data","");
		return jsonObject;
	}

	public class WxMappingJackson2HttpMessageConverter extends MappingJackson2HttpMessageConverter {
		public WxMappingJackson2HttpMessageConverter() {
			List mediaTypes = new ArrayList<>();
			mediaTypes.add(MediaType.TEXT_PLAIN);
			mediaTypes.add(MediaType.TEXT_HTML); //加入text/html类型的支持
			setSupportedMediaTypes(mediaTypes);// tag6
		}
	}

	/**
	 * 分页查询
	 *
	 * @param param
	 * @return
	 */
	@ApiOperation(value = "质量接口-分页查询", notes = "质量接口-分页查询")
	@RequestMapping(value = "/qims-provider/qualityStatistics/qualityStatistics_query", method = RequestMethod.POST)
	public PageInfo queryQualityStatisticsByPage(@RequestBody(required = false) Map param) {
		return qimsService.queryQualityStatisticsList(param);
	}
}
