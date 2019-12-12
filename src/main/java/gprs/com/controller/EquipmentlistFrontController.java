package gprs.com.controller;


import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import gprs.com.exception.MyException;
import gprs.com.service.EquipmentlistServiceInter;
import gprs.tool.resulte.MyResult;

/**
 * <p>
 *  设备用户
 * </p>
 *
 * @author ejar
 * @since 2018-06-11
 */
@Controller
public class EquipmentlistFrontController {
	
	@Resource
	EquipmentlistServiceInter equipmentlistServiceInter;
	
	/**
	 * 用户设备列表
	 * @param token
	 * @param wifimac wifi mac
	 * @return
	 */
	@RequestMapping("front/equipmentlist")
	@ResponseBody
	public Object equipmentlist(String token,String wifimac){
		return equipmentlistServiceInter.equipmentlist(token,wifimac);
	}
	
	/**
	 * 关闭共享
	 * @param token
	 * @return
	 */
	@RequestMapping("front/closeequipment")
	@ResponseBody
	public Object closeequipment(String token,String mac){
		return equipmentlistServiceInter.closeequipment(token,mac);
	}
	/**
	 * 开启共享
	 * @param token
	 * @return
	 */
	@RequestMapping("front/openequipment")
	@ResponseBody
	public Object openequipment(String token,String mac){
		return equipmentlistServiceInter.openequipment(token,mac);
	}
	
	/**
	 * 修改设备名称
	 * @param token
	 * @param id 用户设备关联表的id
	 * @param name
	 * @return
	 */
	@RequestMapping("front/updateEquipmentName")
	@ResponseBody
	public Object updateEquipmentName(String token,Integer id,String name){
		return equipmentlistServiceInter.updateEquipmentName(token,id,name);
	}
	
	/**
	 * 修改设备名称（修改优盟唯一token？）
	 * @param token
	 * @param name
	 * @return
	 */
	@RequestMapping("front/updateUmengtoken")
	@ResponseBody
	public Object updateUmengtoken(String token,String umToken){
		return equipmentlistServiceInter.updateUmengtoken(token,umToken);
	}
	
	/**
	 * 删除设备
	 * @param token
	 * @param id 设备用户关联表id
	 * @return
	 */
	@RequestMapping("front/deleteEquipment")
	@ResponseBody
	public Object deleteEquipment(String token,Integer id){
		return equipmentlistServiceInter.deleteEquipment(token,id);
	}
	
	/**
	 * 添加用户设备列表
	 * @param token
	 * @param mac   设备mac（转码） 引文逗号隔开
	 * @param wifimac 路由器mac地址  
	 * @param type  是否共享
	 * @param initmac  原始mac(未转码前)
	 * @param wifipas  wifi密码
	 * @return
	 */
	@RequestMapping("front/addEquipmentlist")
	@ResponseBody
	public Object addEquipmentlist(String token,String mac,String wifimac,Integer type,String initmac,String wifipas){
		return equipmentlistServiceInter.addEquipmentlist(token,mac,wifimac,type,initmac,wifipas);
	}
	
	/**
	 * 查看wifi密码
	 * @param wifimac  wifi mac
	 * @return
	 */
	@RequestMapping("front/queryWifipas")
	@ResponseBody
	public Object queryWifipas(String token,String wifimac){
		return equipmentlistServiceInter.queryWifipas(token,wifimac);
	}
	
	/**
	 * @method listGprsWait
	 * @apply 查询待绑定gprs设备
	 * @return 
	 */
	@RequestMapping("front/listGprsWait")
    @ResponseBody
	public Object listGprsWait(String token) {
	    return equipmentlistServiceInter.listGprsWait(token);
	}
	
	/**
	 * @method bindGprs
	 * @apply 根据用户凭证和设备imei绑定设备（gprs）
	 * @param token
	 * @param imei
	 * @return
	 */
	@RequestMapping("front/bindGprs")
    @ResponseBody
	public Object bindGprs(String token, String imei) {
	    try {
            return equipmentlistServiceInter.bindGprs(token, imei);
        } catch (MyException e) {
            return MyResult.error(e.getMessage());
        }
	}
	
	/**
	 * @method flushSignal
	 * @apply 根据imei编号，查询设备信号强度
	 * @param imei
	 * @return
	 */
	@RequestMapping("front/flushSignal")
    @ResponseBody
	public Object flushSignal(String[] imei) {
	    return equipmentlistServiceInter.flushSignal(imei);
	}
	
	
	
}
