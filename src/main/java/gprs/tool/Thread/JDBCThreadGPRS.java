package gprs.tool.Thread;

import gprs.tool.MQTT.JdbcClient;
import gprs.tool.MQTT.MqttSession;

public class JDBCThreadGPRS extends Thread{

    private String imei;
    private String startTime;
    private Integer state;

//	private volatile boolean isRunning = true;
	
	public JDBCThreadGPRS(String imei,String startTime, Integer state){
		this.imei = imei;
		this.startTime = startTime;
		this.state = state;
	}
	//插入数据库
	public void run(){
		JdbcClient jdbcClient = new JdbcClient();
		int rows = jdbcClient.waitPair(imei, startTime, state);
		System.out.println("JDBCThreadGPRS：设备 " + imei + "重置上线时间" + (rows > 0 ? "成功" : "失败") + "    rows = " + rows);
	}
	
	
}
