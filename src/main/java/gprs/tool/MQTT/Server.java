package gprs.tool.MQTT;


 
 import java.util.Date;

import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.eclipse.paho.client.mqttv3.MqttSecurityException;
import org.eclipse.paho.client.mqttv3.MqttTopic;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;

/**
  * 
 * Title:Server
  * Description: 服务器向多个客户端推送主题，即不同客户端可向服务器订阅相同主题
  * 2016年1月6日下午3:29:28
 */
public class Server {
    
    public static final String HOST = "tcp://127.0.0.1:1883";
    public static final String TOPIC = "";
    private static final String TAG = "Server: ";
//    private static final String clientid = "serverPush";
    /**
     * 订阅主题
     */
    public MqttTopic topic;
    private String userName = "pushserverAll";
    private String passWord = "password";
    /**
     * 客户端
     */
    private  MqttClient client;
    /*
     * mqtt连接设置 
     */
    private MqttConnectOptions option;
    
    /**
     * 消息主体
     */
    public MqttMessage message;
    
     public Server (String topic2) throws MqttException {
         MqttClient mqttClient = MqttSession.map.get(topic2);
         if (null == mqttClient) {
             // 连接
        	 System.out.println(TAG + "连接 " + HOST);
             this.client = new MqttClient(HOST, topic2, new MemoryPersistence());
             connect(this.client,topic2);
         } else if (!mqttClient.isConnected()) {
             // 重连
        	 System.out.println(TAG + "重连 " + HOST);
             reconnect(mqttClient);
             this.topic = mqttClient.getTopic(topic2);
         } else {
             // 在线
        	 System.out.println(TAG + "在线 " + HOST);
             this.topic = mqttClient.getTopic(topic2);
         }
     }
 
    private synchronized void connect(MqttClient client,String topic2) { 
        getOption();
        try {
//             client.setCallback(new PushCallback(Server.this));
             client.connect(option);
             this.topic = client.getTopic(topic2);
             MqttSession.map.put(topic2, client);
         } catch (Exception e) {
            e.printStackTrace();
         }
        
     }
 
    private void getOption() {
        //MQTT连接设置
        option = new MqttConnectOptions();
        //设置是否清空session,false表示服务器会保留客户端的连接记录，true表示每次连接到服务器都以新的身份连接
        option.setCleanSession(true);
        //设置连接的用户名
        option.setUserName(userName);
        //设置连接的密码
        option.setPassword(passWord.toCharArray());
        //设置超时时间 单位为秒
        option.setConnectionTimeout(60);
        //设置会话心跳时间 单位为秒 服务器会每隔(1.5*keepTime)秒的时间向客户端发送个消息判断客户端是否在线，但这个方法并没有重连的机制
        option.setKeepAliveInterval(40);
        // 自动重连
//        option.setAutomaticReconnect(true);
        //setWill方法，如果项目中需要知道客户端是否掉线可以调用该方法。设置最终端口的通知消息
//        option.setWill(topic, "close".getBytes(), 2, true);
    }
    
     public void publish(MqttTopic topic , MqttMessage message){
         MqttDeliveryToken token;
        try {
            token = topic.publish(message);
            token.waitForCompletion();
        } catch (Exception e) {
            e.printStackTrace();
        }
        
     }
     
     
     public void sevserpush(String topic,String msg) throws MqttException, InterruptedException{
    	 System.out.println("发送消息==============="+msg + " 发送topic==============="+topic);
//    	 Server server = new Server(topic);
    	 this.message = new MqttMessage();
    	 this.message.setQos(0);
    	 this.message.setRetained(false);
    	 this.message.setPayload(toBytes(msg));
    	 this.publish(this.topic , this.message);
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
     
     // 断线重连
     public void reconnect (MqttClient mq) throws MqttSecurityException, MqttException {
         if (null != mq) {
             getOption();
             mq.connect(option);
         }
     }
     
     
     public static void main(String[] args) throws MqttException, InterruptedException {
    	 System.out.println("开始=="+new Date().getTime());
    	 String topic = "inTopic/860344046423696";
//    	 String msg = "6401e60030000401d500050901801e20b400fe0000150a0001020304055c0764";
    	 String msg = "6401000000000000000000000000000000000000000000000000000000000000";
    	 Server server = new Server(topic);
    	 server.message = new MqttMessage();
    	 server.message.setQos(0);
    	 server.message.setRetained(false);
         server.message.setPayload(msg.getBytes());
//         server2.publish(server2.topic , server2.message);
         System.out.println(MqttSession.map);
    	 server.sevserpush(topic , msg);
//    	 
//    	 while (true) {
//    	     Thread.sleep(20000); 
//             System.out.println("************************");
//             String topic2 = "132243235146101122/inTopic";
//             String msg2 = "640de60030000401d500050901801e20b400fe0000150a0001020304055c0764";
//             Server server2 = new Server(topic2);
//             server2.sevserpush(topic2 , msg2);
//    	 }
     }
     
 }