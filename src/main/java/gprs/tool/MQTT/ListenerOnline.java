package gprs.tool.MQTT;

import java.util.Map;

import org.fusesource.hawtbuf.*;
import org.fusesource.mqtt.client.*;

import com.alibaba.fastjson.JSON;

import gprs.com.mapper.MsglogMapper;
import gprs.tool.Thread.JDBCThread;

/**
 * Uses an callback based interface to MQTT.  Callback based interfaces
 * are harder to use but are slightly more efficient.
 */
public class ListenerOnline {

//	private String USER = "onlineOrDownlineAll2";
	private String USER = "onOrOfflineListener";
	private String PASSWORD = "password";
	private String HOST = "127.0.0.1";
	private int PORT = 1883;
    
    /**
     * 上下线监听
     * @throws Exception
     */
    public void online() throws Exception {
    	System.out.println("=====上下线监听启用========");
    	final String destination = "$SYS/brokers/+/clients/#";
    	MQTT mqtt = new MQTT();
    	mqtt.setHost(HOST, PORT);
        mqtt.setUserName(USER);
        mqtt.setPassword(PASSWORD);
    	final CallbackConnection connection = mqtt.callbackConnection();
    	connection.listener(new org.fusesource.mqtt.client.Listener() {
    		
    		public void onConnected() {
    		    System.out.println("上下线监听连接成功");
    		}
    		public void onDisconnected() {
    		    System.out.println("上下线监听连接失败");
    		}
    		public void onFailure(Throwable value) {
    		    System.out.println("上下线监听连接失败");
    			value.printStackTrace();
    			System.exit(-2);
    		}
    		public void onPublish(UTF8Buffer topic, Buffer msg, Runnable ack) {
    			// You can now process a received message from a topic after execute the ack runnable.
    			ack.run(); 
    			System.out.println("\n上下线监听器监听到以下内容：\n" + "topic: " + topic + "\nmsg: " + msg.utf8().toString());
    			try {
                    String body = msg.utf8().toString();
                    String topic1 = topic.toString();
                    String online = topic1.substring(topic1.lastIndexOf("/")+1);
                    Map<String, Object> mapTypes = JSON.parseObject(body);
                    if("connected".equals(online)){//上线
                    	String userName = (String) mapTypes.get("username");
                    	String clientid = (String) mapTypes.get("clientid");
                    	String ip = (String) mapTypes.get("ipaddress");
                    	System.out.println("设备：" + clientid + " : " + userName + " 准备在" + ip + "上线");
//                    	System.out.println("上线：：：clientid=" + clientid + " ip=" + ip);
                    	JdbcClient jdbcClient = new JdbcClient();
                    	if(Params.WIFI_NAME.equals(userName)){
                    		jdbcClient.online(Params.EQUIPMENT_WIFI, clientid, ip, 1);
                    	} else if (Params.GPRS_NAME.equals(userName) && MqttSession.gprsAll.contains(clientid)) {
                    	    jdbcClient.online(Params.EQUIPMENT_GPRS, clientid, ip, 1);
                    	}
                    }else if("disconnected".equals(online)){//离线
                        String userName = (String) mapTypes.get("username");
                        String clientid = (String) mapTypes.get("clientid");
                        System.out.println("设备：" + clientid + " ----> " + userName + " 准备下线");
//                        System.out.println("离线===" + clientid);
                        //数据库更新
                        JdbcClient jdbcClient = new JdbcClient();
                        //"coulet".equals(userName)	
                        if(Params.WIFI_NAME.equals(userName)){
                            jdbcClient.online(Params.EQUIPMENT_WIFI, clientid,null,2);
                        } else if (Params.GPRS_NAME.equals(userName) && MqttSession.gprsAll.contains(clientid)) {
                            jdbcClient.online(Params.EQUIPMENT_GPRS, clientid,null,2);
                        }else {
                        	jdbcClient.online(-1, clientid,null,2);
						}
                    }
                } catch (Exception e) {
                    System.out.println("ListenerOnline.myException: " + e.getMessage());
                }
    		}
    	});
    	connection.connect(new Callback<Void>() {
    		@Override
    		public void onSuccess(Void value) {
    			
    			Topic[] topics = {new Topic(destination, QoS.AT_LEAST_ONCE)};
    			// You can subscribe to multiple topics using the the subscribe method
    			connection.subscribe(topics, new Callback<byte[]>() {
    				public void onSuccess(byte[] qoses) {
    					System.out.println("===》连接成功");
    				}
    				public void onFailure(Throwable value) {
    					value.printStackTrace();
    					System.out.println("===》连接成功后订阅非正常退出 ListenerOnline");
    					System.exit(-2);
    				}
    			});
    		}
    		@Override
    		public void onFailure(Throwable value) {
    			value.printStackTrace();
    			System.out.println("===》连接失败后非正常退出 ListenerOnline");
    			System.exit(-2);
    		}
    	});
    	
    	// Wait forever..
    	synchronized (Listener.class) {
    		while(true)
    			Listener.class.wait();
    	}
    }

    
}
