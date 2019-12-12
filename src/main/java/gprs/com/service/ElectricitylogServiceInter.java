package gprs.com.service;


import com.baomidou.mybatisplus.service.IService;

import gprs.com.entity.Electricitylog;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author ejar
 * @since 2018-05-28
 */
public interface ElectricitylogServiceInter extends IService<Electricitylog> {

	Object electricityloglist(Integer pageNo, Integer pageSize, String equipmentNumber,String searchTime);
	
}
