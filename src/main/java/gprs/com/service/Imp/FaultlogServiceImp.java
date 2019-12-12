package gprs.com.service.Imp;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;

import gprs.com.entity.Faultlog;
import gprs.com.mapper.FaultlogMapper;
import gprs.com.mapper.TokenMapper;
import gprs.com.mapper.UserMapper;
import gprs.com.service.FaultlogServiceInter;
import gprs.tool.resulte.MyResult;

/**
 * <p>
 *  服务实现类
 *  故障
 * </p>
 *
 * @author ejar
 * @since 2018-05-28
 */
@Service
public class FaultlogServiceImp extends ServiceImpl<FaultlogMapper, Faultlog> implements FaultlogServiceInter {

	@Autowired
	FaultlogMapper faultlogMapper;
	@Autowired
	UserMapper userMapper;
	@Autowired
	TokenMapper tokenMapper;
	
	@Override
	public Object faulyloglist(Integer pageNo, Integer pageSize,String equipmentNumber) {
		pageNo = pageNo==null?1:pageNo;
		pageSize = pageSize==null?10:pageSize;
		int start = (pageNo-1)*pageSize;
		List<Faultlog> faultlogs = faultlogMapper.faulyloglist(start,pageSize,equipmentNumber);
		int t = 0;
		if(faultlogs.size()>0){
			t = faultlogMapper.faulyloglistPage(equipmentNumber);
		}
		Map<String, Object> map = new HashMap<>();
		map.put("pageNo", pageNo);
		map.put("pageSize", pageSize);
		map.put("pageTatol", (t-1+pageSize)/pageSize);
		map.put("Tatol", t);
		map.put("data", faultlogs);
		faultlogMapper.updatefaulyIsread(equipmentNumber);
		return MyResult.success(map);
	}
	
	@Override
	public Object deletfaulylog(String id){
		if(id==null || "".equals(id)){
			return MyResult.error("参数错误");
		}
		String[] ids = id.split(",");
		for(int i=0;i<ids.length;i++){
			faultlogMapper.deletfaulylog(Integer.valueOf(ids[i]));
		}
		return MyResult.success();
	}

	//用户故障总数
	@Override
	public Object getFaultlogNumber(String token) {
		Integer userId = tokenMapper.getUserIdbyToken(token);
		List<Faultlog> faulylogList = faultlogMapper.faulylogListByUserId(userId);
		Integer readFault = 0;
		Integer unreadFault = 0;
		Integer totalFault = faulylogList.size();
		for (Faultlog faultlog : faulylogList) {
			if (faultlog.getIsread() == 2) {
				readFault++;
			}else {
				unreadFault++;
			}
		}
		Map<String, Integer> faultlogNumber = new HashMap<>();
		faultlogNumber.put("readFault", readFault);
		faultlogNumber.put("unreadFault", unreadFault);
		faultlogNumber.put("totalFault", totalFault);
		return MyResult.success(faultlogNumber);
	}

	@Override
	public Object deletfaulylogbyMac(String mac) {
		if(mac==null || "".equals(mac)){
			return MyResult.error("参数错误");
		}
		faultlogMapper.deletfaulylogbyMac(mac);
		return MyResult.success();
	}
}
