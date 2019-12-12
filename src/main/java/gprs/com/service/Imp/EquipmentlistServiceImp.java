package gprs.com.service.Imp;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;

import gprs.com.entity.Electrical;
import gprs.com.entity.Equipmentlist;
import gprs.com.entity.GprsOnlineInfo;
import gprs.com.entity.UserEquipmentModle;
import gprs.com.entity.Wifipas;
import gprs.com.exception.MyException;
import gprs.com.mapper.ElectricalMapper;
import gprs.com.mapper.ElectricitylogMapper;
import gprs.com.mapper.EquipmentlistMapper;
import gprs.com.mapper.FaultlogMapper;
import gprs.com.mapper.GprsOnlineInfoMapper;
import gprs.com.mapper.TokenMapper;
import gprs.com.mapper.UserMapper;
import gprs.com.service.EquipmentlistServiceInter;
import gprs.tool.MQTT.JdbcClient;
import gprs.tool.MQTT.MqttSession;
import gprs.tool.MQTT.Params;
import gprs.tool.dateUitle.DateUtils;
import gprs.tool.resulte.MyResult;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author ejar
 * @since 2018-06-11
 */
@Service
public class EquipmentlistServiceImp extends ServiceImpl<EquipmentlistMapper, Equipmentlist> implements EquipmentlistServiceInter {

	@Autowired
	JdbcClient jdbcClient;
	@Autowired
	EquipmentlistMapper equipmentlistMapper;
	@Autowired
	UserMapper userMapper;
	@Autowired
	TokenMapper tokenMapper;
	@Autowired
	ElectricalMapper electricalMapper;
	@Autowired
	FaultlogMapper faultlogMapper;
	@Autowired
	GprsOnlineInfoMapper gprsOnlineInfoMapper;
	@Autowired
	ElectricitylogMapper electricitylogMapper;
	
