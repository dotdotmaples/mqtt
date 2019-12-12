package gprs.tool.Thread;

import gprs.tool.MQTT.JdbcClient;

public class JDBCThread implements Runnable{

	private String topic;
	private String msg;
	private Integer type;
	private Long creaTime;
//	private volatile boolean isRunning = true;
	
	public JDBCThread(String topic,String msg, Integer type){
		this.topic = topic;
		this.msg = msg;
		this.type = type;
		this.creaTime = System.currentTimeMillis();
	}
	
	//插入数据库
	public void run(){
//		isRunning = false;
		System.out.println("JDBCThread:  " + topic + (type == 1 ? " ---- WIFI" : " ---- GPRS") + " 延迟 " + (System.currentTimeMillis() - creaTime) + " ms处理");
		JdbcClient jdbcClient = new JdbcClient();
		jdbcClient.operationJdbc(topic, msg, type);
	}
	
	
}
