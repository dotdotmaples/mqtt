package gprs.com.service;

import com.baomidou.mybatisplus.service.IService;

import gprs.com.entity.Equipmentlist;
import gprs.com.exception.MyException;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author ejar
 * @since 2018-06-11
 */
public interface EquipmentlistServiceInter extends IService<Equipmentlist> {

	Object equipmentlist(String token,String wifimac);

	Object addEquipmentlist(String token, String mac,String wifiMac,Integer type,String initmac,String wifipas);

	Object updateEquipmentName(String token, Integer id, String name);

	Object deleteEquipment(String token, Integer id);

	Object updateUmengtoken(String token, String umToken);

	Object closeequipment(String token, String mac);

	Object openequipment(String token, String mac);

	Object queryWifipas(String token, String wifimac);

	/**
	 * @method listGprsWait
	 * @apply 查询三分钟内待匹配设备
	 * @return  
	 */
	Object listGprsWait(String token);
	
	/**
	 * @method bindGprs
	 * @apply 根据用户凭证和设备imei绑定设备（gprs）
	 * @param token
	 * @param imei
	 * @return
	 * @throws MyException 
	 */
	Object bindGprs(String token, String imei) throws MyException;
	
	Object flushSignal(String[] imei);
	 
}
