package gprs;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import gprs.com.entity.Equipmentlist;
import gprs.com.mapper.EquipmentlistMapper;

@RunWith(SpringRunner.class)
@SpringBootTest
public class MqttGprsApplicationTests {
	
	@Autowired
	private EquipmentlistMapper equipmentlistMapper;

	@Test
	public void contextLoads() {
		Equipmentlist equipmentlist = new Equipmentlist();
        equipmentlist.setUserId(48);
        equipmentlist.setMac("865373044576002");
        Equipmentlist equipmentlist1 = equipmentlistMapper.selectOne(equipmentlist);
        if (equipmentlist1 == null) {
			System.out.println("没有数据");
		}else {
			System.out.println(equipmentlist1.toString());
		}
	}

}
