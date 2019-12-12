package gprs.tool.Thread;


import gprs.tool.MQTT.Listener;

public class MQTTStarThread extends Thread{
	
	public void run(){
		System.out.println("Starting MQTT");
		try {
			Listener listener = new Listener();
			listener.allMsg();
			System.out.println("MQTT is running");
		} catch (Exception e) {
			System.out.println("MQTT启动失败");
		}
	}
}