	/**
	 * 用户设备列表
	 */
	@Override
	public Object equipmentlist(String token,String wifimac) {
		Integer userId = tokenMapper.getUserIdbyToken(token);
		List<Equipmentlist> equipmentlist = new ArrayList<>();
		if(wifimac!=null && !"".equals(wifimac.trim())){
			equipmentlist = equipmentlistMapper.selectByuserId(userId,wifimac);
		}else{
			equipmentlist = equipmentlistMapper.selectByMilde(userId);
		}
		for(Equipmentlist equipment : equipmentlist){
			//判断是否在线
			Electrical electrical = electricalMapper.selectbyclientidandstate(equipment.getMac()); 
			// 1在线，2离线
			if (electrical == null) {
			    equipment.setState(2);
			    equipment.setEquipmentType(Params.EQUIPMENT_WIFI);
			} else {
			    if(electrical.getState()==2){
	                equipment.setState(2);
	                equipment.setEquipmentType(electrical.getType());
	            }else{
	                equipment.setState(1);
	                equipment.setEquipmentType(electrical.getType());
	            }
			}
			//更新中间表
			UserEquipmentModle userEquipmentModle = equipmentlistMapper.selectBymidel(userId,equipment.getId());
			if(userEquipmentModle==null){
				String name = "AFCI_";
				if(equipment.getInitmac()!=null && !"".equals(equipment.getInitmac())){
					name+= equipment.getInitmac().substring(equipment.getInitmac().lastIndexOf(":")+1).toUpperCase();
				}else{
					name += "MAC";
				}
				equipmentlistMapper.insertMidel(userId,equipment.getId(),name);
				equipment.setDevicename(equipment.getMac());
			}else{
				equipment.setDevicename(userEquipmentModle.getName());
			}
			//添加故障数量
			int faultyNumber =  faultlogMapper.getNumberFault(equipment.getMac());
			equipment.setFaultynumber(faultyNumber);
		}
		return MyResult.success(equipmentlist);
	}

	
	/**
	 * 添加设备
	 */
	@Override
	public Object addEquipmentlist(String token, String jsonmac,String wifiMac,Integer type,String initmac,String wifipas) {
		System.out.println("token:" + token + "，jsonmac:" + jsonmac + "，wifiMac:" + wifiMac + "，type:" + type + "，initmac:" + initmac + "，wifipas:" + wifipas);
		if(jsonmac==null || "".equals(jsonmac)){
			return MyResult.error("请传入mac");
		}
		if(initmac==null || "".equals(initmac)){
			return MyResult.error("请传入initmac");
		}
		String [] mac = jsonmac.split(",");
		
		if(wifiMac==null || "".equals(wifiMac)){
			return MyResult.error("请传入路由设备mac");
		}
		/*if(wifipas==null || "".equals(wifipas)){
			return MyResult.error("请传入wifi密码");
		}*/
		if(type==null){
			type=1;
		}
		
		//更新、保存WIFI信息
		Wifipas wifipas2 = equipmentlistMapper.selectBywifiMac(wifiMac);
		if(wifipas2==null){
			equipmentlistMapper.insertWifiMac(wifiMac,wifipas);
		}else{
			if(!wifipas2.getPassword().equals(wifipas)){
				equipmentlistMapper.updateWifiMac(wifipas,wifipas2.getId());
			}
		}
		
		for(int i=0;i<mac.length;i++){
			Integer userId = tokenMapper.getUserIdbyToken(token);
			Equipmentlist equipmentlist = new Equipmentlist();
			equipmentlist.setUserId(userId);
			equipmentlist.setMac(mac[i]);
			equipmentlist.setType(type);
			equipmentlist.setInitmac(initmac);
			Equipmentlist equipmentlist1 = equipmentlistMapper.selectOne(equipmentlist);
			if(equipmentlist1==null){
				equipmentlist.setWifimac(wifiMac);
				equipmentlist.setLastTime(new Date());
				equipmentlistMapper.insert(equipmentlist);
			}else{
				equipmentlist.setWifimac(wifiMac);
				Equipmentlist equipmentlist2 = equipmentlistMapper.selectOne(equipmentlist);
				if(equipmentlist2==null){
					equipmentlist.setId(equipmentlist1.getId());
					equipmentlistMapper.updateById(equipmentlist);
				}
			}
			// 添加进缓存（不然收到消息不会存入数据库）
			if (!MqttSession.wifiAll.contains(mac[i])) {
				MqttSession.wifiAll.add(mac[i]);
				//新设备，需要发送01指令
				jdbcClient.pushMsg(mac[i] + "/inTopic");
			}
		}
		return MyResult.success();
	}

	@Override
	public Object updateEquipmentName(String token, Integer id, String name) {
		Integer userId = tokenMapper.getUserIdbyToken(token);
		equipmentlistMapper.updateModel(userId,id,name);
		return MyResult.success();
	} 
	
