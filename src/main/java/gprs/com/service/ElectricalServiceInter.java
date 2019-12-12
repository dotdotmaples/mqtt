package gprs.com.service;

import com.baomidou.mybatisplus.service.IService;

import gprs.com.entity.Electrical;

/**
 * {@link gprs.com.service.Imp.ElectricalServiceImp}
 * <p>
 *  服务类
 * </p>
 *
 * @author ejar
 * @since 2018-06-11
 */
public interface ElectricalServiceInter extends IService<Electrical> {

	Object electricalList(String clientid, Integer state, Integer pageSize, Integer pageNo);
	
}
