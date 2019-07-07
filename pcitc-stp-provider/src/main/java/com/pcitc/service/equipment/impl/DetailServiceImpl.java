package com.pcitc.service.equipment.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.pcitc.base.stp.equipment.SreEquipmentLedger;
import com.pcitc.mapper.equipment.SreEquipmentLedgerMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.pcitc.base.common.LayuiTableData;
import com.pcitc.base.common.LayuiTableParam;
import com.pcitc.base.stp.equipment.SreDetail;
import com.pcitc.mapper.equipment.SreDetailMapper;
import com.pcitc.service.equipment.DetailService;
@Service("detailService")
@Transactional(propagation = Propagation.REQUIRED, readOnly = false, rollbackFor = Exception.class)
public class DetailServiceImpl implements DetailService {
	
	
	private final static Logger logger = LoggerFactory.getLogger(DetailServiceImpl.class); 
	@Autowired
	private SreDetailMapper detailMapper;

	@Autowired
	private SreEquipmentLedgerMapper sreEquipmentLedgerMapper;
	
	
	private String getTableParam(LayuiTableParam param,String paramName,String defaultstr)
	{
		String resault="";
		Object object=param.getParam().get(paramName);
		if(object!=null)
		{
			resault=(String)object;
		}
		return resault;
	}
	
	
	public LayuiTableData getDetailPage(LayuiTableParam param)throws Exception
	{
		
		//每页显示条数
		int pageSize = param.getLimit();
		//从第多少条开始
		int pageStart = (param.getPage()-1)*pageSize;
		//当前是第几页
		int pageNum = pageStart/pageSize + 1;
		// 1、设置分页信息，包括当前页数和每页显示的总计数
		PageHelper.startPage(pageNum, pageSize);
		String sunlike=getTableParam(param,"sunlike","");
		String unitPathIds=getTableParam(param,"unitPathIds","");
		String parentUnitPathIds=getTableParam(param,"parentUnitPathIds","");
		
		String equipmentName=getTableParam(param,"equipmentName","");
		String equipmentType=getTableParam(param,"equipmentType","");
		String equipmentId=getTableParam(param,"equipmentId","");
		String equipmentPrice=getTableParam(param,"equipmentPrice","");
		String equipmenNumber=getTableParam(param,"equipmenNumber","");
		String declareUnit=getTableParam(param,"declareUnit","");
		
		String declareDepartment=getTableParam(param,"declareDepartment","");
		String declarePeople=getTableParam(param,"declarePeople","");
		String declareTime=getTableParam(param,"declareTime","");
		String assetNumber=getTableParam(param,"assetNumber","");
		String configure=getTableParam(param,"configure","");
		String measuringUnit=getTableParam(param,"measuringUnit","");
		
		String assetsClassification=getTableParam(param,"assetsClassification","");
		String nationality=getTableParam(param,"nationality","");
		String placeUse=getTableParam(param,"placeUse","");
		String placePeople=getTableParam(param,"placePeople","");
		String receivePeople=getTableParam(param,"receivePeople","");

		
		Map map=new HashMap();
		map.put("equipmentName", equipmentName);
		map.put("equipmentType", equipmentType);
		map.put("equipmentId", equipmentId);
		map.put("equipmentPrice", equipmentPrice);
		map.put("equipmenNumber", equipmenNumber);
		map.put("declareUnit", declareUnit);
		map.put("declareDepartment", declareDepartment);
		map.put("declarePeople", declarePeople);
		map.put("declareTime", declareTime);
		map.put("assetNumber", assetNumber);
		map.put("configure", configure);
		map.put("measuringUnit", measuringUnit);
		map.put("assetsClassification", assetsClassification);
		map.put("nationality", nationality);
		map.put("placeUse", placeUse);
		map.put("placePeople", placePeople);
		map.put("receivePeople", receivePeople);
	
		map.put("unitPathIds", parentUnitPathIds);
		map.put("parentUnitPathIds", unitPathIds);
		
		List<SreDetail> sreSunlike = new ArrayList<SreDetail>();
		List<SreDetail> list = detailMapper.getList(map);
		if(list.size()!=0) {
			if(sunlike.equals("1")) {
				for(SreDetail detail : list) {
					map.put("g0anln1", detail.getAssetNumber());
					map.put("g0gsdm", detail.getSupplier());
					List<SreEquipmentLedger> sreequin  = sreEquipmentLedgerMapper.getSreDetailId(map);
					if(sreequin.size()!=0) {
					for(SreEquipmentLedger ledasd : sreequin) {
						if(ledasd!=null) {
							detail.setG0NDURJ(ledasd.getG0ndurj().toString());//使用年限
							detail.setG0SCHRW(ledasd.getG0schrw().toString());//资产残值
							detail.setG0LJGZYZJE(ledasd.getG0ljgzyzje().toString());//账面净额
							detail.setG0LJDJZJJE(ledasd.getG0ljdjzjje().toString());//预付定金
							detail.setG0NCGZYZJE(ledasd.getG0ncgzyzje().toString());//年初购置价值
							detail.setG0LJZJJE(ledasd.getG0ljzjje().toString());//累计折旧
							sreSunlike.add(detail);
						}
					  }
					}
				}
				PageInfo<SreDetail> pageInfo = new PageInfo<SreDetail>(sreSunlike);
				System.out.println(">>>>>>>>>查询分页结果"+pageInfo.getList().size());
				LayuiTableData data = new LayuiTableData();
				data.setData(pageInfo.getList());
				Long total = pageInfo.getTotal();
				data.setCount(total.intValue());
			    return data;
			}
		}
		PageInfo<SreDetail> pageInfo = new PageInfo<SreDetail>(list);
		System.out.println(">>>>>>>>>查询分页结果"+pageInfo.getList().size());
		LayuiTableData data = new LayuiTableData();
		data.setData(pageInfo.getList());
		Long total = pageInfo.getTotal();
		data.setCount(total.intValue());
	    return data;
	}

