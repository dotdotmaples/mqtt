package gprs.com.service.Imp;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;

import gprs.com.entity.Electricitylog;
import gprs.com.mapper.ElectricitylogMapper;
import gprs.com.service.ElectricitylogServiceInter;
import gprs.tool.resulte.MyResult;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author ejar
 * @since 2018-05-28
 */

/**
 * 服务实现类
 * @author 简洁万岁
 * @since 2019-07-19
 */
@Service
public class ElectricitylogServiceImp extends ServiceImpl<ElectricitylogMapper, Electricitylog> implements ElectricitylogServiceInter {

	@Autowired
	ElectricitylogMapper electricitylogMapper;
	
	@Override
	public Object electricityloglist(Integer pageNo, Integer pageSize, String equipmentNumber,String searchTime) {
		List<Electricitylog> electricitylogs = electricitylogMapper.selectElectricityloglist(equipmentNumber,searchTime);
		Map<String, Object> map = new HashMap<>();
		map.put("data", electricitylogs);
		return MyResult.success(map);
	}
	
	/*@Override
	public Object electricityloglist(Integer pageNo, Integer pageSize, String equipmentNumber,String searchTime) {
//		分页方法（不用）
//		pageNo = pageNo==null?1:pageNo;
//		pageSize = pageSize==null?10:pageSize;
//		int start = (pageNo-1)*pageSize;
		List<Electricitylog> electricitylogs = electricitylogMapper.selectElectricityloglist(equipmentNumber,searchTime);
//		int t =0;
//		if(electricitylogs.size()>0){
//			t = electricitylogMapper.selectElectricityloglistPage(equipmentNumber,searchTime);
//		}
		Map<String, Object> map = new HashMap<>();
//		map.put("pageNo", pageNo);
//		map.put("pageSize", pageSize);
//		map.put("pageTotal", (t-1+pageSize)/pageSize);
//		map.put("total", t);
		map.put("data", electricitylogs);
		return MyResult.success(map);
		
//		//返回24小时前的数据
//		//当前时间
//		String newdate = DateUtils.nowDateTimeSS();
//		//24小时前
//		String yesterday = DateUtils.getYesterday();
//		
//		List<Electricitylog> electricitylogs = electricitylogMapper.selectElectricityloglist(equipmentNumber,newdate,yesterday);
//		Map<String, Object> map = new HashMap<>();
//		map.put("data", electricitylogs);
//		return MyResult.success(map);
		
	}*/
	
}
