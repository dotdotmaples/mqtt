package gprs.com.service.Imp;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;

import gprs.com.entity.Equipmentlist;
import gprs.com.entity.Msglog;
import gprs.com.mapper.EquipmentlistMapper;
import gprs.com.mapper.FaultlogMapper;
import gprs.com.mapper.MsglogMapper;
import gprs.com.service.MsglogServiceInter;
import gprs.tool.MQTT.Server;
import gprs.tool.resulte.MyResult;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author ejar
 * @since 2018-05-28
 */
@Service
public class MsglogServiceImp extends ServiceImpl<MsglogMapper, Msglog> implements MsglogServiceInter {

	@Autowired
	MsglogMapper msglogMapper;
	@Autowired
	EquipmentlistMapper equipmentlistMapper;
	@Autowired
	FaultlogMapper faultlogMapper;
	
	
	@Override
	public Object newmsglog(String topic) {
		if(topic==null){
			return MyResult.error("参数错误！");
		}
		Msglog msglog = msglogMapper.newmsglog(topic);
		System.out.println(msglog.toString());
		System.out.println(msglog.getMsg().length());
		if(msglog!=null && msglog.getMsg().length()>100){
			msglog.setMsg(msglog.getMsg().substring(5));
			String mac = topic.substring(0,topic.indexOf("/")); 
			if (topic.toString().startsWith("outTopic") || topic.toString().startsWith("inTopic")) {
			    mac = topic.substring(topic.indexOf("/")+1); 
			}
			System.out.println(mac);
			Equipmentlist equipmentlist = equipmentlistMapper.selectbyMac(mac);
			if(equipmentlist!=null){
				msglog.setTopic(equipmentlist.getDevicename());
			}else{
				msglog.setTopic("未知");
			}
			Map<String,Object> map = new HashMap<>();
			map.put("eleset", equipmentlist.getEleset());
			map.put("msglop", msglog);
			return MyResult.success(map);
		}
		return MyResult.error();
	}
	@Override
	public Object getele(String mac) {
		if(mac==null){
			return MyResult.error("参数错误！");
		}
		Equipmentlist equipmentlist = equipmentlistMapper.selectbyMac(mac);
		//改变故障为已读
		faultlogMapper.updatefaulyIsread(mac);
		Map<String,Object> map = new HashMap<>();
		map.put("eleset", equipmentlist.getEleset());
		map.put("type", equipmentlist.getType());
		return MyResult.success(map);
	}


	@Override
	public Object forwardMsg(String mac,Double eleset) {
		System.out.println("mac===="+mac);
//		System.out.println("msg===="+msg);
		if(mac==null || "".equals(mac)){
			return MyResult.error("请输入设备地址");
		}
//		if(msg==null || msg.length()!=64){
//			return MyResult.error("消息的格式不对！");
//		}
		if(eleset==null){
			return MyResult.error("请输入电费");
		}
//		String topic = mac+"/inTopic";
		equipmentlistMapper.upadteEleset(mac, eleset);
//		System.out.println("中途=="+new Date().getTime());
//		Server server;
//		try {
//			server = new Server(topic);
//			server.sevserpush(topic, msg);
//		} catch (MqttException e) {
//			return MyResult.error("发送失败");
//		} catch (InterruptedException e) {
//			return MyResult.error("发送失败");
//		}
		return MyResult.success();
	}


	/**
	 * 替换参数
	 * @param sb
	 * @param lK_SET
	 * @param rC_SET
	 * @param uV_SET
	 * @param oV_SET
	 * @param trip_bit
	 * @return
	 */
	@SuppressWarnings("static-access")
	public String hexzhuan(StringBuilder sb,Integer lK_SET, Integer rC_SET, Integer uV_SET, Integer oV_SET,
			String trip_bit){
		StringBuilder addOrder = new StringBuilder(sb.replace(2,4,"03").toString());
		String hex = lK_SET.toHexString(lK_SET);
		StringBuilder addlk = new StringBuilder(addOrder.replace(32,34,hex).toString());
		System.out.println(addlk);
		String hex1 = rC_SET.toHexString(rC_SET);
		System.out.println(hex1);
		StringBuilder addrc = new StringBuilder(addlk.replace(34,36,hex1).toString());
		String hex2 = uV_SET.toHexString(uV_SET);
		if(hex2.length()==2){
			hex2=hex2+"00";
		}else if(hex2.length()==3){
			hex2="0"+hex2;
		}
		System.out.println(hex2);
		StringBuilder adduv = new StringBuilder(addrc.replace(36,40,hex2).toString());
		String hex3 = oV_SET.toHexString(oV_SET);
		if(hex3.length()==2){
			hex3=hex2+"00";
		}else if(hex3.length()==3){
			hex3="0"+hex3;
		}
		System.out.println(hex3);
		StringBuilder addov = new StringBuilder(adduv.replace(40,44,hex3).toString());
		String hex4 = Integer.toString (Integer.parseInt (trip_bit, 2), 16);
		System.out.println(hex4);
		if(hex4.length()==1){
			hex4="0"+hex4;
		}
		StringBuilder newMsg = new StringBuilder(addov.replace(44,46,hex4).toString());
		return newMsg.toString();
	}
	