	@Override
	public Integer insertDetail(SreDetail sreDetail) {
		// TODO Auto-generated method stub
		return detailMapper.insert(sreDetail);
	}


	@Override
	public Integer insertForApplication(SreDetail sreDetail) {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public LayuiTableData getForApplicationView(LayuiTableParam param) {
		
			
			//每页显示条数
			int pageSize = param.getLimit();
			//从第多少条开始
			int pageStart = (param.getPage()-1)*pageSize;
			//当前是第几页
			int pageNum = pageStart/pageSize + 1;
			// 1、设置分页信息，包括当前页数和每页显示的总计数
			PageHelper.startPage(pageNum, pageSize);
			String configure=getTableParam(param,"equipmentIds","");
			
			Map map=new HashMap();
			map.put("configure", configure);
		
			
			List<SreDetail> list = detailMapper.getList(map);
			PageInfo<SreDetail> pageInfo = new PageInfo<SreDetail>(list);
			System.out.println(">>>>>>>>>查询分页结果"+pageInfo.getList().size());
			LayuiTableData data = new LayuiTableData();
			data.setData(pageInfo.getList());
			Long total = pageInfo.getTotal();
			data.setCount(total.intValue());
		    return data;
		}

	
	//删除转资绑定装备
	@Override
	public Integer deleteDetail(String id) {
		
		return detailMapper.deleteByPrimaryKey(id);
	}
	
	
	public int updateByid(String id)throws Exception
	{
		return detailMapper.updateByid(id);
		
	}


	@Override
	public SreDetail selectSreDetailId(String id) {
		// TODO Auto-generated method stub
		return detailMapper.selectSreDetailId(id);
	}

	@Override
	public LayuiTableData getLedgerPage(LayuiTableParam param) throws Exception
	{

		//每页显示条数
		int pageSize = param.getLimit();
		//从第多少条开始
		int pageStart = (param.getPage()-1)*pageSize;
		//当前是第几页
		int pageNum = pageStart/pageSize + 1;
		// 1、设置分页信息，包括当前页数和每页显示的总计数
		PageHelper.startPage(pageNum, pageSize);
		String unitPathIds=getTableParam(param,"unitPathIds","");
		String parentUnitPathIds=getTableParam(param,"parentUnitPathIds","");
        String equipmentName1 = getTableParam(param, "equipmentName", "");

        Map map=new HashMap();
		map.put("unitPathIds", parentUnitPathIds);
		map.put("parentUnitPathIds", unitPathIds);
        map.put("equipmentName", equipmentName1);
		List<SreDetail> list = detailMapper.getList(map);


        Map map1=new HashMap();
        List<SreEquipmentLedger> ledgerList = new ArrayList<>();
        if (list!=null){
            for (SreDetail sreDetail : list) {
                String supplier = sreDetail.getSupplier();//公司代码
                String assetNumber = sreDetail.getAssetNumber();//资产编号
                String equipmentName = sreDetail.getEquipmentName();//装备名称

                String equipmentPrice = sreDetail.getEquipmentPrice();//单价(万元)
                String equipmenNumber = sreDetail.getEquipmenNumber();//装备数量
                String declareUnit = sreDetail.getDeclareUnit();//申报单位
                String declareDepartment = sreDetail.getDeclareDepartment();//申报部门
                String declarePeople = sreDetail.getDeclarePeople();//申报人

                map1.put("g0gsdm",supplier);
                map1.put("g0anln1",assetNumber);

                ledgerList = sreEquipmentLedgerMapper.getSreDetailId(map1);
                    for (SreEquipmentLedger equipmentLedger: ledgerList) {

                        equipmentLedger.setEquipmentName(equipmentName);
                        equipmentLedger.setAssetNumber(assetNumber);
                        equipmentLedger.setEquipmentPrice(equipmentPrice);
                        equipmentLedger.setEquipmenNumber(equipmenNumber);
                        equipmentLedger.setDeclareUnit(declareUnit);
                        equipmentLedger.setDeclareDepartment(declareDepartment);
                        equipmentLedger.setDeclarePeople(declarePeople);

                    }
            }
        }

		PageInfo<SreEquipmentLedger> pageInfo = new PageInfo<SreEquipmentLedger>(ledgerList);
		System.out.println(">>>>>>>>>查询分页结果"+pageInfo.getList().size());
		LayuiTableData data = new LayuiTableData();
		data.setData(pageInfo.getList());
		Long total = pageInfo.getTotal();
		data.setCount(total.intValue());
		return data;
	}


	@Override
	public SreDetail detail(String id) {
		// TODO Auto-generated method stub
		return detailMapper.detail(id);
	}


	@Override
	public LayuiTableData getDetailLIVRW(LayuiTableParam param) {
		SimpleDateFormat yymm = new SimpleDateFormat("yyyy-MM");//设置日期格式
		String assetNumber=getTableParam(param,"assetNumber","");
		String supplier=getTableParam(param,"supplier","");
		Date dBefore = new Date();
		Calendar   calendar= Calendar.getInstance();
        calendar.setTime(dBefore);
        calendar.set(Calendar.MONTH,calendar.get(Calendar.MONTH)-1);
        System.out.println(calendar.getTime());
		SimpleDateFormat df = new SimpleDateFormat("yyyyMM");//设置日期格式
		System.out.println(df.format(calendar.getTime()));
		Map map=new HashMap();
		map.put("assetNumber", assetNumber);
		map.put("supplier", supplier);
		map.put("g0CALD", df.format(calendar.getTime()));
		//map.put("g0CALD", "201805");测试，暂时注释
		List<SreEquipmentLedger> sreequin  = sreEquipmentLedgerMapper.getDate(map);
		PageInfo<SreEquipmentLedger> pageInfo = new PageInfo<SreEquipmentLedger>(sreequin);
		System.out.println(">>>>>>>>>查询分页结果"+pageInfo.getList().size());
		LayuiTableData data = new LayuiTableData();
		data.setData(pageInfo.getList());
		Long total = pageInfo.getTotal();
		data.setCount(total.intValue());
		return data;
	}
}