	@Override
	@Transactional(rollbackFor = MyException.class)
	public Object deleteEquipment(String token, Integer id) {
		Integer userId = tokenMapper.getUserIdbyToken(token);
		//查找需要删除的设备信息
		Equipmentlist equipmentlist = equipmentlistMapper.selectById(id);
		if (equipmentlist == null) {
			return MyResult.error("设备不存在");
		}
		String mac = equipmentlist.getMac();
		//删除相关设备的推送token
		equipmentlistMapper.deleteEquipment(userId,id);
		//删除相关设备的关联关系
		equipmentlistMapper.deleteEquipmentbyId(id, userId);
		//从equipmentlist查找是否还存在改设备记录，不存在则将gprs_online_info的state设为0
		equipmentlist = equipmentlistMapper.selectbyMac(mac);
		if (equipmentlist == null) {
			//设备没有与用户有关联
			//更新待绑定表
			gprsOnlineInfoMapper.updateStateByImei(mac, 0);
			//清除缓存
			
			//不能如此在迭代的时候删除元素java.util.ConcurrentModificationException: null
			//下标指向异常
//			MqttSession.gprsAll.forEach(x -> {
//				if (x.equals(mac)) {
//					MqttSession.gprsAll.remove(mac);
//				}
//			});
			Iterator<String> iter = MqttSession.gprsAll.iterator();
			while(iter.hasNext()) {
				String m = iter.next();
				if (m.equals(mac)) {
					iter.remove();
				}
			}
		}
		
		//二改内容
//		Equipmentlist equipmentlist = equipmentlistMapper.selectById(id);
//		System.out.println("删除设备：" + equipmentlist.getMac() + "   该设备" + (equipmentlist.getType() == 1 ? "开启" : "关闭") + "了共享");
//		//判断这个设备是不是分享出来的设备
//		//判断是否开启共享
//		if (equipmentlist.getType() == 1) {
//			//设备开启了共享功能，但不一定就是共享出来的设备
//			//获取mac一致的equipmentlist数据
//			List<Equipmentlist> equimentlists = equipmentlistMapper.listByMac(equipmentlist.getMac());
//			System.out.print("是否存在共享设备：");
//			//减少不必要的内存消耗
//			if (equimentlists != null && equimentlists.size() > 1) {
//				System.out.println("是, 共享设备数量：" + (equimentlists.size() - 1));
//				for (Equipmentlist equipmentlist2 : equimentlists) {
//					if (equipmentlist2.getLastTime().before(equipmentlist.getLastTime())) {
//						System.out.println("用户：" + userId + "下的设备：" + equipmentlist.getMac() + "是共享后添加的设备");
//						//存在更早的设备(该设备是共享出来的)；仅删除equipmentlist表
//						equipmentlistMapper.deleteEquipmentbyId(id, userId);
//						return MyResult.success();
//					}
//				}
//			}else {
//				System.out.println("否");
//			}
//		}
//		//不是被共享出来的设备
//		equipmentlistMapper.deleteEquipmentbyId(id, userId);
//		gprsOnlineInfoMapper.updateStateByImei(equipmentlist.getMac(), 0);
//		electricalMapper.deleteByClientId(equipmentlist.getMac());
//		MqttSession.gprsAll.remove(equipmentlist.getMac());
		
		//一改内容
//		equipmentlistMapper.deleteEquipmentbyId(id, userId);
////		equipmentlistMapper.deleteEquipment(userId,id);
//		gprsOnlineInfoMapper.updateStateByImei(equipmentlist.getMac(), 0);
//		electricalMapper.deleteByClientId(equipmentlist.getMac());
//		MqttSession.gprsAll.remove(equipmentlist.getMac());
		return MyResult.success();
	}
	
	@Override
	public Object updateUmengtoken(String token, String umToken) {
		Integer userId = tokenMapper.getUserIdbyToken(token);
		equipmentlistMapper.updateUmengtoken(userId,umToken);
		return MyResult.success();
	}

	@Override
	public Object closeequipment(String token, String mac) {
		Integer type = 2;
		equipmentlistMapper.updatebymactype(mac,type);
		return MyResult.success();
	}

	@Override
	public Object openequipment(String token, String mac) {
		Integer type = 1;
		equipmentlistMapper.updatebymactype(mac,type);
		return MyResult.success();
	}


	@Override
	public Object queryWifipas(String token, String wifimac) {
		Wifipas wifipas = equipmentlistMapper.selectBywifiMac(wifimac);
		if(wifipas==null){
			return MyResult.error();
		}
		return MyResult.success(wifipas);
	} 
	
	/**
	 *  查询三分钟内待匹配设备
	 * @return  List<String>
	 */
	public Object listGprsWait(String token) {
	    // 已匹配设备（除去自己已匹配）
	    Integer userId = tokenMapper.getUserIdbyToken(token);
	    List<String> selectImeiList = gprsOnlineInfoMapper.selectImeiList(Params.GPRS_CONNECT_SUCCESS, null, userId);
	    // 三分钟待匹配设备
//	    List<String> selectImeiList2 = gprsOnlineInfoMapper.selectImeiList(Params.GPRS_CONNECT_WAIT, DateUtils.nowDateTimeSS(new Date(new Date().getTime()-180000)), null);
	    List<String> selectImeiList2 = gprsOnlineInfoMapper.selectImeiList(Params.GPRS_CONNECT_WAIT, DateUtils.nowDateTimeSS(new Date()), null);
	    Map<String, Object> map = new HashMap<>();
	    map.put("success_connect", selectImeiList);
	    map.put("wait_connect", selectImeiList2);
	    return MyResult.success(map);
	}
	
