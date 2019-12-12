package gprs.tool.MQTT;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.eclipse.paho.client.mqttv3.MqttClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import gprs.com.entity.Electrical;
import gprs.com.mapper.ElectricalMapper;
import gprs.com.mapper.EquipmentlistMapper;
import gprs.com.mapper.GprsOnlineInfoMapper;

@Service
public class MqttSession {

    /**
     *  在线设备
     */
    public static Map<String, MqttClient> map = new HashMap<>();
    
    /**
     * gprs所有已绑定设备imei编号
     */
    public static List<String> gprsAll = new ArrayList<>();
    
    /**
     * 所有已绑定设备(包括wifi、gprs)
     */
    public static List<String> wifiAll = new ArrayList<>();
    
    @Autowired
    private GprsOnlineInfoMapper gprsOnlineInfoMapper;
    @Autowired
    private EquipmentlistMapper equipmentlistMapper;
    
    @PostConstruct
    public void init() {
        // 查询已绑定设备
        gprsAll = gprsOnlineInfoMapper.selectImeiList(Params.GPRS_CONNECT_SUCCESS, null, null);
        wifiAll = equipmentlistMapper.selectMacList();
        System.err.println(gprsAll.size() + " -------------- " + wifiAll.size());
        gprsAll.forEach(x -> {
        	System.out.println(x);
        });
    }
}
