package gprs.com.service;

import com.baomidou.mybatisplus.service.IService;

import gprs.com.entity.Faultlog;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author ejar
 * @since 2018-05-28
 */
public interface FaultlogServiceInter extends IService<Faultlog> {

	//故障列表
	Object faulyloglist(Integer pageNo, Integer pageSize,String equipmentNumber);
	//删除故障
	Object deletfaulylog(String id);
	//获取故障数量
	Object getFaultlogNumber(String token);
	//清楚故障
	Object deletfaulylogbyMac(String mac);
	
}