	@Transactional(rollbackFor = MyException.class)
	public Object bindGprs(String token, String imei) throws MyException {
	    if (null == imei || imei.trim().length() == 0) {
	        throw new MyException("请填写设备MAC");
	    }
	    try {
	        List<GprsOnlineInfo> selectListByImei = gprsOnlineInfoMapper.selectListByImei(imei);
	        if (null == selectListByImei || selectListByImei.size() < 1) {
	            throw new MyException("设备不存在");
	        }
	        //检查设备是否开启共享(存在该设备时条件成立)
		    Equipmentlist equipmentlist = equipmentlistMapper.selectbyMac(imei);
		    if (equipmentlist != null) {
		    	if (equipmentlist.getType() == 2) {
		    		//该设备关闭了共享
		    		System.out.println(equipmentlist.toString());
		    		System.out.println("绑定设备时设备：" + equipmentlist.getMac() + "关闭了共享");
		    		return MyResult.error(501, null);
				}
			}
	        // 用户id
	        Integer userId = tokenMapper.getUserIdbyToken(token);
	        // 绑定关联表
	        equipmentlist = new Equipmentlist();
	        equipmentlist.setUserId(userId);
	        equipmentlist.setMac(imei);
	        
	        Equipmentlist equipmentlist1 = equipmentlistMapper.selectOne(equipmentlist);
	        if(equipmentlist1==null){
	            equipmentlist.setLastTime(new Date());
	            equipmentlist.setType(1);
	            equipmentlistMapper.insert(equipmentlist);
	            System.out.println("成功与设备：" + equipmentlist.getMac() + "建立关联关系");
	        }else{
	        	System.out.println("该用户绑定了该设备");
	        	return MyResult.error(502, null);
	        	/*System.out.println(equipmentlist.toString());
	            equipmentlist.setId(equipmentlist1.getId());
	            equipmentlistMapper.updateById(equipmentlist);*/
	        }
	        
	        // 查询当前设备是否已绑定（如已绑定，则已添加过设备信息）
	        // gprsOnlineInfo表中该设备不在线或者不存在时，添加数据到gprsOnlineInfo和electrical表并添加和缓存
	        List<String> selectImeiList = gprsOnlineInfoMapper.selectImeiList(Params.GPRS_CONNECT_SUCCESS, null, null);
	        if (!selectImeiList.contains(imei)) {
	            // 添加设备，上下
	            /*Electrical electrical1 = new Electrical();
	            electrical1.setClientid(imei);
	            electrical1.setState(1);
	            electrical1.setOnlineDate(DateUtils.nowDateTimeSS());
	            electrical1.setLastOnlineDate(DateUtils.nowDateTimeSS());
	            electrical1.setType(Params.EQUIPMENT_GPRS);
	            electricalMapper.insert(electrical1);*/
	            // 修改带绑定状态
	            gprsOnlineInfoMapper.insertOrUpdateOne(imei, DateUtils.nowDateTimeSS(), Params.GPRS_CONNECT_SUCCESS);
	            // 添加缓存
	            if (!MqttSession.gprsAll.contains(imei)) {
	            	System.out.println("新设备");
	            	MqttSession.gprsAll.add(imei);
	            	System.out.println("topic = " + "inTopic/" + imei);
					jdbcClient.pushMsg("inTopic/" + imei);
					System.out.println("发送完成");
				}else {
					System.out.println("该设备已存在");
				}
	        }
	    } catch (Exception e) {
	        throw new MyException(e.getMessage());
	    }
        return MyResult.success();
	}
	
	
	public Object flushSignal(String[] imei) {
	    if (null == imei || imei.length == 0) {
	        return MyResult.error("缺少设备参数");
	    }
	    // 查询
	    List<Map<String, Object>> selectSignalByMac = electricitylogMapper.selectSignalByMac(Arrays.asList(imei), Params.EQUIPMENT_GPRS);
	    return MyResult.success(selectSignalByMac);
	}
	

	
}