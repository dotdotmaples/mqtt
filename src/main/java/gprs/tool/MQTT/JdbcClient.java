package gprs.tool.MQTT;

import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;

import org.eclipse.paho.client.mqttv3.MqttException;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import gprs.com.entity.Analysis;
import gprs.com.entity.AnalysisGprs;
import gprs.com.entity.Electrical;
import gprs.com.entity.Electricitylog;
import gprs.com.entity.Faultlog;
import gprs.com.entity.Msglog;
import gprs.com.entity.UserEquipmentModle;
import gprs.com.mapper.ElectricalMapper;
import gprs.com.mapper.ElectricitylogMapper;
import gprs.com.mapper.FaultlogMapper;
import gprs.com.mapper.GprsOnlineInfoMapper;
import gprs.com.mapper.MsglogMapper;
import gprs.tool.dateUitle.DateUtils;
import gprs.tool.youmeng.Demo;

@Component
public class JdbcClient {

	@Autowired
	private MsglogMapper msglogMapper;
	@Autowired
	private FaultlogMapper faultlogMapper;
	@Autowired
	private ElectricitylogMapper electricitylogMapper;
	@Autowired
	private ElectricalMapper electricalMapper;
	@Autowired
	private GprsOnlineInfoMapper gprsOnlineInfoMapper;

	public static JdbcClient jdbcClient;

	private final static String TAG = "JdbcClient: ";

	@PostConstruct
	public void init() {
		jdbcClient = this;
	}

	/**
	 * 保存所有消息
	 * 
	 * @param topic
	 * @param msg
	 */
	public void operationJdbc(String topic, String msg, Integer type) {
		Msglog msglog = new Msglog();
		msglog.setCreatTime(new Date());
		msglog.setTopic(topic);
		msglog.setMsg(msg);
		msglog.setType(type); // 设备类型 wifi/gprs
		int rows = jdbcClient.msglogMapper.insert(msglog);
		System.out.println("全部消息新增一条消息：" + rows);
		MsgOperation(topic, msg, type);
	}
	
	public boolean pushMsg(String topic) {
//		String topic = clientid;
		//让设备提交一条消息  发送640100000000000000000000000000000000000000000000000000000000
		/*if (type == 1) {
			//wifi
			topic += "/intopic";
		}else {
			//gprs
			topic = "intpoic/" + topic;
		}*/
		try {
			System.out.println("准备发送01指令");
			Server server = new Server(topic);
			server.sevserpush(topic, "640100000000000000000000000000000000000000000000000000000000");
			System.out.println("发送01指令成功");
			return true;
		} catch (MqttException e) {
			System.out.println("发送01指令失败！" + e.getMessage());
		} catch (InterruptedException e) {
			System.out.println("发送01指令失败！" + e.getMessage());
		}
		return false;
	}

//	public static void main(String[] args) {
//		String topic = "132243235773040/outTopic";
//		System.out.println(topic.substring(0,topic.indexOf("/"))+"/inTopic");
//		String  msg = "hex: 640cf0006f007503d500050901021e20b400fe0000150a0001020304055c07641900000000000000000000000000000084f3eb4d2198e207061b0d162004e000";
//		String newmsg = msg.substring(5,70);
//		StringBuilder sb = new StringBuilder(newmsg);
//		System.out.println(sb.replace(3,4,"d").toString());
//		MsgOperation(topic,msg);
//	}

