package gprs.com.service.Imp;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;

import gprs.com.entity.Electrical;
import gprs.com.mapper.ElectricalMapper;
import gprs.com.service.ElectricalServiceInter;
import gprs.tool.resulte.MyResult;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author ejar
 * @since 2018-06-11
 */

/**
 * 服务实现类
 * @author 简洁万岁
 * @since 2019-07-19
 */
@Service
public class ElectricalServiceImp extends ServiceImpl<ElectricalMapper, Electrical> implements ElectricalServiceInter {

	@Autowired
	ElectricalMapper electricalMapper;

	@Override
	public Object electricalList(String clientid, Integer state, Integer pageSize, Integer pageNo) {
		pageNo = pageNo == null ? 1 : pageNo;
		pageSize = pageSize == null ? 10 : pageSize;
		List<Electrical> electricals = electricalMapper.selecteleList(--pageNo * pageSize, pageSize, clientid, state);
		int t = electricals == null ? 0 : electricalMapper.selecteleListPage(clientid, state);
		Map<String, Object> map = new HashMap<>();
		map.put("pageNo", ++pageNo);
		map.put("pageSize", pageSize);
		map.put("pageTotal", (t - 1 + pageSize) / pageSize);
		map.put("total", t);
		map.put("list", electricals);
		return MyResult.success(map);
	}
	
	/*@Override
	public Object electricalList(String clientid, Integer state, Integer pageSize, Integer pageNo) {
		pageNo = pageNo==null?1:pageNo;
		pageSize = pageSize==null?10:pageSize;
		int start = (pageNo-1)*pageSize;
		List<Electrical> electricals = electricalMapper.selecteleList(start,pageSize,clientid,state);
		int t=0;
		if(electricals!=null){
			t = electricalMapper.selecteleListPage(clientid,state);
		}
		Map<String, Object> map = new HashMap<>();
		map.put("pageNo",pageNo);
		map.put("pageSize",pageSize);
		map.put("pageTotal",(t-1+pageSize)/pageSize);
		map.put("total", t);
		map.put("list", electricals);
		return MyResult.success(map);
	}*/

}
