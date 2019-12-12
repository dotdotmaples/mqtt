package gprs.com.service.Imp;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import org.springframework.stereotype.Service;

import gprs.tool.MQTT.CustomThreadPoolExecutor;
import gprs.tool.MQTT.Listener;
import gprs.tool.MQTT.ListenerOnline;

@Service
public class MQTTServiceImp {

	/**
	 * 控制线程是否继续运行
	 */
	private volatile boolean isRunning = true;

	private Thread thread;
	
	private Thread threadone;
	
	private Listener listener;
	
	/**
	 * 监听消息队列,处理所有的消息
	 */
	@PostConstruct
	public void messageListener() {
	    thread = new Thread(new Runnable() {
	        @Override
	        public void run() {
	        	System.err.println(isRunning);
	            if (isRunning) {
	            	listener = new Listener();
	    			try {
						listener.allMsg();
					} catch (Exception e) {
					    thread.interrupt();
					}
	            }
	        }
	    }, "任务线程");
//	    thread.setDaemon(true);
	    thread.start();
	}
	
	@PreDestroy
	public void stop() {
	    System.out.println("mqtt-gprs is shutdown");
	    isRunning = false;
	    if (thread != null && thread.isAlive()) {
	        thread.interrupt();
	        threadone.interrupt();
	        CustomThreadPoolExecutor.destory();
	    }
	}
	
	/**
	 * 监听消息队列,处理待发送的消息
	 */
	@PostConstruct
	public void messageListener1() {
		threadone = new Thread(new Runnable() {
			@Override
			public void run() {
				if (isRunning) {
					ListenerOnline listener1 = new ListenerOnline();
					try {
						listener1.online();
					} catch (Exception e) {
					    threadone.interrupt();
					}
				}
			}
		}, "任务线程");
//		thread.setDaemon(true);
		threadone.start();
	}
	
	
	
//	@PostConstruct
//    public void allMsg() throws Exception {
//		System.out.println("qidong");
//		if(ext1){
//			System.out.println("qidong========");
//			ext1 = false;
//			MQTTStarThread mqttstart = new MQTTStarThread();  
//			mqttstart.start(); 
//		}
//    }
//	
//	@PostConstruct
//	public void online() throws Exception {
//		System.out.println("qidong2");
//		if(ext2){
//			System.out.println("qidong2==========");
//			ext2 = false;
//			MOTTOnlienOrDown online = new MOTTOnlienOrDown();  
//		    online.start(); 
//		}
//	}
//	
	
}
