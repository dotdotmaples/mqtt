package gprs.com.controller;

import javax.annotation.Resource;

import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.fusesource.mqtt.client.MQTT;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import gprs.com.exception.MyException;
import gprs.com.mapper.GprsOnlineInfoMapper;
import gprs.com.service.TestInt;
import gprs.com.service.TestService;
import gprs.tool.MQTT.JdbcClient;
import gprs.tool.MQTT.Listener;

@Controller
public class Test {
	
	@Resource 
	TestInt testInt;
	
	@RequestMapping("test")
	@ResponseBody
	public Object xx(){
		Object xxObject = testInt.xx();
		return xxObject;
	}
	
	@RequestMapping("home")
	public void home(Model model) throws Exception{
//		Object xxObject = testInt.xx();
		//Listener.xx();
//		model.addAttribute("user", xxObject);
	}
	
	@Autowired
	TestService t;
	
    @RequestMapping("/myTest")
    @ResponseBody
    public Object we(){
        System.out.println("嗯 开始了");
        try {
            t.tt();
        } catch (MyException e) {
            e.printStackTrace();
            System.out.println("嗯 结束了");
        }
        return null;
    }
    
    @RequestMapping("insertMsgLog")
    @ResponseBody
    public String insertMsglog(String topic, String msg, Integer type) {
    	JdbcClient jdbcClient = new JdbcClient();
		jdbcClient.operationJdbc(topic, msg, type);
		return "ok";
    }
	
}