	/**
	 * （消息处理）保存故障和电流
	 * 
	 * @param topic
	 * @param msg
	 * @throws Exception
	 */
	private static void MsgOperation(String topic, String msg, Integer type) {
		int out = topic.indexOf("/") + 1;
		String outString = topic.substring(out);
		System.out.println(TAG + "outString - " + outString);
		String msgString = msg.substring(0, 3);
		System.out.println(TAG + "msgString - " + msgString);
		if (outString.equals("outTopic")) {
			System.out.println(TAG + "设备类型为WIFI");
			if (msgString.equals("hex")) {
				String MAC = topic.substring(0, topic.indexOf("/"));
				String hex = msg.substring(5);
				System.out.println(TAG + "hex = " + hex);
				System.out.println(TAG + "hex.lenth = " + hex.length());
				if (hex.length() == 128) {
					Analysis analysis = new Analysis(hex);
					System.out.println(TAG + "order = " + analysis.getOrder());
					// 收到12回复13 并记录电流信息
					if (analysis.getOrder() == 12) {
						System.out.println(TAG + "记录电流消息");
						String topic2 = topic.substring(0, topic.indexOf("/")) + "/inTopic";
						System.out.println(TAG + "topic2 = " + topic2);
						Server server;
						int rows = -1;
						try {
							// 记录电流等信息
							Electricitylog electricitylog = new Electricitylog();
//	    			    	electricitylog.setLogTime(analysis.getDate());
							electricitylog.setLogTime(DateUtils.nowDateTimeSS());
							electricitylog.setEquipmentNumber(MAC);
							electricitylog.setElectricNumber(analysis.getI());
							electricitylog.setVoltage(analysis.getV());
							electricitylog.setEnergy(analysis.getP());
							electricitylog.setLeakage(analysis.getLK());
							electricitylog.setHarmonic(analysis.getHF());
							electricitylog.setTemperature(analysis.getX5());
							rows = jdbcClient.electricitylogMapper.insert(electricitylog);
							System.out.println(TAG + "electricitylog新增一条数据：" + rows);
							Electrical electrical = jdbcClient.electricalMapper.selectbyclientid(analysis.getMAC(),
									type);
							if (electrical != null) {
								System.out.println(TAG + "该设备已存在，设置在线状态");
								jdbcClient.electricalMapper.updateByMaconline(analysis.getMAC());// 更改设备上线状态
							} else {
								Electrical electrical1 = new Electrical();
								electrical1.setClientid(analysis.getMAC());
								electrical1.setIp("");
								electrical1.setState(1);
								electrical1.setOnlineDate(DateUtils.nowDateTimeSS());
								electrical1.setLastOnlineDate(DateUtils.nowDateTimeSS());
								electrical1.setType(type);
								rows = -1;
								rows = jdbcClient.electricalMapper.insert(electrical1);
								System.out.println(TAG + "新增设备：" + rows);
							}
							// 返回13
							server = new Server(topic2);
							System.out.println(TAG + "准备发送指令12的反馈");
							String newmsg = msg.substring(5, 69);
							StringBuilder sb = new StringBuilder(newmsg);
							System.out.println(TAG + "正在发送指令12的反馈");
							server.sevserpush(topic2, sb.replace(3, 4, "d").toString());
							System.out.println(TAG + "发送指令12的反馈完成");
						} catch (MqttException e) {
							System.out.println("open err！");
						} catch (InterruptedException e) {
							System.out.println("open err！");
						}
						// 收到14回复15 并记录故障信息
					} else if (analysis.getOrder() == 14) {
						int rows = -1;
						System.out.println(TAG + "记录故障消息");
						String topic2 = topic.substring(0, topic.indexOf("/")) + "/inTopic";
						Server server;
						Demo demo = new Demo();
						List<UserEquipmentModle> list = jdbcClient.electricalMapper.getByMac(analysis.getMAC());
						try {
							// 记录故障
							Faultlog faultlog = new Faultlog();
							faultlog.setContent(analysis.getFault() + "");
							faultlog.setEquipmentNumber(MAC);
//    	    			 faultlog.setRecordTime(analysis.getDate());
							faultlog.setRecordTime(DateUtils.nowDateTimeSS());
							faultlog.setIsread(1);
							rows = -1;
							rows = jdbcClient.faultlogMapper.insert(faultlog);
							System.out.println(TAG + "添加一条故障信息 ---- " + rows);
							// 返回15消息
							server = new Server(topic2);
							System.out.println(TAG + "准备发送指令14的反馈");
							String newmsg = msg.substring(5, 69);
							StringBuilder sb = new StringBuilder(newmsg);
							System.out.println(TAG + "正在发送指令14的反馈");
							server.sevserpush(topic2, sb.replace(3, 4, "f").toString());
							System.out.println(TAG + "发送指令14的反馈完成，即将对设备进行推送 ---- " + list.size());
							String nowTime = DateUtils.nowDateTimeSS();
							for (UserEquipmentModle xz : list) {
								System.out.println("故障设备信息：" + xz);
								// 安卓推送
								if (44 == xz.getUmengToken().length()) {
									demo.sendAndroidUnicast("和瑞", "设备故障",
											xz.getName() + "在" + nowTime + "发生" + getfaultmsg(analysis.getFault()),
											xz.getUmengToken());
								} else {// 苹果推送
									demo.sendIOSUnicast(xz.getUmengToken(),
											xz.getName() + "在" + nowTime + "发生" + getfaultmsg(analysis.getFault()));
								}
							}
							System.out.println(TAG + list.size() + "个设备推送完成");
						} catch (MqttException e) {
							System.out.println("open err！");
						} catch (InterruptedException e) {
							System.out.println("open err！");
						}
//    				catch (Exception e) {
//    					System.out.println("推送失败");
//					}
						catch (Exception e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					} else if (analysis.getOrder() == 16) {
						// 收到16回复17
						System.out.println(TAG + "遗漏数据上报");
						String topic2 = topic.substring(0, topic.indexOf("/")) + "/inTopic";
						Server server;
						try {
							server = new Server(topic2);
							System.out.println(TAG + "准备发送指令16的反馈");
							String newmsg = msg.substring(5, 69);
							StringBuilder sb = new StringBuilder(newmsg);
							System.out.println(TAG + "正在发送指令16的反馈");
							server.sevserpush(topic2, sb.replace(2, 4, "11").toString());
							System.out.println(TAG + "发送指令16的反馈完成");
						} catch (MqttException e) {
							System.out.println("open err！");
						} catch (InterruptedException e) {
							System.out.println("open err！");
						}
					}
				}
			}
		} else if (topic.substring(0, (out - 1)).equals("outTopic")) {
			System.out.println(TAG + "设备类型为GPRS");
			// gprs模块
			if (msgString.equals("hex")) {
				String MAC = topic.substring(out);
				String hex = msg.substring(5);
				System.out.println(TAG + MAC + " - " + hex);
				AnalysisGprs analysisGprs = new AnalysisGprs(hex);
				String topic2 = "inTopic" + topic.substring(topic.indexOf("/"));
				System.out.println(TAG + "topic2 = " + topic2);
				Server server;
				int rows = -1;
				// 收到12回复13 并记录电流信息
				if (analysisGprs.getOrder() == 12) {
					System.out.println(TAG + "记录电流信息");
//                        String topic2 = topic.substring(0,topic.indexOf("/"))+"/inTopic";
					try {
						// 记录电流等信息
						Electricitylog electricitylog = new Electricitylog();
//                        electricitylog.setLogTime(analysis.getDate());
						electricitylog.setLogTime(DateUtils.nowDateTimeSS());
						electricitylog.setEquipmentNumber(MAC);
						electricitylog.setElectricNumber(analysisGprs.getI());
						electricitylog.setVoltage(analysisGprs.getV());
						electricitylog.setEnergy(analysisGprs.getP());
						electricitylog.setLeakage(analysisGprs.getLK());
						electricitylog.setHarmonic(analysisGprs.getHF()); // 高频指数
						electricitylog.setTemperature(analysisGprs.getT()); // 温度
						electricitylog.setSignal(analysisGprs.getSignal()); // gprs信号强度
						rows = jdbcClient.electricitylogMapper.insert(electricitylog);
						System.out.println(TAG + "electricitylog新增一条数据：" + rows);
						System.out.println(electricitylog.toString());
						Electrical electrical = jdbcClient.electricalMapper.selectbyclientid(MAC, type);
						if (electrical != null) {
							System.out.println(TAG + "该设备已存在，设置在线状态");
							jdbcClient.electricalMapper.updateByMaconline(MAC);// 更改设备上线状态
						} else {
							Electrical electrical1 = new Electrical();
							electrical1.setClientid(MAC);
							electrical1.setIp("");
							electrical1.setState(1);
							electrical1.setOnlineDate(DateUtils.nowDateTimeSS());
							electrical1.setLastOnlineDate(DateUtils.nowDateTimeSS());
							electrical1.setType(type);
							rows = -1;
							rows = jdbcClient.electricalMapper.insert(electrical1);
							System.out.println(TAG + "新增设备：" + rows);
						}
						// 返回13
						server = new Server(topic2);
						System.out.println(TAG + "准备发送指令12的反馈");
						String newmsg = msg.substring(5, 69);
						StringBuilder sb = new StringBuilder(newmsg);
						System.out.println(TAG + "正在发送指令12的反馈");
						server.sevserpush(topic2, sb.replace(3, 4, "d").toString());
						System.out.println(TAG + "发送指令12的反馈完成");
					} catch (MqttException e) {
						System.out.println("open err！");
					} catch (InterruptedException e) {
						System.out.println("open err！");
					}
					// 收到14回复15 并记录故障信息
				} else if (analysisGprs.getOrder() == 14) {
					System.out.println(TAG + "记录故障信息");
					Demo demo = new Demo();
					List<UserEquipmentModle> list = jdbcClient.electricalMapper.getByMac(MAC);
					try {
						// 记录故障
						Faultlog faultlog = new Faultlog();
						faultlog.setContent(analysisGprs.getFault() + "");
						faultlog.setEquipmentNumber(MAC);
//                         faultlog.setRecordTime(analysis.getDate());
						faultlog.setRecordTime(DateUtils.nowDateTimeSS());
						faultlog.setIsread(1);
						rows = -1;
						rows = jdbcClient.faultlogMapper.insert(faultlog);
						System.out.println(TAG + "添加一条故障信息 ---- " + rows);
						// 返回15消息
						server = new Server(topic2);
						System.out.println(TAG + "准备发送指令14的反馈");
						String newmsg = msg.substring(5, 69);
						StringBuilder sb = new StringBuilder(newmsg);
						System.out.println(TAG + "正在发送指令14的反馈");
						server.sevserpush(topic2, sb.replace(3, 4, "f").toString());
						System.out.println(TAG + "发送指令14的反馈完成，即将对设备进行推送 ---- " + list.size());
						for (UserEquipmentModle xz : list) {
							/*System.out.println("消息====" + xz);
							System.out.println("消息2====" + xz.getUmengToken());*/
							// 安卓推送
							String nowTime = DateUtils.nowDateTimeSS();
							if (44 == xz.getUmengToken().length()) {
								demo.sendAndroidUnicast("和瑞", "设备故障",
										xz.getName() + "在" + nowTime + "发生" + getfaultmsg(analysisGprs.getFault()),
										xz.getUmengToken());
							} else {// 苹果推送
								demo.sendIOSUnicast(xz.getUmengToken(),
										xz.getName() + "在" + nowTime + "发生" + getfaultmsg(analysisGprs.getFault()));
							}
						}
						System.out.println(TAG + list.size() + "个设备推送完成");
					} catch (MqttException e) {
						System.out.println("open err！");
					} catch (InterruptedException e) {
						System.out.println("open err！");
					}
					// 收到16回复17
//                    catch (Exception e) {
//                        System.out.println("推送失败");
//                    }
					catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				} else if (analysisGprs.getOrder() == 16) {
					System.out.println(TAG + "漏报数据上报");
					try {
						server = new Server(topic2);
						System.out.println(TAG + "准备发送指令16的反馈");
						String newmsg = msg.substring(5, 69);
						StringBuilder sb = new StringBuilder(newmsg);
						System.out.println(TAG + "正在发送指令16的反馈");
						server.sevserpush(topic2, sb.replace(2, 4, "11").toString());
						System.out.println(TAG + "发送指令16的反馈完成");
					} catch (MqttException e) {
						System.out.println("open err！");
					} catch (InterruptedException e) {
						System.out.println("open err！");
					}
				}
			}
		}
	}

	public void online(Integer type, String clientid, String ip, int state) {
		Integer rows = -1;
		Electrical electrical = null;
		if (state == 1) {
			System.out.println("上线中=====" + clientid);
			electrical = jdbcClient.electricalMapper.selectbyclientid(clientid, type);
			if (electrical != null) {
				//当前设备已存在
				electrical.setLastOnlineDate(DateUtils.nowDateTimeSS());
				electrical.setIp(ip);
				electrical.setState(state);
				rows = jdbcClient.electricalMapper.updateById(electrical);
			} else {
				System.out.println(clientid + "为新设备");
				electrical = new Electrical();
				electrical.setClientid(clientid);
				electrical.setIp(ip);
				electrical.setState(state);
				electrical.setOnlineDate(DateUtils.nowDateTimeSS());
				electrical.setLastOnlineDate(DateUtils.nowDateTimeSS());
				electrical.setType(type);
				rows = jdbcClient.electricalMapper.insert(electrical);
			}
			System.out.println(electrical.toString());
			System.out.print("设备：" + clientid + "上线");
		} else if (state == 2) {
			System.out.println("下线中=====" + clientid);
			electrical = jdbcClient.electricalMapper.selectbyclientid(clientid, type);
			if (electrical != null) {
				electrical.setState(state);
				electrical.setDownDate(DateUtils.nowDateTimeSS());
				System.out.println(electrical.toString());
				rows = jdbcClient.electricalMapper.updateById(electrical);
				System.out.print("设备：" + clientid + "下线");
			} else {
				System.out.println("未被绑定的设备：" + clientid + " 下线");
			}
		}
		if (rows <= 0 && electrical != null) {
			rows = jdbcClient.electricalMapper.updateState(electrical);
			System.out.println((rows > 0 ? "成功" : "失败") + "    rows = " + rows);
		} else {
			System.out.println((rows > 0 ? "成功" : "失败，electrical为空") + "    rows = " + rows);
		}
	}

	/**
	 * @method waitPair
	 * @apply gprs等待设备配对
	 * @param imei
	 * @param time
	 * @param state
	 */
	public int waitPair(String imei, String time, Integer state) {
		int insertOne = jdbcClient.gprsOnlineInfoMapper.insertOrUpdateOne(imei, time, state);
		return insertOne;
	}

	public static String getfaultmsg(int number) {
		String msg = "其他故障";
		if (number == 1) {
			msg = "电弧故障";
			return msg;
		}
		if (number == 2) {
			msg = "漏电故障";
			return msg;
		}
		if (number == 4) {
			msg = "欠压故障";
			return msg;
		}
		if (number == 8) {
			msg = "过压故障";
			return msg;
		}
		if (number == 16) {
			msg = "过流故障";
			return msg;
		}
		if (number == 32) {
			msg = "短路故障";
			return msg;
		}
		if (number == 64) {
			msg = "过温故障";
			return msg;
		}
		return msg;
	}


}
