package gprs.tool.MQTT;


import java.util.concurrent.ExecutorService;

import org.apache.ibatis.javassist.compiler.ast.Symbol;
import org.fusesource.hawtbuf.Buffer;
import org.fusesource.hawtbuf.UTF8Buffer;
import org.fusesource.mqtt.client.Callback;
import org.fusesource.mqtt.client.CallbackConnection;
import org.fusesource.mqtt.client.MQTT;
import org.fusesource.mqtt.client.QoS;
import org.fusesource.mqtt.client.Topic;

import gprs.tool.Thread.JDBCThread;
import gprs.tool.Thread.JDBCThreadGPRS;
import gprs.tool.dateUitle.DateUtils;

/**
 * Uses an callback based interface to MQTT.  Callback based interfaces
 * are harder to use but are slightly more efficient.
 */
public class Listener {

//	private String USER = "onlineOrDownlineAll";
	private String USER = "MessageListener";
	private String PASSWORD = "password";
	private String HOST = "127.0.0.1";
	private int PORT = 1883;
	private CallbackConnection connection;
	
	/**
	 * 所有信息监听
	 * @throws Exception
	 */
	public void allMsg() throws Exception {
    	System.out.println("=====所有信息监听启用========");
    	final String destination = "#";
        MQTT mqtt = new MQTT();
        mqtt.setHost(HOST, PORT);
        mqtt.setUserName(USER);
        mqtt.setPassword(PASSWORD);

        connection = mqtt.callbackConnection();
        
        connection.listener(new org.fusesource.mqtt.client.Listener() {

            public void onConnected() {
                System.out.println("信息监听连接成功");
            }
            public void onDisconnected() {
                System.out.println("信息监听连接失败");
            }
            public void onFailure(Throwable value) {
                System.out.println("信息监听失败");
                value.printStackTrace();
                System.exit(-2);
            }
            public void onPublish(UTF8Buffer topic, Buffer msg, Runnable ack) {
            	ack.run();
            	System.out.println("Listener收到一条消息： " + topic + " ---- " + msg);
            	try{
            		//只有gprs设备在上线的时候才会给该主题发送消息
            	    // gprs设备首先订阅主题：/AFDD1，然后推送设备编号信息ascii: 860344046423696
                    if (topic.toString().equals(Params.GPRS_TOPIC)) {
                        // 设备mac
                        String imei = msg.toString().substring(7);
                        // 如果内存中已存在imei编号，则忽略（因为已匹配才会存在）
                        if (!MqttSession.gprsAll.contains(imei)) {
                            // 存入数据库
                            ExecutorService pool = CustomThreadPoolExecutor.getCustomThreadPoolExecutor();  
                            pool.execute(new JDBCThreadGPRS(imei, DateUtils.nowDateTimeSS(), 0));
                        }
                    } else {
                        // gprs模板是以outTopic开头，wifi是以outTopic结尾
                    	// topic尾部需要时已经注册的设备
                        if ((topic.toString().startsWith("outTopic") || topic.toString().startsWith("inTopic")) && MqttSession.gprsAll.contains(topic.toString().substring(topic.toString().indexOf("/")+1))) {
                            System.out.println("gprstopic==="+topic);
                            System.out.println("gprsmsg==="+msg);
                            String body = msg.toString();
                            String topic1 = topic.toString();
                            ExecutorService pool = CustomThreadPoolExecutor.getCustomThreadPoolExecutor();  
                            pool.execute(new JDBCThread(topic1, body, Params.EQUIPMENT_GPRS));
                        } else if ((topic.toString().endsWith("outTopic") || topic.toString().endsWith("inTopic")) && MqttSession.wifiAll.contains(topic.toString().substring(0, topic.toString().indexOf("/")<0?0:topic.toString().indexOf("/")))) {
                            System.out.println("wifitopic==="+topic);
                            System.out.println("wifimsg==="+msg);
                            String body = msg.toString();
                            String topic1 = topic.toString();
                            ExecutorService pool = CustomThreadPoolExecutor.getCustomThreadPoolExecutor();  
                            pool.execute(new JDBCThread(topic1, body, Params.EQUIPMENT_WIFI));
                        }
                    }
            	} catch (Exception e) {
            	    System.out.println("Listener.myException: " + e.getMessage());
            	}
            }
        });
        connection.connect(new Callback<Void>() {
            @Override
            public void onSuccess(Void value) {
                Topic[] topics = {new Topic(destination, QoS.AT_LEAST_ONCE)};
                connection.subscribe(topics, new Callback<byte[]>() {
                    public void onSuccess(byte[] qoses) {
                    }
                    public void onFailure(Throwable value) {
                        value.printStackTrace();
                        System.out.println("===》连接成功后订阅非正常退出 Listener");
                        System.exit(-2);
                    }
                });
            }
            @Override
            public void onFailure(Throwable value) {
                value.printStackTrace();
                System.out.println("===》连接失败后非正常退出 Listener");
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