	@Override
	public Object forwardMsg(String mac, Integer lK_SET, Integer rC_SET, Integer uV_SET, Integer oV_SET,
			String trip_bit,String deviceName) {
		if(mac==null){
			return MyResult.error("mac不能为空");
		}
		
		if(deviceName!=null && !"".equals(deviceName)){
			equipmentlistMapper.updateEquip(mac,deviceName);
		}
		
		Msglog msglog = msglogMapper.newmsglog(mac+"/outTopic");
		if(msglog!=null && msglog.getMsg().length()>48){
			StringBuilder sb = new StringBuilder(msglog.getMsg().substring(5,69));
			String msg = hexzhuan(sb, lK_SET, rC_SET, uV_SET, oV_SET, trip_bit);
			String topic2 = mac+"/inTopic";
			System.out.println("topic2===="+topic2);
			System.out.println("msg===="+msg);
			Server server;
			try {
				server = new Server(topic2);
				server.message = new MqttMessage();
		    	server.message.setQos(0);
		    	server.message.setRetained(false);
		        server.message.setPayload(toBytes(msg));
		    	server.publish(server.topic , server.message);
		        Thread.sleep(1000); 
//				server.sevserpush(topic2, msg);
			} catch (MqttException e) {
//				return MyResult.error("发送失败");
			} catch (InterruptedException e) {
//				return MyResult.error("发送失败");
			}
		}
		return MyResult.success();
	}
    //字符串转16进制数组
    public static byte[] toBytes(String str) {
        if(str == null || str.trim().equals("")) {
            return new byte[0];
        }

        byte[] bytes = new byte[str.length() / 2];
        for(int i = 0; i < str.length() / 2; i++) {
            String subStr = str.substring(i * 2, i * 2 + 2);
            bytes[i] = (byte) Integer.parseInt(subStr, 16);
        }
        return bytes;
    }

	/**
	 * 发送指令
	 */
	@Override
	public Object instructions(String mac, Integer order) {
		if(mac==null || "".equals(mac)){
			return MyResult.error("mac不能为空");
		}
		if(order==null || "".equals(order)){
			return MyResult.error("指令不能为空");
		}
		String neworder =Integer.toHexString(order);
		String topic2 = mac+"/inTopic";
		
		if(neworder.length()==1){
			neworder="0"+neworder;
		}
		
		Msglog msglog = msglogMapper.newmsglog(mac+"/outTopic");
		StringBuilder newmsg = new StringBuilder(msglog.getMsg().substring(5,69));
		if(newmsg.length()!=64){
			return MyResult.error("发送失败！");
		}
		StringBuilder addOrder = new StringBuilder(newmsg.replace(2,4,neworder).toString());
		Server server;
		try {
			server = new Server(topic2);
			server.sevserpush(topic2, addOrder.toString());
		} catch (MqttException e) {
			return MyResult.error("发送失败");
		} catch (InterruptedException e) {
			return MyResult.error("发送失败");
		}
		return MyResult.success();
	}


	@Override
	public Object msgloglist(String mac, Integer pageSize, Integer pageNo,String searchdate) {
		pageNo = pageNo==null?1:pageNo;
		pageSize = pageSize==null?20:pageSize;
		int start = (pageNo-1)*pageSize;
		String topic1=null;
		String topic2=null;
		if(mac!=null && !"".equals(mac)){
			 topic1 = mac+"/inTopic";
			 topic2 = mac+"/outTopic";
		}
		List<Msglog> msglogs = msglogMapper.selectByall(start,pageSize,searchdate,topic1,topic2);
		int t = 0;
		if(msglogs.size()>0){
			t = msglogMapper.selectByallPage(searchdate,topic1,topic2);
		}
		Map<String, Object> map= new HashMap<>();
		map.put("list", msglogs);
		map.put("pageNo", pageNo);
		map.put("pageSize", pageSize);
		map.put("pageTotal", (t+pageSize-1)/pageSize);
		
		map.put("total", t);
		return MyResult.success(map);
	}


	@Override
	public Object deletemsglog(String id) {
		if(id==null || "".equals(id)){
			MyResult.error("参数错误");
		}
		String[] ids = id.split(",");
		for(int i=0;i<ids.length;i++){
			msglogMapper.deletemsglog(Integer.valueOf(ids[i]));
		}
		return MyResult.success();
	}
}
