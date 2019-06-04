package com.pcitc.web.expenses;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.pcitc.base.stp.expenses.ExpensesBills;
import com.pcitc.base.stp.expenses.ExpensesBillsApproves;
import com.pcitc.base.stp.expenses.ExpensesBillsItem;
import com.pcitc.base.stp.expenses.ExpensesBillsPay;
import com.pcitc.base.stp.expenses.ExpensesPayHana;
import com.pcitc.base.util.DateUtil;
import com.pcitc.service.expenses.ExpensesService;

@Api(value = "经费报销相关接口", tags = { "经费报销相关接口" })
@RestController
public class ExpensesProviderClient {
	private final static Logger logger = LoggerFactory.getLogger(ExpensesProviderClient.class);

	@Autowired
	private ExpensesService expensesService;

	@ApiOperation(value = "查询最大的更新时间", notes = "update_date字段")
	@RequestMapping(value = "/expenses/max-date/update", method = RequestMethod.POST)
	public String selectMaxUpdate() throws Exception {
		System.out.println("selectMaxUpdate==================");

		String updateDate = expensesService.selectMaxUpdate();
		return updateDate;
	}

	@ApiOperation(value = "批量保存费用报销数据", notes = "审批信息、费用信息等")
	@RequestMapping(value = "/expenses/add", method = RequestMethod.POST)
	public String saveExpenses(@RequestBody String jsonStr) throws Exception {
		System.out.println("saveExpenses==================" + jsonStr);
		JSONObject json = JSONObject.parseObject(jsonStr);
		if (json != null && json.get("result") != null && json.get("resMsg").toString().equals("success")) {
			List<ExpensesBillsPay> payList = new ArrayList<ExpensesBillsPay>();
			List<ExpensesBillsApproves> apprList = new ArrayList<ExpensesBillsApproves>();
			List<ExpensesBillsItem> itemList = new ArrayList<ExpensesBillsItem>();
			List<ExpensesBills> exList = new ArrayList<ExpensesBills>();

			// 查询最大值，+一个月作为本次的查询条件
			String startDate = expensesService.selectMaxUpdate();
			if (startDate == null) {
				startDate = "2018-02-28";
			} else {
				startDate = startDate.substring(0, 4) + "-" + startDate.substring(4, 6) + "-" + startDate.substring(6, 8);
			}

			Date temStartDate = DateUtil.strToDate(startDate, DateUtil.FMT_DD);

			Calendar rightNow1 = Calendar.getInstance();
			rightNow1.setTime(temStartDate);
			rightNow1.add(Calendar.DAY_OF_YEAR, 1);// 日期加1天
			Date temDate1 = rightNow1.getTime();

			Calendar rightNow = Calendar.getInstance();
			rightNow.setTime(temDate1);
			rightNow.add(Calendar.MONTH, 1);
			rightNow.add(Calendar.DAY_OF_YEAR, -1);// 日期加1天
			Date temDate = rightNow.getTime();
			String realStartDate = DateUtil.dateToStr(temDate1, DateUtil.FMT_YYYY_DD);
			String realEndDate = DateUtil.dateToStr(temDate, DateUtil.FMT_YYYY_DD);
			System.out.println("1执行完毕--------" + realStartDate);
			System.out.println("1执行完毕--------" + realEndDate);

			JSONArray jsonArray = (JSONArray) json.get("result");
			System.out.println("21返回--------" + jsonArray.toJSONString());
			for (int i = 0; i < jsonArray.size(); i++) {
				JSONObject temJson = (JSONObject) jsonArray.get(i);
				if (temJson != null && temJson.get("expenses") != null) {
					JSONArray expArray = (JSONArray) temJson.get("expenses");
					System.out.println("21返回--------" + expArray.toJSONString());
					for (int e = 0; e < expArray.size(); e++) {
						JSONObject eJson = expArray.getJSONObject(e);
						ExpensesBillsItem ebi = JSON.toJavaObject(eJson, ExpensesBillsItem.class);
						ebi.setDataId(UUID.randomUUID().toString().replaceAll("-", ""));
						ebi.setCreateDate(DateUtil.dateToStr(new Date(), DateUtil.FMT_SS));
						ebi.setSts("1");
						itemList.add(ebi);
					}
				}
				if (temJson != null && temJson.get("payoffs") != null) {
					JSONArray payArray = (JSONArray) temJson.get("payoffs");
					System.out.println("22返回--------" + payArray.toJSONString());
					for (int p = 0; p < payArray.size(); p++) {
						JSONObject pJson = payArray.getJSONObject(p);
						ExpensesBillsPay ebp = JSON.toJavaObject(pJson, ExpensesBillsPay.class);
						ebp.setDataId(UUID.randomUUID().toString().replaceAll("-", ""));
						ebp.setCreateDate(DateUtil.dateToStr(new Date(), DateUtil.FMT_SS));
						ebp.setSts("1");
						payList.add(ebp);
					}

				}
				if (temJson != null && temJson.get("approves") != null) {
					JSONArray apprArray = (JSONArray) temJson.get("approves");
					System.out.println("23返回--------" + apprArray.toJSONString());
					for (int a = 0; a < apprArray.size(); a++) {
						JSONObject aJson = apprArray.getJSONObject(a);
						ExpensesBillsApproves eba = JSON.toJavaObject(aJson, ExpensesBillsApproves.class);
						eba.setDataId(UUID.randomUUID().toString().replaceAll("-", ""));
						eba.setCreateDate(DateUtil.dateToStr(new Date(), DateUtil.FMT_SS));
						eba.setSts("1");
						apprList.add(eba);
					}
				}
				if (temJson != null && temJson.get("success") == null) {
					ExpensesBills expensesBills = JSON.toJavaObject(temJson, ExpensesBills.class);
					expensesBills.setDataId(UUID.randomUUID().toString().replaceAll("-", ""));
					expensesBills.setUpdateDate(realEndDate);
					expensesBills.setCreateDate(DateUtil.dateToStr(new Date(), DateUtil.FMT_SS));
					expensesBills.setSts("1");
					exList.add(expensesBills);
				}

			}
			System.out.println("31返回--------" + apprList.size());
			System.out.println("31返回--------" + payList.size());
			System.out.println("31返回--------" + itemList.size());
			System.out.println("31返回--------" + exList.size());
			if (exList.size() > 0) {
				expensesService.insertExpensesBillsBatch(exList);
			}
			if (apprList.size() > 0) {
				expensesService.insertExpensesBillsApprBatch(apprList);
			}
			if (payList.size() > 0) {
				expensesService.insertExpensesBillsPayBatch(payList);
			}
			if (itemList.size() > 0) {
				expensesService.insertExpensesBillsItemBatch(itemList);
			}

		}
		return "true";

	}

	@ApiOperation(value = "批量保存报销对于的erp中的付款明显清单", notes = "成本信息等")
	@RequestMapping(value = "/expenses/pay/hana/add", method = RequestMethod.POST)
	public String saveExpensesPayHana(@RequestBody String jsonStr) throws Exception {
		System.out.println("saveExpensesPayHana==================" + jsonStr);
		JSONArray jsonArray = JSONArray.parseArray(jsonStr);
		if (jsonArray != null && jsonArray.size() > 0) {
			List<ExpensesPayHana> payList = new ArrayList<ExpensesPayHana>();
			System.out.println("21返回--------" + jsonArray.toJSONString());
			// 先删除当年的所有数据
			for (int i = 0; i < jsonArray.size(); i++) {
				JSONObject json = (JSONObject) jsonArray.get(i);
				if (json != null) {
					String G0GJAHR = json.getString("G0GJAHR");
					HashMap<String, String> hashmap = new HashMap<String, String>();
					hashmap.put("G0GJAHR", G0GJAHR);
					expensesService.deleteExpensesPayHana(hashmap);
					break;
				}
			}
			for (int i = 0; i < jsonArray.size(); i++) {
				JSONObject json = (JSONObject) jsonArray.get(i);
				if (json != null) {
					String G0LOGSYS = json.getString("G0LOGSYS");
					String G0GSDM = json.getString("G0GSDM");
					String G0GSJC = json.getString("G0GSJC");
					String G0ZPZID = json.getString("G0ZPZID");
					String G0ZECSID = json.getString("G0ZECSID");
					String G0BUDAT = json.getString("G0BUDAT");
					String G0ZTMSVOUCHERID = json.getString("G0ZTMSVOUCHERID");
					String G0BELNR = json.getString("G0BELNR");
					String G0GJAHR = json.getString("G0GJAHR");
					String G0AUGBL = json.getString("G0AUGBL");
					String G0AUGDT = json.getString("G0AUGDT");
					String G0LIFNR = json.getString("G0LIFNR");
					String dataId = UUID.randomUUID().toString().replaceAll("-", "");
					
					ExpensesPayHana eph = new ExpensesPayHana();
					eph.setDataId(dataId);
					eph.setG0logsys(G0LOGSYS);
					eph.setG0gsdm(G0GSDM);
					eph.setG0gsjc(G0GSJC);
					eph.setG0zpzid(G0ZPZID);
					eph.setG0zecsid(G0ZECSID);
					eph.setG0budat(G0BUDAT);
					eph.setG0ztmsvoucherid(G0ZTMSVOUCHERID);;
					eph.setG0belnr(G0BELNR);
					eph.setG0gjahr(G0GJAHR);
					eph.setG0augbl(G0AUGBL);
					eph.setG0augdt(G0AUGDT);
					eph.setG0lifnr(G0LIFNR);
					payList.add(eph);
				}

			}
			System.out.println("31返回--------" + payList.size());
			if (payList.size() > 0) {
				expensesService.insertExpensesPayHanaBatch(payList);
			}

		}
		return "true";

	}

}
